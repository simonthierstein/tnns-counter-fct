/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.match;

import io.vavr.control.Option;
import java.util.function.Predicate;

public record PunkteGegner(Punkte punkte) {

    public static Predicate<PunkteGegner> passIfNotWon() {
        return x -> Punkte.lteOne().test(x.punkte);
    }

    public static PunkteGegner punkteGegner(final Punkte value) {
        return new PunkteGegner(value);
    }

    public static Option<PunkteGegner> fromInteger(final Integer punkte) {
        return Option.of(punkte)
                .flatMap(Punkte::ofInteger)
                .filter(Punkte.passIfInRange(0, 2))
                .map(PunkteGegner::new);
    }

    public Integer current() {
        return punkte().asInteger();
    }

    public PunkteGegner increment() {
        return new PunkteGegner(punkte.increment());
    }
}
