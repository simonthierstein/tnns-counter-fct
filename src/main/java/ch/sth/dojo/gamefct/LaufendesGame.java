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
public class LaufendesGame implements Game {
    private final List<Punkt> punkteSpieler;
    private final List<Punkt> punkteGegner;

    public static LaufendesGame initial() {
        return new LaufendesGame(List.empty(),List.empty());
    }

    Game spielerPunktet() {
        return punkteSpieler.size() + 1 == 4
                ? AbeschlossenesGame.abgeschlossenesGame(punkteSpieler.append(punkt()), punkteGegner)
                : new LaufendesGame(punkteSpieler.append(punkt()), punkteGegner);
    }

    Game gegnerPunktet() {
        return punkteGegner.size() + 1 == 4
                ? AbeschlossenesGame.abgeschlossenesGame(punkteSpieler, punkteGegner.append(punkt()))
                : new LaufendesGame(punkteSpieler, punkteGegner.append(punkt()));
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
