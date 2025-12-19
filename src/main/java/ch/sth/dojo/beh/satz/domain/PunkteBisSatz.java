/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.satz.domain;

record PunkteBisSatz(Integer value) {

    boolean isOne() {
        return value == 1;
    }
}
