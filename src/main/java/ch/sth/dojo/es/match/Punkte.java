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

    int asInteger() {
        return punkts.size();
    }

    Punkte increment() {
        return new Punkte(punkts.append(Punkt.punkt()));
    }

    boolean inRange(final int fromInclusive, final int toInclusive) {
        return punkts.size() >= fromInclusive && punkts.size() <= toInclusive;
    }

    static Predicate<Punkte> passIfInRange(final int fromInclusive, final int toInclusive) {
        return punkte -> punkte.inRange(fromInclusive, toInclusive);
    }
}
