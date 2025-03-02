package ch.sth.dojo.beh.cmatch.domain;

import static ch.sth.dojo.beh.PredicateUtils.eq;
import static ch.sth.dojo.beh.PredicateUtils.gte;
import static ch.sth.dojo.beh.PredicateUtils.lte;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.PredicateUtils;
import io.vavr.Predicates;
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.util.function.Predicate;

public record PunkteMatch(Integer value) {

    static Predicate<PunkteMatch> hasOneSet = PredicateUtils.compose(eq(1), PunkteMatch::value);
    static Predicate<PunkteMatch> hasTwoSets = PredicateUtils.compose(eq(2), PunkteMatch::value);

    static Either<DomainProblem, PunkteMatch> of(Integer value) {
        return Option.of(value)
            .toEither(DomainProblem.nullValueNotValid)
            .filterOrElse(Predicates.allOf(
                gte(0),
                lte(2)
            ), x -> DomainProblem.valueNotValid)
            .map(PunkteMatch::new);
    }

    static PunkteMatch zero() {
        return new PunkteMatch(0);
    }

    PunkteMatch increment() {
        return new PunkteMatch(value + 1);
    }
}
