/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.match;

import ch.sth.dojo.es.DomainError;
import io.vavr.Function2;
import io.vavr.Tuple;
import io.vavr.control.Either;
import io.vavr.control.Option;

public record AbgeschlossenesStandardMatch(Integer punkteSpieler, Integer punkteGegner) implements StandardMatch {
    public static Either<DomainError, AbgeschlossenesStandardMatch> AbgeschlossenesStandardMatch(final Integer punkteSpieler, final Integer punkteGegner) {
        return Option.of(punkteSpieler).flatMap(punkteSpieler1 ->
                        Option.of(punkteGegner).map(punkteGegner1 ->
                                new AbgeschlossenesStandardMatch(punkteSpieler1, punkteGegner1)))
                .toEither(new DomainError.InvalidStateForMatch());
    }


    public <T> T eval(Function2<Integer, Integer, T> sp) {
        return Tuple.of(punkteSpieler, punkteGegner).apply(sp);
    }

}

