/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package com.example.demo;

import io.vavr.collection.List;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class CounterState {

    private final List<CounterState> stateList;
    static CounterState zero() {
        return new CounterState(List.empty());
    }

    static CounterState inc(final CounterState state) {
        return new CounterState(state.stateList.append(CounterState.inst()));
    }

    private static CounterState inst() {
        return new CounterState(List.empty());
    }

    static Integer eval(final CounterState state) {
        return state.stateList.foldLeft(0, (st, acc) -> st + 1);
    }
}
