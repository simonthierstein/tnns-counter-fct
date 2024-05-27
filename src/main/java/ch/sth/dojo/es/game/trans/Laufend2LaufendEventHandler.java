/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.game.trans;

import ch.sth.dojo.es.events.GegnerHatPunktGewonnen;
import ch.sth.dojo.es.events.SpielerHatPunktGewonnen;
import ch.sth.dojo.es.game.LaufendesGame;
import java.util.function.Function;

public class Laufend2LaufendEventHandler {

    public static Function<GegnerHatPunktGewonnen, LaufendesGame> ghpg(LaufendesGame prev) {
        return event -> LaufendesGame.incrementGegner(prev);
    }

    public static Function<SpielerHatPunktGewonnen, LaufendesGame> shpg(LaufendesGame prev) {
        return event -> LaufendesGame.incrementSpieler(prev);
    }
}
