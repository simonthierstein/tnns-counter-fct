/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es;

import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.satztiebreak.SatzTiebreak;
import io.vavr.Function2;
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.util.function.Function;

public interface Util {

    static <S, E extends DomainEvent, L extends DomainError, R> Function2<S, E, Either<L, R>> rightF2(Function2<S, E, R> inputFunction) {
        return (s, e) -> Either.<L, E>right(e).map(inputFunction.apply(s));
    }

    static <S, E extends DomainEvent, L extends DomainError, R> Function2<S, E, Either<L, R>> leftF2(Function2<S, E, L> inputFunction) {
        return (s, e) -> Either.<E, R>left(e).mapLeft(inputFunction.apply(s));
    }

    static <I extends DomainEvent, L extends DomainError, R> Function<I, Either<L, R>> right(Function<I, R> inputFunction) {
        return i -> Either.<L, I>right(i).map(inputFunction);
    }

    static <I extends DomainEvent, L extends DomainError, R> Function<I, Either<DomainError, R>> left(Function<I, L> inputFunction) {
        return i -> Either.<I, R>left(i).mapLeft(inputFunction);
    }

    static <T> Function<T, Option<SatzTiebreak>> none() {
        return x -> Option.none();
    }
}
