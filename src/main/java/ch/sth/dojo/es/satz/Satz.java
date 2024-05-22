/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.satz;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

import java.util.function.Function;

public interface Satz {
    static Satz spielerHatGameGewonnen(Satz prev) {
        return apply(prev,
                laufenderSatz -> laufenderSatz.incrementSpieler(),
                abgeschlossenerSatz -> {
                    throw new RuntimeException();
                }

        );
    }

    static Satz gegnerHatGameGewonnen(Satz prev) {
        return apply(prev,
                laufenderSatz -> laufenderSatz.incrementGegner(),
                abgeschlossenerSatz -> {
                    throw new RuntimeException();
                }

        );
    }

    static Satz zero() {
        return LaufenderSatz.zero();
    }

    private static <T> T apply(final Satz prev,
                               Function<LaufenderSatz, T> f1,
                               Function<AbgeschlossenerSatz, T> f2
    ) {
        return Match(prev).of(
                Case($(instanceOf(LaufenderSatz.class)), f1),
                Case($(instanceOf(AbgeschlossenerSatz.class)), f2)
        );
    }

}
