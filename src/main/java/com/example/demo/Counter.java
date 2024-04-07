/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package com.example.demo;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
class Counter {
    private final CounterState state;

    static Counter initial() {
        return new Counter(CounterState.zero());
    }

    Counter increment() {
        return new Counter(CounterState.inc(state));
    }

    Integer eval() {
        return CounterState.eval(state);
    }
}
