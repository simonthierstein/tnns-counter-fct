/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cmd;

import static ch.sth.dojo.beh.Condition.condition;
import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cgame.CGameCommand;
import ch.sth.dojo.beh.cgame.TiebreakCommand;
import ch.sth.dojo.beh.cgame.domain.CGame;
import ch.sth.dojo.beh.cgame.domain.LaufendesCGame;
import ch.sth.dojo.beh.cgame.domain.Tiebreak;
import ch.sth.dojo.beh.cmatch.CMatchCommand;
import ch.sth.dojo.beh.cmatch.domain.CMatch;
import ch.sth.dojo.beh.cmatch.domain.LaufendesMatch;
import ch.sth.dojo.beh.csatz.CSatzCommand;
import ch.sth.dojo.beh.csatz.domain.CSatz;
import ch.sth.dojo.beh.csatz.domain.LaufenderCSatz;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.GegnerGameGewonnen;
import ch.sth.dojo.beh.evt.GegnerPunktGewonnen;
import ch.sth.dojo.beh.evt.GegnerSatzGewonnen;
import ch.sth.dojo.beh.evt.SpielerGameGewonnen;
import ch.sth.dojo.beh.evt.SpielerMatchGewonnen;
import ch.sth.dojo.beh.evt.SpielerPunktGewonnen;
import ch.sth.dojo.beh.evt.SpielerSatzGewonnen;
import ch.sth.dojo.beh.matchstate.MatchState;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;
import io.vavr.control.Either;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;
import java.util.function.Function;

public record GegnerPunktet() implements DomainCommand {

    public static Either<DomainProblem, DomainEvent> applyC(MatchState state, GegnerPunktet cmd) {
        return state.apply(
            gameMatchState -> gameMatchState.tupled().apply(GegnerPunktet::apply)
        );
    }

    private static Either<DomainProblem, DomainEvent> apply(CMatch cMatch, CSatz cSatz, CGame cGame) {
        final Either<DomainProblem, DomainEvent> domainEvents = CGameCommand.gegnerGewinntPunkt(cGame);
        return domainEvents
            .flatMap(handleGameEvent(cSatz))
            .flatMap(handleSatzEvent(cMatch));
    }

    private static Either<DomainProblem, DomainEvent> apply(CMatch cMatch, CSatz cSatz, Tiebreak cGame) {
        final Either<DomainProblem, DomainEvent> domainEvents = TiebreakCommand.gegnerGewinntPunkt(cGame);
        return domainEvents
            .flatMap(handleGameEvent(cSatz))
            .flatMap(handleSatzEvent(cMatch));
    }

    private static Function<DomainEvent, Either<DomainProblem, DomainEvent>> handleSatzEvent(CMatch state) {
        return satzEvent -> Match(satzEvent).of(
            Case($(instanceOf(SpielerPunktGewonnen.class)), Either::right),
            Case($(instanceOf(SpielerGameGewonnen.class)), Either::right),
            Case($(instanceOf(SpielerSatzGewonnen.class)), event -> CMatchCommand.spielerGewinntSatz(state, event)),
            Case($(instanceOf(GegnerPunktGewonnen.class)), Either::right),
            Case($(instanceOf(GegnerGameGewonnen.class)), Either::right),
            Case($(instanceOf(GegnerSatzGewonnen.class)), event -> CMatchCommand.gegnerGewinntSatz(state, event))
        );
    }

    private static Function<DomainEvent, Either<DomainProblem, DomainEvent>> handleGameEvent(final CSatz cSatz) {
        return event -> Match(event).of(
            Case($(instanceOf(SpielerPunktGewonnen.class)), Either::right),
            Case($(instanceOf(SpielerGameGewonnen.class)), evt -> CSatzCommand.spielerGewinntGame(cSatz, evt)),
            Case($(instanceOf(GegnerPunktGewonnen.class)), Either::right),
            Case($(instanceOf(GegnerGameGewonnen.class)), evt -> CSatzCommand.gegnerGewinntGame(cSatz, evt))
        );
    }

    private static Either<DomainProblem, DomainEvent> applyToTiebreak(final Tiebreak tiebreak) {
        return right(condition(tiebreak, Tiebreak.passIfSpielerOnePunktBisSatz,
            x -> new SpielerSatzGewonnen(),
            x -> new SpielerPunktGewonnen()
        ));
    }

    private static Either<DomainProblem, DomainEvent> applyToLaufendesCGame(LaufendesCGame laufendesCGame, CSatz satz, final CMatch cMatch) {
        return condition(laufendesCGame, LaufendesCGame.passIfSpielerOnePunktBisCGame,
            game -> applyToCSatz(satz, cMatch),
            x -> right(new SpielerPunktGewonnen()));
    }

    private static Either<DomainProblem, DomainEvent> applyToCSatz(CSatz satz, final CMatch cMatch) {
        return CSatz.apply(satz,
            laufenderCSatz -> applyToLaufenderSatz(laufenderCSatz, cMatch),
            x -> left(DomainProblem.valueNotValid));
    }

    private static Either<DomainProblem, DomainEvent> applyToLaufenderSatz(final LaufenderCSatz laufenderCSatz, final CMatch cMatch) {
        return condition(laufenderCSatz, LaufenderCSatz.passIfSpielerOneGameBisSatz,
            x -> applyToMatch(cMatch),
            x -> right(new SpielerGameGewonnen()));
    }

    private static Either<DomainProblem, DomainEvent> applyToMatch(CMatch match) {
        return CMatch.apply(match,
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

    private static Either<DomainProblem, DomainEvent> applyToAbgeschlossenesCGame(final CSatz prev) {
        return CSatz.apply(prev,
            laufend -> right(new SpielerPunktGewonnen()),
            abgeschlossenerCSatz -> left(DomainProblem.valueNotValid));
    }

}
