/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.shared.domain;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import java.util.function.Function;
import java.util.function.Predicate;

public record GewinnerVerlierer(Gewinner gewinner, Verlierer verlierer) {

    static final Predicate<Integer> lte4 = in -> in <= 4;
    static final Predicate<Integer> lte5 = in -> in <= 5;
    static final Predicate<Integer> eq5 = in -> in == 5;
    static final Predicate<Integer> eq6 = in -> in == 6;

    public static GewinnerVerlierer of(Gewinner gewinner, Verlierer verlierer) {
        return new GewinnerVerlierer(gewinner, verlierer);
    }

    public static Predicate<GewinnerVerlierer> compose(Predicate<Gewinner> gewinnerPredicate, Predicate<Verlierer> verliererPredicate) {
        return gewinnerVerlierer -> gewinnerPredicate.test(gewinnerVerlierer.gewinner) && verliererPredicate.test(gewinnerVerlierer.verlierer);
    }

    static <T> Predicate<T> compose(Predicate<Integer> composable, Function<T, Integer> unpacker) {
        return packed -> composable.test(unpacker.apply(packed));
    }

    public Tuple2<Gewinner, Verlierer> tupled() {
        return Tuple.of(gewinner, verlierer);
    }

    static GewinnerVerlierer zero() {
        return new GewinnerVerlierer(new Gewinner(0), new Verlierer(0));
    }
}
