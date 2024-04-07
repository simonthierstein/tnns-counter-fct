/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package com.example.demo;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

import io.vavr.collection.List;
import java.util.function.Function;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;


public sealed interface CounterState_ permits CounterState_.Zero,CounterState_.Counting {

    static CounterState_ zero() {
        return new Zero();
    }

    static CounterState_ inc(CounterState_ prev) {
        return apply(prev, Zero.zeroInc(), Counting.countingInc());
    }

    static Integer eval(CounterState_ prev) {
        return apply(prev, Zero.zeroEval(), Counting.countingEval());
    }

    private static <T> T apply(CounterState_ prev,
                               Function<Zero, T> f1,
                               Function<Counting, T> f2
    ) {
        return Match(prev).of(
                Case($(instanceOf(Zero.class)), f1),
                Case($(instanceOf(Counting.class)), f2)
        );
    }


    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @EqualsAndHashCode final class Zero implements CounterState_ {

        private static Function<Zero, Counting> zeroInc() {
            return x -> Counting.inst();
        }

        private static Function<Zero, Integer> zeroEval() {
            return x -> 0;
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @EqualsAndHashCode
    final class Counting implements CounterState_ {

        private final List<CounterState_> stateList;

        private static Counting inst() {
            return new Counting(List.empty());
        }

        private static Function<Counting, Counting> countingInc() {
            return x -> new Counting(x.stateList.append(inst()));
        }

        private static Function<Counting, Integer> countingEval() {
            return x -> x.stateList.foldLeft(1, (st, acc) -> st + 1);
        }
    }
}