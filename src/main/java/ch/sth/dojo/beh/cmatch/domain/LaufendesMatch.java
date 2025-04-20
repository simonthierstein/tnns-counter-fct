/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cmatch.domain;

import static ch.sth.dojo.beh.Condition.condition;
import static ch.sth.dojo.beh.PredicateUtils.compose;

import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.GegnerMatchGewonnen;
import ch.sth.dojo.beh.evt.GegnerSatzGewonnen;
import ch.sth.dojo.beh.evt.SpielerMatchGewonnen;
import ch.sth.dojo.beh.evt.SpielerSatzGewonnen;
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

    public DomainEvent spielerGewinntSatz() {
        return condition(this, passIfSpielerOneSatzBisMatch,
            x -> new SpielerMatchGewonnen(),
            x -> new SpielerSatzGewonnen()
        );
    }

    public DomainEvent gegnerGewinntSatz() {
        return condition(this, passIfSpielerOneSatzBisMatch,
            x -> new GegnerMatchGewonnen(),
            x -> new GegnerSatzGewonnen()
        );
    }
}
