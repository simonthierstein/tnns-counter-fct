/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.satz;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.events.DomainEvent;
import io.vavr.control.Either;

public record AbgeschlossenerSatz(int punkteSpieler, int punkteGegner) implements Satz {
    public static AbgeschlossenerSatz AbgeschlossenerSatz(final int punkteSpieler, final int punkteGegner) {
        return new AbgeschlossenerSatz(punkteSpieler, punkteGegner);
    }

    static Either<DomainError, Satz> handleAbgeschlossenerSatz(final AbgeschlossenerSatz state, final DomainEvent event) {
        return Either.left(new DomainError.InvalidEventForSatz(state, event));
    }
}
