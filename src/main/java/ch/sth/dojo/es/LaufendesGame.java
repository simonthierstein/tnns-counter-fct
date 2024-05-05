/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es;

import static ch.sth.dojo.gamefct.AbgeschlossenesGame.abgeschlossenesGame;
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
public class LaufendesGame implements Game {
    private final List<Punkt> punkteSpieler;
    private final List<Punkt> punkteGegner;

    static LaufendesGame initial() {
        return laufendesGame(List.empty(), List.empty());
    }

    static LaufendesGame laufendesGame(final List<Punkt> punkteSpieler, final List<Punkt> punkteGegner) {
        return new LaufendesGame(punkteSpieler, punkteGegner);
    }

    DomainEvent spielerPunktet() {
        final List<Punkt> incremented = punkteSpieler.append(punkt());
        return incremented.size() == 4
                ? abgeschlossenesGame(incremented, punkteGegner)
                : laufendesGame(incremented, punkteGegner);
    }

    DomainEvent gegnerPunktet() {
        final List<Punkt> incremented = punkteGegner.append(punkt());
        return incremented.size() == 4
                ? abgeschlossenesGame(punkteSpieler, incremented)
                : laufendesGame(punkteSpieler, incremented);
    }

    static Function<LaufendesGame, Tuple2<Integer, Integer>> export2Integer() {
        return game -> {
            final Integer spielerPts = game.punkteSpieler.foldLeft(0, (st, acc) -> st + 1);
            final Integer gegnerPts = game.punkteGegner.foldLeft(0, (st, acc) -> st + 1);
            return Tuple.of(spielerPts, gegnerPts);
        };
    }

    <T> T eval(final Function<LaufendesGame, T> mapper) {
        return mapper.apply(this);
    }
}
