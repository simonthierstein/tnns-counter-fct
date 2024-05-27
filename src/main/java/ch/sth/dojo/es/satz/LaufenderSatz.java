/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.satz;

import static ch.sth.dojo.es.game.Punkt.punkt;

import ch.sth.dojo.es.game.Punkt;
import io.vavr.Function2;
import io.vavr.Predicates;
import io.vavr.collection.List;
import io.vavr.control.Option;

public record LaufenderSatz(List<Punkt> punkteSpieler, List<Punkt> punkteGegner) implements Satz {
    static LaufenderSatz zero() {
        return new LaufenderSatz(List.empty(), List.empty());
    }

    static boolean passIfSpielerWon(final LaufenderSatz laufenderSatz) {
        return Option.of(laufenderSatz)
                .filter(Predicates.anyOf(x -> x.punkteSpieler.size() == 6 && x.punkteGegner.size() <= 4,
                        x -> x.punkteSpieler.size() == 7))
                .isDefined();
    }

    static boolean passIfGegnerWon(final LaufenderSatz laufenderSatz) {
        return Option.of(laufenderSatz)
                .filter(Predicates.anyOf(x -> x.punkteGegner.size() == 6 && x.punkteSpieler.size() <= 4,
                        x -> x.punkteGegner.size() == 7))
                .isDefined();
    }

    LaufenderSatz incrementSpieler() {
        return new LaufenderSatz(punkteSpieler.append(punkt()), punkteGegner);
    }

    LaufenderSatz incrementGegner() {
        return new LaufenderSatz(punkteSpieler, punkteGegner.append(punkt()));
    }

    <T> T export(Function2<List<Punkt>, List<Punkt>, T> exporter) {
        return exporter.apply(punkteSpieler, punkteGegner);
    }
}

