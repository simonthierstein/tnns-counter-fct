/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.game.trans;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.game.AbgeschlossenesGame;
import ch.sth.dojo.es.game.Game;
import io.vavr.control.Either;
import java.util.function.Function;

public class ErrorHandling {
    public static <G extends Game> Function<G, Either<DomainError, DomainEvent>> invalidCommandForState(final String commandAsString) {
        return prev -> invalidCommandForState(prev, commandAsString);
    }

    private static Either<DomainError, DomainEvent> invalidCommandForState(final Game prev, final String commandAsString) {
        return Either.left(new DomainError.InvalidCommandForState(prev, commandAsString));
    }

}
