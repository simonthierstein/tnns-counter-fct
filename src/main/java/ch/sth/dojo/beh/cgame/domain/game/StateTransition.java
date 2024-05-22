/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cgame.domain.game;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

import ch.sth.dojo.beh.DomainProblem;
import io.vavr.collection.List;
import io.vavr.control.Either;
import java.util.function.Function;
import java.util.function.Predicate;

public record StateTransition(Predicate<GewinnerVerlierer> condition, Function<GewinnerVerlierer, GewinnerVerlierer> transition) {

    static List<StateTransition> stateTransitions = List.of(
        new StateTransition(GewinnerVerlierer.deuce30AllCondition, GewinnerVerlierer.deuce30AllTransition),
        new StateTransition(GewinnerVerlierer.breakpointCondition, GewinnerVerlierer.breakpointTransition),
        new StateTransition(GewinnerVerlierer.standardCondition, GewinnerVerlierer.standardTransition)
    );

    static Function<GewinnerVerlierer, Either<DomainProblem, GewinnerVerlierer>> apply() {
        return prev ->
            stateTransitions.filter(tuple -> tuple.condition.test(prev))
                .foldLeft(left(DomainProblem.InvalidEvent),
                    (acc, it) -> right(it.transition.apply(prev)));
    }
}
