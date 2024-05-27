/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.evt.game;

import static ch.sth.dojo.es.game.AbgeschlossenesGame.abgeschlossenesGame;
import static ch.sth.dojo.es.game.Punkt.punkt;

import ch.sth.dojo.es.events.GegnerHatGameGewonnen;
import ch.sth.dojo.es.events.SpielerHatGameGewonnen;
import ch.sth.dojo.es.game.AbgeschlossenesGame;
import ch.sth.dojo.es.game.LaufendesGame;
import java.util.function.Function;

public class Laufend2AbgeschlossenEventHandler {

    public static Function<SpielerHatGameGewonnen, AbgeschlossenesGame> shgg(LaufendesGame prev) {
        return event -> abgeschlossenesGame(prev.punkteSpieler.append(punkt()), prev.punkteGegner);
    }

    public static Function<GegnerHatGameGewonnen, AbgeschlossenesGame> ghgg(LaufendesGame prev) {
        return event -> abgeschlossenesGame(prev.punkteSpieler, prev.punkteGegner.append(punkt()));
    }
}
