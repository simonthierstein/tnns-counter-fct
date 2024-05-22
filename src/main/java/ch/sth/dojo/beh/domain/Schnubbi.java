/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.domain;

import java.util.function.Function;
import java.util.function.Predicate;

public record Schnubbi(Punkt punkt) {

    static Schnubbi zero() {
        return new Schnubbi(Punkt.p00);
    }

    public <T> T eval(Function<Punkt, T> mapper) {
        return mapper.apply(punkt);
    }

    static Predicate<Schnubbi> asPredicate(Predicate<Punkt> punktPredicate) {
        return schnubbi -> punktPredicate.test(schnubbi.punkt);
    }
}
