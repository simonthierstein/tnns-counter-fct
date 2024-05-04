/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.gamefct;

import static ch.sth.dojo.gamefct.Punkt.punkt;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import java.util.function.Function;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode @ToString
public class AbeschlossenesGame implements Game{
    private final List<Punkt> punkteSpieler;
    private final List<Punkt> punkteGegner;

    static AbeschlossenesGame abgeschlossenesGame(final List<Punkt> punkteSpieler,
                                                  final List<Punkt> punkteGegner) {
        return new AbeschlossenesGame(punkteSpieler, punkteGegner);
    }
}
