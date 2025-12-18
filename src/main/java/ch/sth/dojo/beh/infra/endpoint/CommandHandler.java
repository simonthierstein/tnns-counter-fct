/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.infra.endpoint;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cmd.GegnerPunktet;
import ch.sth.dojo.beh.cmd.SpielerPunktet;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.matchstate.MatchState;
import io.vavr.control.Either;

class CommandHandler {

    static Either<DomainProblem, DomainEvent> handleCommand(InfraCommand command, final MatchState zero) {
        return switch (command) {
            case SpielerPunktetCommand cmd -> spielerPunktet(zero, cmd);
            case GegnerPunktetCommand cmd -> gegnerPunktet(zero, cmd);
        };
    }

    private static Either<DomainProblem, DomainEvent> gegnerPunktet(final MatchState zero, final GegnerPunktetCommand cmd) {
        return GegnerPunktet.gegnerPunktet(cmd.commandId())
                .toEither(DomainProblem.valueNotValid)
                .flatMap(x -> GegnerPunktet.applyC(zero, x));
    }

    private static Either<DomainProblem, DomainEvent> spielerPunktet(final MatchState zero, final SpielerPunktetCommand cmd) {
        return SpielerPunktet.spielerPunktet(cmd.commandId()).toEither(DomainProblem.valueNotValid).flatMap(x -> SpielerPunktet.applyCommand(zero, x));
    }
}
