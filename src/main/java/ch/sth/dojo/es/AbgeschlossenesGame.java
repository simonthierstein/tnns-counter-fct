/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es;

import static ch.sth.dojo.es.GegnerHatGameGewonnen.gegnerHatGameGewonnen;
import static ch.sth.dojo.es.SpielerHatGameGewonnen.spielerHatGameGewonnen;

import io.vavr.collection.List;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode @ToString
public class AbgeschlossenesGame implements Game {
    private final List<Punkt> punkteSpieler;
    private final List<Punkt> punkteGegner;

    static AbgeschlossenesGame abgeschlossenesGame(final List<Punkt> punkteSpieler,
                                                   final List<Punkt> punkteGegner) {
        return new AbgeschlossenesGame(punkteSpieler, punkteGegner);
    }


    static SpielerHatGameGewonnen SpielerHatGameGewonnen(final List<ch.sth.dojo.gamefct.Punkt> punkteSpieler,
                                                         final List<ch.sth.dojo.gamefct.Punkt> punkteGegner) {
        return spielerHatGameGewonnen();
    }

    static GegnerHatGameGewonnen GegnerHatGameGewonnen(final List<ch.sth.dojo.gamefct.Punkt> punkteSpieler,
                                                       final List<ch.sth.dojo.gamefct.Punkt> punkteGegner) {
        return gegnerHatGameGewonnen();
    }
}
