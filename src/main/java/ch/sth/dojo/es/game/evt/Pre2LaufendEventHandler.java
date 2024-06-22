/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.game.evt;

import ch.sth.dojo.es.events.GameErzeugt;
import ch.sth.dojo.es.game.Game;
import ch.sth.dojo.es.game.LaufendesGame;
import java.util.function.Function;

public class Pre2LaufendEventHandler {

    //evt
    public static Function<GameErzeugt, Game> gameErzeugt() {
        return gameErzeugt -> LaufendesGame.initial();
    }
}
