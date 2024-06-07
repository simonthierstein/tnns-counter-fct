/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.satz;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Option;
import java.util.function.Function;

public record AbgeschlossenerSatz(int punkteSpieler, int punkteGegner) implements Satz {
    public static AbgeschlossenerSatz AbgeschlossenerSatz(final int punkteSpieler, final int punkteGegner) {
        return new AbgeschlossenerSatz(punkteSpieler, punkteGegner);
    }

    public static Option<AbgeschlossenerSatz> fromInteger(final Integer sp, final Integer ge) {
        return Option.of(sp).flatMap(spx ->
                Option.of(ge).flatMap(gex -> erstelleValiderAbgeschlossenerSatz(spx, gex)));
    }

    private static Option<? extends AbgeschlossenerSatz> erstelleValiderAbgeschlossenerSatz(final Integer spx, final Integer gex) {
        return Option.of(new AbgeschlossenerSatz(spx, gex));
    }


    public <T, U> Tuple2<T, U> eval(
            Function<Integer, T> spielerEval,
            Function<Integer, U> gegnerEval) {
        return Tuple.of(punkteSpieler, punkteGegner)
                .map(spielerEval, gegnerEval);
    }

}

