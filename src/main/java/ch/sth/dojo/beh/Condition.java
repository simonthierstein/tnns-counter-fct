/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh;

import io.vavr.control.Option;
import java.util.function.Function;
import java.util.function.Predicate;

public class Condition {

    public static <T, R> R condition(T target, Predicate<T> predicate,
        Function<T, R> positiveBehavior,
        Function<T, R> negativeBehavior) {
        return Option.some(target)
            .filter(predicate)
            .fold(() -> negativeBehavior.apply(target), positiveBehavior);
    }
}
