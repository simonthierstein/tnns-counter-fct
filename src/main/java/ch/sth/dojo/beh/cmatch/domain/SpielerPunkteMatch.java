/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cmatch.domain;

import static ch.sth.dojo.beh.PredicateUtils.compose;
import static ch.sth.dojo.beh.cmatch.domain.PunkteMatch.hasTwoSets;

import ch.sth.dojo.beh.cmatch.domain.state.SpielerPunkteMatchState;
import java.util.function.Predicate;

public class SpielerPunkteMatch {

    public static final Predicate<SpielerPunkteMatchState> hasWon = compose(hasTwoSets, SpielerPunkteMatchState::punkteMatchState);

    public static SpielerPunkteMatchState zero() {
        return new SpielerPunkteMatchState(PunkteMatch.zero());
    }

    public static SpielerPunkteMatchState increment(SpielerPunkteMatchState state) {
        return new SpielerPunkteMatchState(PunkteMatch.increment(state.punkteMatchState()));
    }
}
