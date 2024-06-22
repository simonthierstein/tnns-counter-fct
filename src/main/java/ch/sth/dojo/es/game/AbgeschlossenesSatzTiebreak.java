/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.game;

import static io.vavr.Predicates.allOf;
import static io.vavr.Predicates.anyOf;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Option;
import java.util.function.Predicate;

record AbgeschlossenesSatzTiebreak(Integer punkteSpieler, Integer punkteGegner) implements SatzTiebreak {

    private static final Predicate<Integer> passIfGte7 = x -> x >= 7;
    private static final Predicate<Integer> passIfEq7 = x -> x == 7;
    private static final Predicate<Tuple2<Integer, Integer>> passIfDiffEq2 = t2 -> Math.abs(t2._1 - t2._2) == 2;
    private static final Predicate<Tuple2<Integer, Integer>> passIfDiffGte2 = t2 -> Math.abs(t2._1 - t2._2) >= 2;
    public static final Predicate<Tuple2<Integer, Integer>> passIfValidAbgeschlossenesTiebreak =
            anyOf(allOf(
                            anyOf(
                                    x -> passIfEq7.test(x._1),
                                    x -> passIfEq7.test(x._2)
                            ),
                            passIfDiffGte2),
                    allOf(
                            anyOf(
                                    x -> passIfGte7.test(x._1),
                                    x -> passIfGte7.test(x._2)
                            ),
                            passIfDiffEq2)
            );


    static Option<AbgeschlossenesSatzTiebreak> abgeschlossenesSatzTiebreak(final Integer punkteSpieler, final Integer punkteGegner) {
        return Option.of(punkteSpieler).flatMap(spieler ->
                        Option.of(punkteGegner).map(gegner ->
                                Tuple.of(spieler, gegner)))
                .filter(passIfValidAbgeschlossenesTiebreak)
                .map(t2 -> t2.apply(AbgeschlossenesSatzTiebreak::new));
    }
}
