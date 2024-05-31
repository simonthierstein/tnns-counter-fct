/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.match;


import static ch.sth.dojo.es.match.AbgeschlossenesStandardMatch.AbgeschlossenesStandardMatch;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.Routing;
import io.vavr.control.Either;
import io.vavr.control.Option;

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
}


