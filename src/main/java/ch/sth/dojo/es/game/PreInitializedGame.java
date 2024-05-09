/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.game;

import ch.sth.dojo.es.Unit;
import ch.sth.dojo.es.events.GameErzeugt;
import java.util.function.Function;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode @ToString
public class PreInitializedGame implements Game {

    private static PreInitializedGame preInitializedGame() {
        return new PreInitializedGame();
    }

// gehört eigentlich nicht hier rein...
    static Function<Unit, PreInitializedGame> PreInitializedGame() {
        return unit -> preInitializedGame();
    }

    //cmd
    static Function<PreInitializedGame, GameErzeugt> erzeugeGame() {
        return x -> GameErzeugt.gameErzeugt();
    }

    //evt
    static Function<GameErzeugt, Game> gameErzeugt() {
        return LaufendesGame.gameErzeugt().andThen(x->x);
    }


}
