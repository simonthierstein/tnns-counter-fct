/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.game;

import io.vavr.Function2;
import io.vavr.Predicates;
import io.vavr.Tuple;
import io.vavr.collection.List;
import io.vavr.control.Option;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode @ToString
public class AbgeschlossenesGame implements Game {
    private final List<Punkt> punkteSpieler;
    private final List<Punkt> punkteGegner;

    public static AbgeschlossenesGame abgeschlossenesGame(final List<Punkt> punkteSpieler, final List<Punkt> punkteGegner) {
        return new AbgeschlossenesGame(punkteSpieler, punkteGegner);
    }


    public static Option<AbgeschlossenesGame> fromInteger(final Integer sp, final Integer ge) {
        return Option.of(sp)
                .flatMap(spx -> Option.of(ge)
                        .flatMap(gex -> erstelleValidesAbgeschlossenesGame(spx, gex)));
    }

    private static Option<AbgeschlossenesGame> erstelleValidesAbgeschlossenesGame(final Integer spx, final Integer gex) {
        return Option.of(Tuple.of(spx, gex))
                .filter(Predicates.allOf(Predicates.anyOf(t2 -> t2._1 >= 4, t2 -> t2._2 >= 4), t2 -> Math.abs(t2._1 - t2._2) >= 2))
                .map(t2 -> t2.map(Game::toPunkte, Game::toPunkte))
                .map(t2 -> t2.apply(AbgeschlossenesGame::new));
    }

    public static <T> T eval(AbgeschlossenesGame state,
                             Function2<List<Punkt>, List<Punkt>, T> evalFct) {
        return evalFct.apply(state.punkteSpieler, state.punkteGegner);
    }
}
