/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.game;

import io.vavr.Predicates;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Option;
import java.util.function.Predicate;

record AbgeschlossenesSatzTiebreak(Integer punkteSpieler, Integer punkteGegner) implements SatzTiebreak {

    private static final Predicate<Integer> passIfGteZero = x -> x >= 0;
    private static final Predicate<Integer> passIfGte7 = x -> x >= 7;
    private static final Predicate<Tuple2<Integer, Integer>> passIfDiffEq2 = t2 -> Math.abs(t2._1 - t2._2) == 2;
    public static final Predicate<Tuple2<Integer, Integer>> passIfValidAbgeschlossenesTiebreak = Predicates.allOf(
            Predicates.anyOf(
                    x -> passIfGte7.test(x._1),
                    x -> passIfGte7.test(x._2)),
            passIfDiffEq2);

    static Option<AbgeschlossenesSatzTiebreak> abgeschlossenesSatzTiebreak(final Integer punkteSpieler, final Integer punkteGegner) {
        final Option<Integer> spielerOpt = Option.of(punkteSpieler).filter(passIfGteZero);
        final Option<Integer> gegnerOpt = Option.of(punkteGegner).filter(passIfGteZero);

        return spielerOpt.flatMap(spieler ->
                        gegnerOpt.map(gegner ->
                                Tuple.of(spieler, gegner)))
                .filter(passIfValidAbgeschlossenesTiebreak)
                .map(t2 -> t2.apply(AbgeschlossenesSatzTiebreak::new));
    }
}
