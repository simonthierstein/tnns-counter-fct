package ch.sth.dojo.beh.match.domain;

import static ch.sth.dojo.beh.PredicateUtils.compose;
import static ch.sth.dojo.beh.match.domain.PunkteMatch.hasTwoSets;

import java.util.function.Predicate;

public record SpielerPunkteMatch(PunkteMatch punkteMatch) {

    static Predicate<SpielerPunkteMatch> hasWon = compose(hasTwoSets, SpielerPunkteMatch::punkteMatch);

    static SpielerPunkteMatch zero() {
        return new SpielerPunkteMatch(PunkteMatch.zero());
    }

    SpielerPunkteMatch increment() {
        return new SpielerPunkteMatch(punkteMatch.increment());
    }
}
