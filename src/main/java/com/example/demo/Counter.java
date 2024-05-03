/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package com.example.demo;

import java.util.function.Function;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
class Counter {
    private final Function<Unit, CounterState_> state;

    static Counter initial() {
        return new Counter(CounterState_.zero());
    }


    Counter increment() {
        return new Counter(state.andThen(CounterState_.inc()));
    }

    Integer eval() {
        return state.andThen(CounterState_.eval()).apply(Unit.INSTANCE);
    }
}
