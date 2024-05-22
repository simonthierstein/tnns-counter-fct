/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.domain;

public record GegnerSatzPunktestand(SatzPunkt satzPunkt) {

    static GegnerSatzPunktestand zero() {
        return new GegnerSatzPunktestand(SatzPunkt.zero());
    }

    public GegnerSatzPunktestand plusEins() {
        return new GegnerSatzPunktestand(satzPunkt.inc());
    }
}
