/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.match;


import static ch.sth.dojo.es.match.AbgeschlossenesStandardMatch.AbgeschlossenesStandardMatch;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.Routing;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.util.function.Function;

public record LaufendesStandardMatch(PunkteSpieler punkteSpieler, PunkteGegner punkteGegner) implements StandardMatch {

    public static Either<DomainError, LaufendesStandardMatch> LaufendesStandardMatch(PunkteSpieler punkteSpieler, PunkteGegner punkteGegner) {
        return Option.of(punkteSpieler)
                .flatMap(punkteSpieler1 -> Option.of(punkteGegner)
                        .map(punkteGegner1 -> new LaufendesStandardMatch(punkteSpieler1, punkteGegner1)))
                .toEither(new DomainError.InvalidStateForMatch());
    }


    public static Either<DomainError, StandardMatch> incrementSpieler(LaufendesStandardMatch prev) {
        return Either.narrow(Routing.selection(prev.punkteSpieler().increment(),
                PunkteSpieler.passIfNotWon(),
                nextPt -> LaufendesStandardMatch(nextPt, prev.punkteGegner()),
                nextPt -> AbgeschlossenesStandardMatch(nextPt.current(), prev.punkteGegner().current())));
    }

    public static Either<DomainError, StandardMatch> incrementGegner(LaufendesStandardMatch prev) {
        return Either.narrow(Routing.selection(prev.punkteGegner().increment(),
                PunkteGegner.passIfNotWon(),
                nextPt -> LaufendesStandardMatch(prev.punkteSpieler(), nextPt),
                nextPt -> AbgeschlossenesStandardMatch(prev.punkteSpieler().current(), nextPt.current())));
    }

    static StandardMatch zero() {
        return new LaufendesStandardMatch(new PunkteSpieler(Punkte.zero()), new PunkteGegner(Punkte.zero()));
    }

    public <T, U> Tuple2<T, U> eval(Function<PunkteSpieler, T> sp, Function<PunkteGegner, U> ge) {
        return Tuple.of(punkteSpieler, punkteGegner).map(sp, ge);
    }

}


