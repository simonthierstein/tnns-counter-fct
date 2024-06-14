/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.game;

import static ch.sth.dojo.es.game.Punkt.punkt;

import io.vavr.Function2;
import io.vavr.Predicates;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Traversable;
import io.vavr.control.Option;
import java.util.function.Function;
import java.util.function.Predicate;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode @ToString
public class LaufendesSatzTiebreak implements SatzTiebreak {
    private static Predicate<Tuple2<Integer, Integer>> passIfValidLaufendesTiebreak;
    public final List<Punkt> punkteSpieler;
    public final List<Punkt> punkteGegner;

    public static Option<LaufendesSatzTiebreak> laufendesSatzTiebreak(final List<Punkt> punkteSpieler, final List<Punkt> punkteGegner) {
        final Option<Integer> ps = Option.of(punkteSpieler).map(Traversable::size);
        final Option<Integer> pg = Option.of(punkteGegner).map(Traversable::size);

        return ps.flatMap(s -> pg.flatMap(g -> erstelleValidesLaufendesTiebreak(s, g)));
    }

    public static Option<LaufendesSatzTiebreak> laufendesSatzTiebreak(final Integer punkteSpieler, final Integer punkteGegner) {
        final Option<Integer> psOpt = Option.of(punkteSpieler);
        final Option<Integer> geOpt = Option.of(punkteGegner);

        return psOpt.flatMap(spInt -> geOpt.flatMap(geInt -> erstelleValidesLaufendesTiebreak(spInt, geInt)));
    }

    private static Option<LaufendesSatzTiebreak> erstelleValidesLaufendesTiebreak(Integer sp, Integer ge) {
        passIfValidLaufendesTiebreak = Predicates.allOf(
                t2 -> t2._1 >= 0,
                t2 -> t2._2 >= 0,
                Predicates.anyOf(Predicates.allOf(
                                t2 -> t2._1 <= 6,
                                t2 -> t2._2 <= 6
                        ),
                        t2 -> t2.apply((ss, gg) -> Math.abs(ss - gg) <= 1)
                )
        );
        return Option.of(Tuple.of(sp, ge))
                .filter(passIfValidLaufendesTiebreak)
                .map(t2 -> t2.map(Game::toPunkte, Game::toPunkte))
                .map(t2 -> t2.apply(LaufendesSatzTiebreak::new));
    }


    public static LaufendesSatzTiebreak zero() {
        return laufendesSatzTiebreak(List.empty(), List.empty()).get();
    }

    public static SatzTiebreak incrementSpieler(LaufendesSatzTiebreak prev) {
        final List<Punkt> incremented = increment(prev.punkteSpieler);
        return AbgeschlossenesSatzTiebreak.abgeschlossenesSatzTiebreak(incremented.size(), prev.punkteGegner.size())
                .fold(() -> new LaufendesSatzTiebreak(incremented, prev.punkteGegner),
                        Function.identity());
    }

    public static SatzTiebreak incrementGegner(LaufendesSatzTiebreak prev) {
        final List<Punkt> incremented = increment(prev.punkteGegner);
        return AbgeschlossenesSatzTiebreak.abgeschlossenesSatzTiebreak(prev.punkteSpieler.size(), incremented.size())
                .fold(() -> new LaufendesSatzTiebreak(prev.punkteSpieler, incremented),
                        Function.identity());
    }

    public static Tuple2<Integer, Integer> eval(LaufendesSatzTiebreak state) {
        return eval(state, Tuple::of).map(List::size, List::size);
    }

    public static <T> T eval(LaufendesSatzTiebreak state,
                             Function2<List<Punkt>, List<Punkt>, T> evalFct) {
        return evalFct.apply(state.punkteSpieler, state.punkteGegner);
    }

    private static List<Punkt> increment(final List<Punkt> prev) {
        return prev.append(punkt());
    }

}
