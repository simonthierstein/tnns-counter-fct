/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.match;

import ch.sth.dojo.es.game.Punkt;
import io.vavr.collection.List;
import java.util.function.Predicate;

public class Punkte {
    private final List<Punkt> punkts;

    private Punkte(final List<Punkt> punkts) {
        this.punkts = punkts;
    }

    public static Punkte punkte(final List<Punkt> punkts) {
        return new Punkte(punkts);
    }

    static Predicate<Punkte> lteOne() {
        return pt -> pt.punkts.size() <= 1;
    }

    int asInteger() {
        return punkts.size();
    }

    Punkte increment() {
        return new Punkte(punkts.append(Punkt.punkt()));
    }
}
