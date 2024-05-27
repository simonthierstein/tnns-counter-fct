/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.game;

import static ch.sth.dojo.es.game.Punkt.punkt;

import io.vavr.collection.List;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode @ToString
public class LaufendesGame implements Game {
   public final List<Punkt> punkteSpieler;
   public final List<Punkt> punkteGegner;

    public static LaufendesGame laufendesGame(final List<Punkt> punkteSpieler, final List<Punkt> punkteGegner) {
        return new LaufendesGame(punkteSpieler, punkteGegner);
    }

    public static LaufendesGame initial() {
        return laufendesGame(List.empty(), List.empty());
    }

    public static LaufendesGame incrementSpieler(LaufendesGame prev) {
        return new LaufendesGame(increment(prev.punkteSpieler), prev.punkteGegner);
    }

    public static LaufendesGame incrementGegner(LaufendesGame prev) {
        return new LaufendesGame(increment(prev.punkteSpieler), prev.punkteGegner);
    }

    public static List<Punkt> increment(final List<Punkt> prev) {
        return prev.append(punkt());
    }
}
