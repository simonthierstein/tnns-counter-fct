package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;
import org.junit.jupiter.api.Test;

class APITest {
    @Test
    void name2() {
        final Counter counter1 = Counter.initial();
        final Counter counter2 = Counter.initial();

        assertThat(counter1.increment()).isEqualTo(counter2.increment());
        assertThat(counter1.increment()).isNotEqualTo(counter2.increment().increment());

        assertThat(counter1.increment().eval()).isEqualTo(1);
        assertThat(counter1.increment().increment().eval()).isEqualTo(2);
    }

}