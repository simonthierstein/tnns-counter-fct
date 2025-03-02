package ch.sth.dojo.beh.cmatch.domain;

import static ch.sth.dojo.beh.PredicateUtils.compose;
import static ch.sth.dojo.beh.cmatch.domain.PunkteMatch.hasTwoSets;

import java.util.function.Predicate;

public record GegnerPunkteMatch(PunkteMatch punkteMatch) {

    static Predicate<GegnerPunkteMatch> hasWon = compose(hasTwoSets, GegnerPunkteMatch::punkteMatch);

    static GegnerPunkteMatch zero() {
        return new GegnerPunkteMatch(PunkteMatch.zero());
    }

    GegnerPunkteMatch incerement() {
        return new GegnerPunkteMatch(punkteMatch.increment());
    }
}
