/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.game;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode @ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class Punkt {
    public static Punkt punkt() {
        return new Punkt();
    }
}
