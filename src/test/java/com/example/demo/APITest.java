package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class APITest {
    @Test
    void name() {
        final Counter counter = new Counter();
        counter.increment();
        assertThat(counter.get()).isEqualTo(1);
    }

}