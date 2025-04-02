package ch.sth.dojo.beh.cmd;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.matchstate.MatchState;
import io.vavr.control.Either;

public sealed interface DomainCommand permits SpielerPunktet, GegnerPunktet, StarteGame {

    static Either<DomainProblem, DomainEvent> handleCommand(MatchState state, DomainCommand command) {
        return Match(command).of(
            Case($(instanceOf(SpielerPunktet.class)), cmd -> SpielerPunktet.applyCommand(state, cmd)),
            Case($(instanceOf(GegnerPunktet.class)), cmd -> GegnerPunktet.applyC(state, cmd))
        );
    }

}
