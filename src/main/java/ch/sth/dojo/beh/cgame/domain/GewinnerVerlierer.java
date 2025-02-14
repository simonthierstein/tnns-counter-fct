/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cgame.domain;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import java.util.function.Function;
import java.util.function.Predicate;

public record GewinnerVerlierer(Gewinner gewinner, Verlierer verlierer) {

    static GewinnerVerlierer of(Gewinner gewinner, Verlierer verlierer) {
        return new GewinnerVerlierer(gewinner, verlierer);
    }

    Tuple2<Gewinner, Verlierer> tupled() {
        return Tuple.of(gewinner, verlierer);
    }

    static Predicate<GewinnerVerlierer> deuce30AllCondition = t2 ->
        t2.gewinner().value() == 2 && t2.verlierer().value() == 2;
    static Function<GewinnerVerlierer, GewinnerVerlierer> deuce30AllTransition = t2 ->
        of(new Gewinner(1), new Verlierer(3));
    static Predicate<GewinnerVerlierer> breakpointCondition = t2 ->
        t2.gewinner().value() == 3 && t2.verlierer().value() == 1;
    static Function<GewinnerVerlierer, GewinnerVerlierer> breakpointTransition =
        t2 -> of(new Gewinner(2), new Verlierer(2));
    static Predicate<GewinnerVerlierer> standardCondition = t2 ->
        (t2.gewinner().value() >= 2 && t2.verlierer().value() >= 3)
            || (t2.gewinner().value() >= 3 && t2.verlierer().value() == 2)
            || (t2.gewinner().value() >= 4 && t2.verlierer().value() == 1);
    static Function<GewinnerVerlierer, GewinnerVerlierer> standardTransition =
        t2 -> of(new Gewinner(t2.gewinner().value() - 1), t2.verlierer());

    static GewinnerVerlierer zero() {
        return new GewinnerVerlierer(new Gewinner(0), new Verlierer(0));
    }
}
