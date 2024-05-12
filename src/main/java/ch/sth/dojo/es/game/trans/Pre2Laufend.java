/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.game.trans;

import ch.sth.dojo.es.events.GameErzeugt;
import ch.sth.dojo.es.game.Game;
import ch.sth.dojo.es.game.LaufendesGame;
import ch.sth.dojo.es.game.PreInitializedGame;
import java.util.function.Function;

public class Pre2Laufend {
    //cmd
    public static Function<PreInitializedGame, GameErzeugt> erzeugeGame() {
        return x -> GameErzeugt.gameErzeugt();
    }

    //evt
    public static Function<GameErzeugt, Game> gameErzeugt() {
        return LaufendesGame.gameErzeugt().andThen(x->x);
    }

}
