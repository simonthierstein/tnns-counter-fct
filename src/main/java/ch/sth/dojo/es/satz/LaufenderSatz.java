/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.satz;

import static ch.sth.dojo.es.game.Punkt.punkt;

import ch.sth.dojo.es.game.Punkt;
import io.vavr.collection.List;

public record LaufenderSatz(List<Punkt> punkteSpieler, List<Punkt> punkteGegner) implements Satz {
    static LaufenderSatz zero() {
        return new LaufenderSatz(List.empty(), List.empty());
    }

    Satz incrementSpieler() {
        return new LaufenderSatz(punkteSpieler.append(punkt()), punkteGegner);
    }

    Satz incrementGegner() {
        return new LaufenderSatz(punkteSpieler, punkteGegner.append(punkt()));
    }
}
