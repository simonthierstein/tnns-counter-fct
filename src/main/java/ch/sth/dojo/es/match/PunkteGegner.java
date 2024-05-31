/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.match;

import java.util.function.Predicate;

public record PunkteGegner(Punkte punkte) {

    static Predicate<PunkteGegner> passIfNotWon() {
        return x -> Punkte.lteOne().test(x.punkte);
    }

    Integer current() {
        return punkte().asInteger();
    }

    PunkteGegner increment() {
        return new PunkteGegner(punkte.increment());
    }
}
