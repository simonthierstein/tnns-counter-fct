/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.satz;

import static ch.sth.dojo.es.game.Punkt.punkt;

import ch.sth.dojo.es.events.GegnerHatGameGewonnen;
import ch.sth.dojo.es.events.SpielerHatGameGewonnen;
import ch.sth.dojo.es.game.Punkt;
import io.vavr.collection.List;
import java.util.function.Function;

public record LaufenderSatz(List<Punkt> punkteSpieler, List<Punkt> punkteGegner) implements Satz {
    static LaufenderSatz zero() {
        return new LaufenderSatz(List.empty(), List.empty());
    }

    static Function<SpielerHatGameGewonnen, Satz> spielerHatGameGewonnen(LaufenderSatz prev) {
        return evt -> incrementSpieler(prev);
    }

    static Function<GegnerHatGameGewonnen, Satz> gegnerHatGameGewonnen(LaufenderSatz prev) {
        return evt -> incrementGegner(prev);
    }

    private static Satz incrementSpieler(LaufenderSatz prev) {
        return new LaufenderSatz(prev.punkteSpieler.append(punkt()), prev.punkteGegner);
    }

    private static Satz incrementGegner(LaufenderSatz prev) {
        return new LaufenderSatz(prev.punkteSpieler, prev.punkteGegner.append(punkt()));
    }
}
