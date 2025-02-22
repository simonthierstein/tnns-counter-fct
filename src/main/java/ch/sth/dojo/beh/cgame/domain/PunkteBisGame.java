/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cgame.domain;

import ch.sth.dojo.beh.DomainProblem;
import io.vavr.Predicates;
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.util.function.Function;
import java.util.function.Predicate;

final class PunkteBisGame {

    public static final Predicate<Integer> gte1 = in -> in >= 1;
    public static final Predicate<Integer> lte5 = in -> in <= 5;
    public static final Predicate<Integer> passIfValidScoreRange = Predicates.allOf(gte1, lte5);

    static <T> Either<DomainProblem, T> PunkteBisGame(Integer value, Function<Integer, T> mapper) {
        return Option.of(value)
            .filter(passIfValidScoreRange)
            .map(mapper)
            .toEither(DomainProblem.valueNotValid);
    }

}
