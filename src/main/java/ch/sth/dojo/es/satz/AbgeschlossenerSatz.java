/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.satz;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import java.util.function.Function;

public record AbgeschlossenerSatz(int punkteSpieler, int punkteGegner) implements Satz {
    public static AbgeschlossenerSatz AbgeschlossenerSatz(final int punkteSpieler, final int punkteGegner) {
        return new AbgeschlossenerSatz(punkteSpieler, punkteGegner);
    }

    public <T, U> Tuple2<T, U> eval(
            Function<Integer, T> spielerEval,
            Function<Integer, U> gegnerEval) {
        return Tuple.of(punkteSpieler, punkteGegner)
                .map(spielerEval, gegnerEval);
    }

}

