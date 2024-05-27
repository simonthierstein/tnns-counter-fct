/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.satz;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.events.DomainEvent;
import java.util.function.Function;

public interface Satz {

    static Satz zero() {
        return LaufenderSatz.zero();
    }


    static <E extends DomainEvent> Function<E, DomainError> eventToError(Satz state, String command) {
        return event -> new DomainError.InvalidCommandForSatz(state, command);
    }

    static <E extends DomainEvent> Function<E, DomainError> eventToError(Satz state) {
        return event -> new DomainError.InvalidEventForSatz(state, event);
    }

}

