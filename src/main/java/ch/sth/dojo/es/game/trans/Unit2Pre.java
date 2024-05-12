/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.game.trans;

import ch.sth.dojo.es.Unit;
import ch.sth.dojo.es.game.PreInitializedGame;
import java.util.function.Function;

public class Unit2Pre {
    public static Function<Unit, PreInitializedGame> createEmpty() { // TODO sth/12.05.2024 : kein command, kein event?
        return unit -> PreInitializedGame.preInitializedGame();
    }
}
