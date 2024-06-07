/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.scoring;

import ch.sth.dojo.es.game.Game;

public record CurrentGame(Game current) {
    static CurrentGame zero() {
        return new CurrentGame(Game.zero());
    }

    public static CurrentGame currentGame(Game current) {
        return new CurrentGame(current);
    }

}
