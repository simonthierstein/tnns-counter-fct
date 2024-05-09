/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.game;

import static ch.sth.dojo.es.events.GameErzeugt.gameErzeugt;

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

    static Function<Unit, PreInitializedGame> PreInitializedGame() {
        return unit -> preInitializedGame();
    }

    static Function<PreInitializedGame, GameErzeugt> erzeugeGame() {
        return x-> gameErzeugt();
    }

}
