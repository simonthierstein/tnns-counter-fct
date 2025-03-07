/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.noadgame.domain;

import ch.sth.dojo.beh.DomainProblem;
import static ch.sth.dojo.beh.PredicateUtils.gte;
import static ch.sth.dojo.beh.PredicateUtils.lte;
import io.vavr.Predicates;
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.util.function.Function;
import java.util.function.Predicate;

final class Punkte {

    private static final Predicate<Integer> passIfValidScoreRange = Predicates.allOf(gte(0), lte(4));

    static <T> Either<DomainProblem, T> Punkte(Integer value, Function<Integer, T> mapper) {
        return Option.of(value)
            .filter(passIfValidScoreRange)
            .map(mapper)
            .toEither(DomainProblem.valueNotValid);
    }

}
