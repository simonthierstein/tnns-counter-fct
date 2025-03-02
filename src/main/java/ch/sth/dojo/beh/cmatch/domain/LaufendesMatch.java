/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cmatch.domain;

import static ch.sth.dojo.beh.Condition.condition;

import java.util.function.Function;

public record LaufendesMatch(SpielerPunkteMatch spielerPunkteMatch, GegnerPunkteMatch gegnerPunkteMatch) implements CMatch {

    static Function<LaufendesMatch, CMatch> spielerPunktetTransition = LaufendesMatch::spielerPunktet;
    static Function<LaufendesMatch, CMatch> gegnerPunktetTransition = LaufendesMatch::spielerPunktet;

    public CMatch spielerPunktet() {
        return condition(spielerPunkteMatch.punkteMatch(), PunkteMatch.hasOneSet,
            x -> new AbgeschlossenesMatch(),
            x -> new LaufendesMatch(spielerPunkteMatch.increment(), gegnerPunkteMatch)
        );
    }

    public CMatch gegnerPunktet() {
        return condition(gegnerPunkteMatch.punkteMatch(), PunkteMatch.hasOneSet,
            x -> new AbgeschlossenesMatch(),
            x -> new LaufendesMatch(spielerPunkteMatch, gegnerPunkteMatch.incerement())
        );
    }
}
