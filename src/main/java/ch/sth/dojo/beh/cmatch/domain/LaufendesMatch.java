/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cmatch.domain;

import static ch.sth.dojo.beh.Condition.condition;
import static ch.sth.dojo.beh.PredicateUtils.compose;

import java.util.function.Predicate;

public record LaufendesMatch(SpielerPunkteMatch spielerPunkteMatch, GegnerPunkteMatch gegnerPunkteMatch) implements CMatch {

    public static final Predicate<LaufendesMatch> passIfSpielerOneSatzBisMatch =
        compose(PunkteMatch.hasOneSet, x -> x.spielerPunkteMatch.punkteMatch());
    public static final Predicate<? super LaufendesMatch> passIfGegnerOneSatzBisMatch =
        compose(PunkteMatch.hasOneSet, x -> x.gegnerPunkteMatch.punkteMatch());

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
