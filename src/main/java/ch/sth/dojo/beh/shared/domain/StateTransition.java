/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.shared.domain;

import static io.vavr.control.Either.right;

import ch.sth.dojo.beh.DomainProblem;
import io.vavr.collection.List;
import io.vavr.control.Either;
import java.util.function.Function;
import java.util.function.Predicate;

public record StateTransition(Predicate<GewinnerVerlierer> condition, Function<GewinnerVerlierer, GewinnerVerlierer> transition) {

    public static Function<GewinnerVerlierer, Either<DomainProblem, GewinnerVerlierer>> apply(List<StateTransition> stateTransitions) {
        return prev ->
            stateTransitions.filter(tuple -> tuple.condition.test(prev))
                .foldLeft(right(GewinnerVerlierer.zero()),
                    (acc, it) -> right(it.transition.apply(prev)));
    }
}
