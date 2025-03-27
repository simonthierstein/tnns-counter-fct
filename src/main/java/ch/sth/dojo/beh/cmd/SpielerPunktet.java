/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cmd;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cgame.CGameCommand;
import ch.sth.dojo.beh.cgame.domain.CGame;
import ch.sth.dojo.beh.cmatch.CMatchCommand;
import ch.sth.dojo.beh.cmatch.domain.CMatch;
import ch.sth.dojo.beh.csatz.CSatzCommand;
import ch.sth.dojo.beh.csatz.domain.CSatz;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.SpielerGameGewonnen;
import ch.sth.dojo.beh.evt.SpielerPunktGewonnen;
import ch.sth.dojo.beh.evt.SpielerSatzGewonnen;
import ch.sth.dojo.beh.matchstate.MatchState;
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.util.UUID;
import java.util.function.Function;

public record SpielerPunktet(UUID id) implements DomainCommand {

    public static Option<SpielerPunktet> spielerPunktet(final UUID id) {
        return Option.of(id).map(SpielerPunktet::new);
    }

    public static Either<DomainProblem, DomainEvent> applyC(MatchState state, SpielerPunktet cmd) {
        return state.apply(
            gameMatchState -> gameMatchState.tupled().apply(SpielerPunktet::apply)
        );
    }

    private static Either<DomainProblem, DomainEvent> apply(CMatch cMatch, CSatz cSatz, CGame cGame) {
        final Either<DomainProblem, DomainEvent> domainEvents = CGameCommand.spielerGewinntPunkt(cGame);
        return domainEvents
            .flatMap(handleGameEvent(cSatz))
            .flatMap(handleSatzEvent(cMatch));
    }

    private static Function<DomainEvent, Either<DomainProblem, DomainEvent>> handleSatzEvent(CMatch state) {
        return satzEvent -> Match(satzEvent).of(
            Case($(instanceOf(SpielerPunktGewonnen.class)), Either::right),
            Case($(instanceOf(SpielerGameGewonnen.class)), Either::right),
            Case($(instanceOf(SpielerSatzGewonnen.class)), event -> CMatchCommand.spielerGewinntSatz(state, event))
        );
    }

    private static Function<DomainEvent, Either<DomainProblem, DomainEvent>> handleGameEvent(final CSatz cSatz) {
        return event -> Match(event).of(
            Case($(instanceOf(SpielerPunktGewonnen.class)), Either::right),
            Case($(instanceOf(SpielerGameGewonnen.class)), evt -> CSatzCommand.spielerGewinntGame(cSatz, evt))
        );
    }

}
