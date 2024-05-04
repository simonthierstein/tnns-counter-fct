/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.gamefct;

import io.vavr.collection.List;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode @ToString
public class AbgeschlossenesGame implements Game{
    private final List<Punkt> punkteSpieler;
    private final List<Punkt> punkteGegner;

    static AbgeschlossenesGame abgeschlossenesGame(final List<Punkt> punkteSpieler,
                                                   final List<Punkt> punkteGegner) {
        return new AbgeschlossenesGame(punkteSpieler, punkteGegner);
    }
}
