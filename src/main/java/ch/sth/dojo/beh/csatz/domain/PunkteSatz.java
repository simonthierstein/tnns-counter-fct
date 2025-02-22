/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.csatz.domain;

import ch.sth.dojo.beh.DomainProblem;
import io.vavr.Predicates;
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.util.function.Function;
import java.util.function.Predicate;

final class PunkteSatz {

    public static final Predicate<Integer> gte0 = in -> in >= 0;
    public static final Predicate<Integer> lte7 = in -> in <= 7;
    public static final Predicate<Integer> validRange = Predicates.allOf(gte0, lte7);

    static <T> Either<DomainProblem, T> PunkteSatz(Integer punkte, Function<Integer, T> mapper) {
        return Option.of(punkte)
            .filter(validRange)
            .map(mapper)
            .toEither(DomainProblem.valueNotValid);

    }
}
