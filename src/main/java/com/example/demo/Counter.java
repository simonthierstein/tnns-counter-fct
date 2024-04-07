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
    private final CounterState_ state;

    static Counter initial() {
        return new Counter(CounterState_.zero());
    }

    Counter increment() {
        return new Counter(CounterState_.inc(state));
    }

    Integer eval() {
        return CounterState_.eval(state);
    }
}
