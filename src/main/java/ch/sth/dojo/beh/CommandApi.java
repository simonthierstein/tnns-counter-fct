package ch.sth.dojo.beh;

import static ch.sth.dojo.beh.cgame.cmd.DomainCommand.handleCommand;

import ch.sth.dojo.beh.cgame.cmd.GegnerPunktet;
import ch.sth.dojo.beh.cgame.cmd.SpielerPunktet;
import ch.sth.dojo.beh.cgame.domain.CGame;
import ch.sth.dojo.beh.evt.DomainEvent;
import io.vavr.Function2;
import io.vavr.control.Either;

public interface CommandApi {

    Function2<GegnerPunktet, CGame, Either<DomainProblem, DomainEvent>> gegnerPunktet = (cmd, state) -> handleCommand(state, cmd);
    Function2<SpielerPunktet, CGame, Either<DomainProblem, DomainEvent>> spielerPunktet = (cmd, state) -> handleCommand(state, cmd);

}
