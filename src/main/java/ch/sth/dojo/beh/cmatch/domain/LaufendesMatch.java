/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cmatch.domain;

import static ch.sth.dojo.beh.Condition.condition;
import static ch.sth.dojo.beh.PredicateUtils.compose;

import ch.sth.dojo.beh.cmatch.domain.state.AbgeschlossenesMatchState;
import ch.sth.dojo.beh.cmatch.domain.state.LaufendesMatchState;
import ch.sth.dojo.beh.cmatch.domain.state.MatchState;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.GegnerMatchGewonnen;
import ch.sth.dojo.beh.evt.GegnerSatzGewonnen;
import ch.sth.dojo.beh.evt.SpielerMatchGewonnen;
import ch.sth.dojo.beh.evt.SpielerSatzGewonnen;
import java.util.function.Predicate;

public class LaufendesMatch {

    public static final Predicate<LaufendesMatchState> passIfSpielerOneSatzBisMatch =
        compose(PunkteMatch.hasOneSet, x -> x.spielerPunkteMatch().punkteMatchState());
    public static final Predicate<? super LaufendesMatchState> passIfGegnerOneSatzBisMatch =
        compose(PunkteMatch.hasOneSet, x -> x.gegnerPunkteMatch().punkteMatchState());

    public static MatchState spielerPunktet(LaufendesMatchState laufendesMatch) {
        return condition(laufendesMatch.spielerPunkteMatch().punkteMatchState(), PunkteMatch.hasOneSet,
            x -> new AbgeschlossenesMatchState(),
            x -> new LaufendesMatchState(SpielerPunkteMatch.increment(laufendesMatch.spielerPunkteMatch()), laufendesMatch.gegnerPunkteMatch())
        );
    }

    public static MatchState gegnerPunktet(LaufendesMatchState laufendesMatch) {
        return condition(laufendesMatch.gegnerPunkteMatch().punkteMatchState(), PunkteMatch.hasOneSet,
            x -> new AbgeschlossenesMatchState(),
            x -> new LaufendesMatchState(laufendesMatch.spielerPunkteMatch(), GegnerPunkteMatch.incerement(laufendesMatch.gegnerPunkteMatch()))
        );
    }

    public static DomainEvent spielerGewinntSatz(LaufendesMatchState laufendesMatch) {
        return condition(laufendesMatch, passIfSpielerOneSatzBisMatch,
            x -> new SpielerMatchGewonnen(),
            x -> new SpielerSatzGewonnen()
        );
    }

    public static DomainEvent gegnerGewinntSatz(LaufendesMatchState laufendesMatch) {
        return condition(laufendesMatch, passIfSpielerOneSatzBisMatch,
            x -> new GegnerMatchGewonnen(),
            x -> new GegnerSatzGewonnen()
        );
    }
}
