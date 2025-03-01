package ch.sth.dojo.beh;

import static ch.sth.dojo.beh.cmd.DomainCommand.handleCommand;

import ch.sth.dojo.beh.cgame.domain.CGame;
import ch.sth.dojo.beh.cmatch.domain.CMatch;
import ch.sth.dojo.beh.cmd.GegnerPunktet;
import ch.sth.dojo.beh.cmd.SpielerPunktet;
import ch.sth.dojo.beh.csatz.domain.CSatz;
import ch.sth.dojo.beh.evt.DomainEvent;
import io.vavr.Function2;
import io.vavr.Tuple3;
import io.vavr.control.Either;

public interface CommandApi {

    Function2<GegnerPunktet, Tuple3<CMatch, CSatz, CGame>, Either<DomainProblem, DomainEvent>> gegnerPunktet = (cmd, state) -> handleCommand(state, cmd);
    Function2<SpielerPunktet, Tuple3<CMatch, CSatz, CGame>, Either<DomainProblem, DomainEvent>> spielerPunktet = (cmd, state) -> handleCommand(state, cmd);

}
