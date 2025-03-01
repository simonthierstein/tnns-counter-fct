/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.evt;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

import java.util.function.Function;

public sealed interface SpielerDomainEvent extends DomainEvent permits SpielerGameGewonnen, SpielerMatchGewonnen, SpielerPunktGewonnen, SpielerSatzGewonnen {

    static <T> T apply(SpielerDomainEvent evt,
        Function<SpielerPunktGewonnen, T> spielerPunktGewonnenTFunction,
        Function<SpielerGameGewonnen, T> spielerGameGewonnenTFunction) {
        return Match(evt).of(
            Case($(instanceOf(SpielerPunktGewonnen.class)), spielerPunktGewonnenTFunction),
            Case($(instanceOf(SpielerGameGewonnen.class)), spielerGameGewonnenTFunction)
        );
    }

    static <T> T apply(SpielerDomainEvent event,
        Function<SpielerGameGewonnen, T> f1,
        Function<SpielerMatchGewonnen, T> f2,
        Function<SpielerPunktGewonnen, T> f3,
        Function<SpielerSatzGewonnen, T> f4
    ) {
        return switch (event) {
            case SpielerGameGewonnen evt -> f1.apply(evt);
            case SpielerMatchGewonnen evt -> f2.apply(evt);
            case SpielerPunktGewonnen evt -> f3.apply(evt);
            case SpielerSatzGewonnen evt -> f4.apply(evt);
        };
    }
}
