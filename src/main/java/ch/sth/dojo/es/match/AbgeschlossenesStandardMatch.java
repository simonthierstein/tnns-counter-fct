/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.match;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.events.DomainEvent;
import io.vavr.control.Either;

public record AbgeschlossenesStandardMatch(Integer punkteSpieler, Integer punkteGegner) implements StandardMatch {
    public static AbgeschlossenesStandardMatch AbgeschlossenesStandardMatch(final Integer punkteSpieler, final Integer punkteGegner) {
        return new AbgeschlossenesStandardMatch(punkteSpieler, punkteGegner);
    }

    Either<DomainError, StandardMatch> handleEvent(final DomainEvent event) {
        return Either.left(new DomainError.InvalidEventForMatch());
    }
}
