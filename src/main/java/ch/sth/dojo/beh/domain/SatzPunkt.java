/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.domain;

import static java.lang.Math.incrementExact;

import ch.sth.dojo.beh.Condition;
import ch.sth.dojo.beh.DomainProblem;
import io.vavr.Tuple2;
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.util.function.Function;
import java.util.function.Predicate;

public record SatzPunkt(Integer value) {

    private static final Predicate<Integer> passIfInSatzRange =
        x -> x >= 0 && x <= 7;

    static SatzPunkt zero() {
        return new SatzPunkt(0);
    }

    static boolean isNotAbgeschlossen(final SatzPunkt spieler, final SatzPunkt gegner) {
        var spielerWonRegular = spieler.value == 6 && gegner.value <= 4;
        var gegnerWonRegular = spieler.value <= 4 && gegner.value == 6;
        var spielerWonLongSet = spieler.value == 7;
        var gegnerWonLongSet = gegner.value == 7;

        return !(spielerWonRegular
            || gegnerWonLongSet
            || spielerWonLongSet
            || gegnerWonRegular);

    }

    static <T> T applyIfAbgeschlossen(final Tuple2<SatzPunkt, SatzPunkt> of,
        final Function<Tuple2<SatzPunkt, SatzPunkt>, T> positiveApplicable,
        final Function<Tuple2<SatzPunkt, SatzPunkt>, T> negativeApplicable) {
        return Condition.condition(of, t2 -> isNotAbgeschlossen(t2._1, t2._2), positiveApplicable, negativeApplicable);
    }

    public Either<DomainProblem, SatzPunkt> of(Integer value) {
        return Option.of(value)
            .filter(passIfInSatzRange)
            .map(SatzPunkt::new)
            .toEither(DomainProblem.invalidValue);
    }

    SatzPunkt inc() {
        return new SatzPunkt(incrementExact(value));
    }
}
