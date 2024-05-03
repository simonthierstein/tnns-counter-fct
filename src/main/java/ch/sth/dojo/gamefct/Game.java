/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.gamefct;

import static ch.sth.dojo.gamefct.Punkt.punkt;

import io.vavr.collection.List;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode @ToString
public class Game {
    private final List<Punkt> punkteSpieler;
    private final List<Punkt> punkteGegner;

    public static Game initial() {
        return new Game(List.empty(),List.empty());
    }

    Game spielerPunktet() {
        return new Game(punkteSpieler.append(punkt()), punkteGegner);
    }
}
