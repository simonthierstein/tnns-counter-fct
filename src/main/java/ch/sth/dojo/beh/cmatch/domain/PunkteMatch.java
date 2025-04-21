/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cmatch.domain;

import static ch.sth.dojo.beh.PredicateUtils.eq;
import static ch.sth.dojo.beh.PredicateUtils.gte;
import static ch.sth.dojo.beh.PredicateUtils.lte;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.PredicateUtils;
import ch.sth.dojo.beh.cmatch.domain.state.PunkteMatchState;
import io.vavr.Predicates;
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.util.function.Predicate;

public class PunkteMatch {

    public static final Predicate<PunkteMatchState> hasOneSet = PredicateUtils.compose(eq(1), PunkteMatchState::value);
    public static final Predicate<PunkteMatchState> hasTwoSets = PredicateUtils.compose(eq(2), PunkteMatchState::value);

    public static Either<DomainProblem, PunkteMatchState> of(Integer value) {
        return Option.of(value)
            .toEither(DomainProblem.nullValueNotValid)
            .filterOrElse(Predicates.allOf(
                gte(0),
                lte(2)
            ), x -> DomainProblem.valueNotValid)
            .map(PunkteMatchState::new);
    }

    public static PunkteMatchState zero() {
        return new PunkteMatchState(0);
    }

    public static PunkteMatchState increment(PunkteMatchState state) {
        return new PunkteMatchState(state.value() + 1);
    }
}
