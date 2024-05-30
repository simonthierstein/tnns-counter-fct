/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.match;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.events.DomainEvent;
import io.vavr.control.Either;

public final class AbgeschlossenesStandardMatch implements StandardMatch {
    Either<DomainError, StandardMatch> handleEvent(final DomainEvent event) {
        return null;
    }
}
