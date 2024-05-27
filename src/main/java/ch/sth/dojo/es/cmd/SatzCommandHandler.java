package ch.sth.dojo.es.cmd;

import static ch.sth.dojo.es.cmd.AbgeschlossenerSatzCommandHandler.handleAbgeschlossenerSatzCmd;
import static ch.sth.dojo.es.cmd.LaufenderSatzCommandHandler.handleLaufenderSatzCmd;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.satz.Satz;
import io.vavr.control.Either;

public interface SatzCommandHandler {
    static Either<DomainError, DomainEvent> handleCommand(Satz prev, DomainEvent event) {
        return Satz.apply(prev,
                state -> handleLaufenderSatzCmd(state, event),
                state -> handleAbgeschlossenerSatzCmd(state, event)
        );
    }

}
