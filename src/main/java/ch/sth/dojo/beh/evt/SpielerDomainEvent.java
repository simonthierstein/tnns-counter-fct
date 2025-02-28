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

}
