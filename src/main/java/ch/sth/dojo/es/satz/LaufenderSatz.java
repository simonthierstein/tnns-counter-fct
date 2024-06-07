/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.satz;

import static ch.sth.dojo.es.game.Punkt.punkt;

import ch.sth.dojo.es.game.Punkt;
import io.vavr.Predicates;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.control.Option;
import java.util.function.Function;

public record LaufenderSatz(List<Punkt> punkteSpieler, List<Punkt> punkteGegner) implements Satz {
    static LaufenderSatz zero() {
        return new LaufenderSatz(List.empty(), List.empty());
    }

    public static boolean passIfSpielerWon(final LaufenderSatz laufenderSatz) {
        return Option.of(laufenderSatz)
                .filter(Predicates.anyOf(x -> x.punkteSpieler.size() == 6 && x.punkteGegner.size() <= 4,
                        x -> x.punkteSpieler.size() == 7))
                .isDefined();
    }

    public static boolean passIfGegnerWon(final LaufenderSatz laufenderSatz) {
        return Option.of(laufenderSatz)
                .filter(Predicates.anyOf(x -> x.punkteGegner.size() == 6 && x.punkteSpieler.size() <= 4,
                        x -> x.punkteGegner.size() == 7))
                .isDefined();
    }

    public LaufenderSatz incrementSpieler() {
        return new LaufenderSatz(punkteSpieler.append(punkt()), punkteGegner);
    }

    public LaufenderSatz incrementGegner() {
        return new LaufenderSatz(punkteSpieler, punkteGegner.append(punkt()));
    }

    public <T, U> Tuple2<T, U> eval(
            Function<List<Punkt>, T> spielerEval,
            Function<List<Punkt>, U> gegnerEval) {
        return Tuple.of(punkteSpieler, punkteGegner)
                .map(spielerEval, gegnerEval);
    }
}

