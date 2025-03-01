/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cmatch.domain;

import static ch.sth.dojo.beh.Condition.condition;

public record LaufendesMatch(SpielerPunkteMatch spielerPunkteMatch, GegnerPunkteMatch gegnerPunkteMatch) implements CMatch {

    public CMatch spielerPunktet() {
        return condition(spielerPunkteMatch.punkteMatch(), PunkteMatch.isWon,
            x -> new AbgeschlossenesMatch(),
            x -> new LaufendesMatch(spielerPunkteMatch.increment(), gegnerPunkteMatch)
        );
    }

    public CMatch gegnerPunktet() {
        return condition(spielerPunkteMatch.punkteMatch(), PunkteMatch.isWon,
            x -> new AbgeschlossenesMatch(),
            x -> new LaufendesMatch(spielerPunkteMatch, gegnerPunkteMatch.incerement())
        );
    }
}
