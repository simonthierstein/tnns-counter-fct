/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es;

import java.util.function.Function;

public class Identity<T> {
    private final T type;

    private Identity(final T type) {
        this.type = type;
    }

    public static <T> Identity<T> unit(final T type) {
        return new Identity<>(type);
    }

    public <U> Identity<U> map(Function<T, U> mapper) {
        return unit(mapper.apply(type));
    }

    public <U> Identity<U> flatMap(Function<T, Identity<U>> mapper) {
        return unit(mapper.apply(type).eval());
    }

    public T eval() {
        return type;
    }

    public <U> U eval(Function<T, U> mapper) {
        return mapper.apply(eval());
    }
}
