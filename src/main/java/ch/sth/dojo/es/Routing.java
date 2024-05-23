/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es;

import io.vavr.control.Either;
import io.vavr.control.Option;
import java.util.function.Function;
import java.util.function.Predicate;

public final class Routing {

    public static <T, L, R> Either<L, R> selective2SplitEither(T prev,
                                                               Predicate<T> split,
                                                               Function<T, Either<L, R>> positiveHandler,
                                                               Function<T, Either<L, R>> negativeHandler) {
        return Option.some(prev)
                .filter(split)
                .map(positiveHandler)
                .getOrElse(() -> negativeHandler.apply(prev));

    }

    public static <T, R> R selective2Split(T prev,
                                           Predicate<T> split,
                                           Function<T, R> positiveHandler,
                                           Function<T, R> negativeHandler) {
        return Option.some(prev)
                .filter(split)
                .map(positiveHandler)
                .getOrElse(() -> negativeHandler.apply(prev));

    }
}
