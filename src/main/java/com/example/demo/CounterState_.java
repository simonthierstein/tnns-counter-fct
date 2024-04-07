/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package com.example.demo;

import io.vavr.collection.List;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;




public interface CounterState_ {

    static CounterState_ zero() {
        return new Zero();
    }

    CounterState_ inc();

    Integer eval();


    class Zero implements CounterState_ {

        @Override public CounterState_ inc() {
            return Counting.inst();
        }

        @Override public Integer eval() {
            return 0;
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @EqualsAndHashCode
    class Counting implements CounterState_{

        private final List<CounterState_> stateList;

        @Override public CounterState_ inc() {
            return new Counting(stateList.append(Counting.inst()));
        }

        private static Counting inst() {
            return new Counting(List.empty());
        }

        @Override public Integer eval() {
            return stateList.foldLeft(1, (st, acc) -> st + 1);
        }
    }
}