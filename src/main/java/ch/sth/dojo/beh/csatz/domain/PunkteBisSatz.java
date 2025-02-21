/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.csatz.domain;

record PunkteBisSatz(Integer value) {

    boolean isOne() {
        return value == 1;
    }
}
