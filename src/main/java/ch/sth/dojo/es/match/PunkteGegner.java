/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.match;

import java.util.function.Predicate;

public record PunkteGegner(Punkte punkte) {

    public static Predicate<PunkteGegner> passIfNotWon() {
        return x -> Punkte.lteOne().test(x.punkte);
    }

    public Integer current() {
        return punkte().asInteger();
    }

    public PunkteGegner increment() {
        return new PunkteGegner(punkte.increment());
    }
}
