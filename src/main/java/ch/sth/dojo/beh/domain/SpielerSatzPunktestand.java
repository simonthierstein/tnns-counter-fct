/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.domain;

public record SpielerSatzPunktestand(SatzPunkt satzPunkt) {

    static SpielerSatzPunktestand zero() {
        return new SpielerSatzPunktestand(SatzPunkt.zero());
    }

    public SpielerSatzPunktestand plusEins() {
        return new SpielerSatzPunktestand(satzPunkt.inc());
    }
}
