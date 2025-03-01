package ch.sth.dojo.beh.cmatch.domain;

public record GegnerPunkteMatch(PunkteMatch punkteMatch) {

    static GegnerPunkteMatch zero() {
        return new GegnerPunkteMatch(PunkteMatch.zero());
    }

    GegnerPunkteMatch incerement() {
        return new GegnerPunkteMatch(punkteMatch.increment());
    }
}
