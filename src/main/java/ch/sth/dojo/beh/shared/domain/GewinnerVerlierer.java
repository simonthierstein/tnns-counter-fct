/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.shared.domain;

import io.vavr.Tuple;
import io.vavr.Tuple2;

public record GewinnerVerlierer(Gewinner gewinner, Verlierer verlierer) {

    public static GewinnerVerlierer of(Gewinner gewinner, Verlierer verlierer) {
        return new GewinnerVerlierer(gewinner, verlierer);
    }

    public Tuple2<Gewinner, Verlierer> tupled() {
        return Tuple.of(gewinner, verlierer);
    }

    static GewinnerVerlierer zero() {
        return new GewinnerVerlierer(new Gewinner(0), new Verlierer(0));
    }
}
