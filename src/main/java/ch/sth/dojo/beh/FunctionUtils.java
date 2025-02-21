/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh;

import io.vavr.Tuple2;
import java.util.function.Predicate;

public final class FunctionUtils {

    private FunctionUtils() {
    }

    public static <T, U> Predicate<Tuple2<T, U>> untuple(
        Predicate<T> tPred, Predicate<U> uPred) {
        return t2 -> t2.apply((t, u) -> Boolean.logicalAnd(tPred.test(t), uPred.test(u)));
    }
}
