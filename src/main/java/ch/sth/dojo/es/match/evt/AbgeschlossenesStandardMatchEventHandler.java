/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.match.evt;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.match.AbgeschlossenesStandardMatch;
import ch.sth.dojo.es.match.StandardMatch;
import io.vavr.control.Either;

public class AbgeschlossenesStandardMatchEventHandler {

    public static Either<DomainError, StandardMatch> handleEvent(AbgeschlossenesStandardMatch state, final DomainEvent event) {
        return Either.left(new DomainError.InvalidEventForMatch());
    }
}
