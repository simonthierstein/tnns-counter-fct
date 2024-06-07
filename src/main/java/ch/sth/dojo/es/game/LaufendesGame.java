/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.game;

import static ch.sth.dojo.es.game.Punkt.punkt;

import io.vavr.Function2;
import io.vavr.Predicates;
import io.vavr.Tuple;
import io.vavr.collection.List;
import io.vavr.collection.Traversable;
import io.vavr.control.Option;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode @ToString
public class LaufendesGame implements Game {
    public final List<Punkt> punkteSpieler;
    public final List<Punkt> punkteGegner;

    public static Option<LaufendesGame> laufendesGame(final Integer punkteSpieler, final Integer punkteGegner) {
        final Option<Integer> psOpt = Option.of(punkteSpieler);
        final Option<Integer> geOpt = Option.of(punkteGegner);

        return psOpt.flatMap(spInt -> geOpt.flatMap(geInt -> erstelleValidesLaufendesGame(spInt, geInt)));
    }

    public static Option<LaufendesGame> laufendesGame(final List<Punkt> punkteSpieler, final List<Punkt> punkteGegner) {
        final Option<Integer> ps = Option.of(punkteSpieler).map(Traversable::size);
        final Option<Integer> pg = Option.of(punkteGegner).map(Traversable::size);

        return ps.flatMap(s -> pg.flatMap(g -> erstelleValidesLaufendesGame(s, g)));
    }

    private static Option<LaufendesGame> erstelleValidesLaufendesGame(Integer sp, Integer ge) {
        return Option.of(Tuple.of(sp, ge))
                .filter(Predicates.allOf(
                        t2 -> t2._1 >= 0,
                        t2 -> t2._2 >= 0 // TODO sth/07.06.2024 : validierung zweipunkteabstand
                ))
                .map(t2 -> t2.map(Game::toPunkte, Game::toPunkte))
                .map(t2 -> t2.apply(LaufendesGame::new));
    }


    public static LaufendesGame initial() {
        return laufendesGame(List.empty(), List.empty()).get();
    }

    public static LaufendesGame incrementSpieler(LaufendesGame prev) {
        return new LaufendesGame(increment(prev.punkteSpieler), prev.punkteGegner);
    }

    public static LaufendesGame incrementGegner(LaufendesGame prev) {
        return new LaufendesGame(prev.punkteSpieler, increment(prev.punkteGegner));
    }

    public static <T> T eval(LaufendesGame state,
                             Function2<List<Punkt>, List<Punkt>, T> evalFct) {
        return evalFct.apply(state.punkteSpieler, state.punkteGegner);
    }

    private static List<Punkt> increment(final List<Punkt> prev) {
        return prev.append(punkt());
    }
}
