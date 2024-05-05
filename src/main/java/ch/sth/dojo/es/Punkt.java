/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode @ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
final class Punkt {
    static Punkt punkt() {
        return new Punkt();
    }
}
