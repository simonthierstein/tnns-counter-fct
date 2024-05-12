/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.game.trans;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.game.Game;
import io.vavr.control.Either;
import java.util.function.Function;

public class ErrorHandling {
    public static <G extends Game> Function<G, Either<DomainError, DomainEvent>> handleSpielerPunktet() {
        return ErrorHandling::handleSpielerPunktet;
    }

    private static Either<DomainError, DomainEvent> handleSpielerPunktet(final Game prev) {
        return Either.left(new DomainError.InvalidCommandForState(prev, "handleSpielerPunktet"));
    }
}
