/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.match;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.satz.AbgeschlossenerSatz;
import io.vavr.Function2;
import io.vavr.Predicates;
import io.vavr.Tuple;
import io.vavr.control.Either;
import io.vavr.control.Option;

public record AbgeschlossenesStandardMatch(Integer punkteSpieler, Integer punkteGegner) implements StandardMatch {
    public static Either<DomainError, AbgeschlossenesStandardMatch> abgeschlossenesStandardMatch(final Integer punkteSpieler, final Integer punkteGegner) {
        return Option.of(punkteSpieler).flatMap(punkteSpieler1 ->
                        Option.of(punkteGegner).map(punkteGegner1 ->
                                AbgeschlossenesStandardMatch.fromInteger(punkteSpieler1, punkteGegner1)))
                .toEither(new DomainError.InvalidStateForMatch());
    }

    private static AbgeschlossenesStandardMatch fromInteger(final Integer ps, final Integer pg) {
        return new AbgeschlossenesStandardMatch(ps, pg);
    }


    private static Option<AbgeschlossenerSatz> erstelleValiderAbgeschlossenerSatz(final Integer spx, final Integer gex) {
        return Option.of(Tuple.of(spx, gex))
                .filter(Predicates.allOf(t2 -> t2._1 >= 0, t2 -> t2._2 >= 0, Predicates.anyOf(t2 -> t2._1 <= 2, t2 -> t2._2 <= 2)))
                .map(t2 -> t2.apply(AbgeschlossenerSatz::new));
    }

    public <T> T eval(Function2<Integer, Integer, T> sp) {
        return Tuple.of(punkteSpieler, punkteGegner).apply(sp);
    }

}

