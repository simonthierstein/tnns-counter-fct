/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.match;


import ch.sth.dojo.es.DomainError;
import io.vavr.control.Either;
import io.vavr.control.Option;

public record LaufendesStandardMatch(PunkteSpieler punkteSpieler, PunkteGegner punkteGegner) implements StandardMatch {

    public static Either<DomainError, LaufendesStandardMatch> LaufendesStandardMatch(PunkteSpieler punkteSpieler, PunkteGegner punkteGegner) {
        return Option.of(punkteSpieler)
                .flatMap(punkteSpieler1 -> Option.of(punkteGegner)
                        .map(punkteGegner1 -> new LaufendesStandardMatch(punkteSpieler1, punkteGegner1)))
                .toEither(new DomainError.InvalidStateForMatch());
    }

}


