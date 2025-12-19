/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cmd;

import static ch.sth.dojo.beh.Condition.condition;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.game.GameCommand;
import ch.sth.dojo.beh.game.TiebreakCommand;
import ch.sth.dojo.beh.game.domain.Game;
import ch.sth.dojo.beh.game.domain.LaufendesGame;
import ch.sth.dojo.beh.game.domain.Tiebreak;
import ch.sth.dojo.beh.cmatch.MatchCommand;
import ch.sth.dojo.beh.cmatch.domain.TennisMatch;
import ch.sth.dojo.beh.cmatch.domain.LaufendesMatch;
import ch.sth.dojo.beh.satz.SatzCommand;
import ch.sth.dojo.beh.satz.domain.Satz;
import ch.sth.dojo.beh.satz.domain.LaufenderSatz;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.GegnerGameGewonnen;
import ch.sth.dojo.beh.evt.GegnerPunktGewonnen;
import ch.sth.dojo.beh.evt.GegnerSatzGewonnen;
import ch.sth.dojo.beh.evt.SpielerGameGewonnen;
import ch.sth.dojo.beh.evt.SpielerMatchGewonnen;
import ch.sth.dojo.beh.evt.SpielerPunktGewonnen;
import ch.sth.dojo.beh.evt.SpielerSatzGewonnen;
import ch.sth.dojo.beh.matchstate.MatchState;
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.util.UUID;
import java.util.function.Function;

public record GegnerPunktet(UUID uuid) implements DomainCommand {

    public static Option<GegnerPunktet> gegnerPunktet(final UUID id) {
        return Option.of(id).map(GegnerPunktet::new);
    }

    public static Either<DomainProblem, DomainEvent> applyC(MatchState state, GegnerPunktet cmd) {
        return state.apply(
            gameMatchState -> gameMatchState.tupled().apply(GegnerPunktet::apply)
        );
    }

    private static Either<DomainProblem, DomainEvent> apply(TennisMatch match, Satz satz, Game game) {
        final Either<DomainProblem, DomainEvent> domainEvents = GameCommand.gegnerGewinntPunkt(game);
        return domainEvents
            .flatMap(handleGameEvent(satz))
            .flatMap(handleSatzEvent(match));
    }

    private static Either<DomainProblem, DomainEvent> apply(TennisMatch match, Satz satz, Tiebreak cGame) {
        final Either<DomainProblem, DomainEvent> domainEvents = TiebreakCommand.gegnerGewinntPunkt(cGame);
        return domainEvents
            .flatMap(handleGameEvent(satz))
            .flatMap(handleSatzEvent(match));
    }

    private static Function<DomainEvent, Either<DomainProblem, DomainEvent>> handleSatzEvent(TennisMatch state) {
        return satzEvent -> Match(satzEvent).of(
            Case($(instanceOf(SpielerPunktGewonnen.class)), Either::right),
            Case($(instanceOf(SpielerGameGewonnen.class)), Either::right),
            Case($(instanceOf(SpielerSatzGewonnen.class)), event -> MatchCommand.spielerGewinntSatz(state, event)),
            Case($(instanceOf(GegnerPunktGewonnen.class)), Either::right),
            Case($(instanceOf(GegnerGameGewonnen.class)), Either::right),
            Case($(instanceOf(GegnerSatzGewonnen.class)), event -> MatchCommand.gegnerGewinntSatz(state, event))
        );
    }

    private static Function<DomainEvent, Either<DomainProblem, DomainEvent>> handleGameEvent(final Satz satz) {
        return event -> Match(event).of(
            Case($(instanceOf(SpielerPunktGewonnen.class)), Either::right),
            Case($(instanceOf(SpielerGameGewonnen.class)), evt -> SatzCommand.spielerGewinntGame(satz, evt)),
            Case($(instanceOf(GegnerPunktGewonnen.class)), Either::right),
            Case($(instanceOf(GegnerGameGewonnen.class)), evt -> SatzCommand.gegnerGewinntGame(satz, evt))
        );
    }

    private static Either<DomainProblem, DomainEvent> applyToTiebreak(final Tiebreak tiebreak) {
        return right(condition(tiebreak, Tiebreak.passIfSpielerOnePunktBisSatz,
            x -> new SpielerSatzGewonnen(),
            x -> new SpielerPunktGewonnen()
        ));
    }

    private static Either<DomainProblem, DomainEvent> applyToLaufendesCGame(LaufendesGame laufendesCGame, Satz satz, final TennisMatch match) {
        return condition(laufendesCGame, LaufendesGame.passIfSpielerOnePunktBisCGame,
            game -> applyToCSatz(satz, match),
            x -> right(new SpielerPunktGewonnen()));
    }

    private static Either<DomainProblem, DomainEvent> applyToCSatz(Satz satz, final TennisMatch match) {
        return Satz.apply(satz,
            laufenderCSatz -> applyToLaufenderSatz(laufenderCSatz, match),
            x -> left(DomainProblem.valueNotValid));
    }

    private static Either<DomainProblem, DomainEvent> applyToLaufenderSatz(final LaufenderSatz laufenderCSatz, final TennisMatch match) {
        return condition(laufenderCSatz, LaufenderSatz.passIfSpielerOneGameBisSatz,
            x -> applyToMatch(match),
            x -> right(new SpielerGameGewonnen()));
    }

    private static Either<DomainProblem, DomainEvent> applyToMatch(TennisMatch match) {
        return TennisMatch.apply(match,
            laufendesMatch -> right(applyToLaufendesMatch(laufendesMatch)),
            abgeschlossenesMatch -> left(DomainProblem.valueNotValid)
        );
    }

    private static DomainEvent applyToLaufendesMatch(final LaufendesMatch laufendesMatch) {
        return condition(laufendesMatch, LaufendesMatch.passIfSpielerOneSatzBisMatch,
            x -> new SpielerMatchGewonnen(),
            x -> new SpielerSatzGewonnen()
        );
    }

    private static Either<DomainProblem, DomainEvent> applyToAbgeschlossenesCGame(final Satz prev) {
        return Satz.apply(prev,
            laufend -> right(new SpielerPunktGewonnen()),
            abgeschlossenerCSatz -> left(DomainProblem.valueNotValid));
    }

}
