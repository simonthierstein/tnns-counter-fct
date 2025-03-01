package ch.sth.dojo.beh.cmatch.domain;

public record SpielerPunkteMatch(PunkteMatch punkteMatch) {

    static SpielerPunkteMatch zero() {
        return new SpielerPunkteMatch(PunkteMatch.zero());
    }

    SpielerPunkteMatch increment() {
        return new SpielerPunkteMatch(punkteMatch.increment());
    }
}
