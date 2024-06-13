/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.match;

import ch.sth.dojo.es.game.Punkt;
import io.vavr.collection.List;
import io.vavr.control.Option;
import java.util.function.Predicate;

public record Punkte(List<Punkt> punkts) {

    public static Punkte punkte(final List<Punkt> punkts) {
        return new Punkte(punkts);
    }

    public static Punkte zero() {
        return new Punkte(List.empty());
    }

    static Predicate<Punkte> lteOne() {
        return pt -> pt.punkts.size() <= 1;
    }

    static Option<Punkte> ofInteger(Integer anz) {
        return Option.of(anz)
                .map(x -> List.range(0, x).foldLeft(
                        List.<Punkt>empty(), (acc, item) -> acc.append(Punkt.punkt())))
                .map(Punkte::new);
    }

    static int asInteger(Punkte punkte) {
        return punkte.punkts.size();
    }

    static Punkte increment(Punkte punkte) {
        return new Punkte(punkte.punkts.append(Punkt.punkt()));
    }

    static boolean inRange(Punkte punkte, final int fromInclusive, final int toInclusive) {
        return punkte.punkts.size() >= fromInclusive && punkte.punkts.size() <= toInclusive;
    }

    static Predicate<Punkte> passIfInRange(final int fromInclusive, final int toInclusive) {
        return punkte -> inRange(punkte, fromInclusive, toInclusive);
    }
}
