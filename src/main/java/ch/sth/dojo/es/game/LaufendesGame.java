/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.game;

import static ch.sth.dojo.es.game.AbgeschlossenesGame.abgeschlossenesGame;
import static ch.sth.dojo.es.game.Punkt.punkt;

import ch.sth.dojo.es.events.GegnerHatGameGewonnen;
import ch.sth.dojo.es.events.GegnerHatPunktGewonnen;
import ch.sth.dojo.es.events.SpielerHatGameGewonnen;
import ch.sth.dojo.es.events.SpielerHatPunktGewonnen;
import io.vavr.collection.List;
import java.util.function.Function;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode @ToString
public class LaufendesGame implements Game {
   public final List<Punkt> punkteSpieler;
   public final List<Punkt> punkteGegner;

    public static LaufendesGame laufendesGame(final List<Punkt> punkteSpieler, final List<Punkt> punkteGegner) {
        return new LaufendesGame(punkteSpieler, punkteGegner);
    }

    public static LaufendesGame initial() {
        return laufendesGame(List.empty(), List.empty());
    }

    // commands

    // events

    static Function<SpielerHatGameGewonnen, AbgeschlossenesGame> shgg(LaufendesGame prev) {
        return event -> abgeschlossenesGame().apply(prev.punkteSpieler.append(punkt()), prev.punkteGegner);
    }

    static Function<GegnerHatGameGewonnen, AbgeschlossenesGame> ghgg(LaufendesGame prev) {
        return event -> abgeschlossenesGame().apply(prev.punkteSpieler, prev.punkteGegner.append(punkt()));
    }

    static Function<GegnerHatPunktGewonnen, LaufendesGame> ghpg(LaufendesGame prev) {
        return event -> laufendesGame(prev.punkteSpieler, prev.punkteGegner.append(punkt()));
    }

    static Function<SpielerHatPunktGewonnen, LaufendesGame> shpg(LaufendesGame prev) {
        return event -> laufendesGame(prev.punkteSpieler.append(punkt()), prev.punkteGegner);
    }
}
