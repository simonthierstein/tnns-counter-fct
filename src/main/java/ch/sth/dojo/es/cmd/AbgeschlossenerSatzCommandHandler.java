/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.cmd;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.satz.AbgeschlossenerSatz;
import ch.sth.dojo.es.satz.Satz;
import io.vavr.control.Either;

class AbgeschlossenerSatzCommandHandler {
    public static Either<DomainError, DomainEvent> handleAbgeschlossenerSatzCmd(AbgeschlossenerSatz state, DomainEvent event) {
        return Either.<DomainEvent, DomainEvent>left(event).mapLeft(Satz.eventToError(state, event.toString()));
    }
}
