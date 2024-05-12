/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.game;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode @ToString
public class PreInitializedGame implements Game {

    public static PreInitializedGame preInitializedGame() {
        return new PreInitializedGame();
    }

}
