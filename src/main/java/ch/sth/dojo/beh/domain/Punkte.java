/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.domain;

import java.util.function.Function;

public record Punkte(Schnubbi schnubbi) {

    static Punkte zero() {
        return new Punkte(Schnubbi.zero());
    }

    public <T> T eval(Function<Schnubbi, T> mapper) {
        return mapper.apply(schnubbi);
    }

}

