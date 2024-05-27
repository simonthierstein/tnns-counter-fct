package ch.sth.dojo.es.evt.satz;

import static ch.sth.dojo.es.evt.satz.AbgeschlossenerSatzEventHandler.handleAbgeschlossenerSatz;
import static ch.sth.dojo.es.evt.satz.LaufenderSatzEventHandler.handleLaufenderSatz;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.satz.Satz;
import io.vavr.Function2;
import io.vavr.control.Either;

public interface SatzEventHandler {

    static Either<DomainError, Satz> handleEvent(Satz prev, DomainEvent event) {
        return Satz.apply(prev,
                state -> handleLaufenderSatz(state, event),
                state -> handleAbgeschlossenerSatz(state, event)
        );
    }

    static <S extends Satz, E extends DomainEvent> Function2<S, E, DomainError> eventToErrorF2() {
        return DomainError.InvalidEventForSatz::new;
    }

}
