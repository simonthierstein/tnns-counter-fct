/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.cmd.game;

import ch.sth.dojo.es.events.GameErzeugt;
import ch.sth.dojo.es.game.PreInitializedGame;
import java.util.function.Function;

public class Pre2LaufendCommandHandler {
    //cmd
    public static Function<PreInitializedGame, GameErzeugt> erzeugeGame() {
        return x -> GameErzeugt.gameErzeugt();
    }

}

