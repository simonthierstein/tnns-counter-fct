/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.game;

import static ch.sth.dojo.es.events.GameErzeugt.gameErzeugt;

import ch.sth.dojo.es.events.GameErzeugt;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode @ToString
public class PreInitializedGame implements Game {

    static PreInitializedGame preInitializedGame() {
        return new PreInitializedGame();
    }

    static GameErzeugt erzeugeGame() {
        return gameErzeugt();
    }

}
