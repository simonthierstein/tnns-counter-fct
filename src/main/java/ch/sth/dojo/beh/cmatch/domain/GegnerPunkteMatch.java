/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cmatch.domain;

import static ch.sth.dojo.beh.PredicateUtils.compose;
import static ch.sth.dojo.beh.cmatch.domain.PunkteMatch.hasTwoSets;

import ch.sth.dojo.beh.cmatch.domain.state.GegnerPunkteMatchState;
import java.util.function.Predicate;

public class GegnerPunkteMatch {

    static Predicate<GegnerPunkteMatchState> hasWon = compose(hasTwoSets, GegnerPunkteMatchState::punkteMatchState);

    static GegnerPunkteMatchState zero() {
        return new GegnerPunkteMatchState(PunkteMatch.zero());
    }

    public static GegnerPunkteMatchState increment(GegnerPunkteMatchState state) {
        return new GegnerPunkteMatchState(PunkteMatch.increment(state.punkteMatchState()));
    }
}
