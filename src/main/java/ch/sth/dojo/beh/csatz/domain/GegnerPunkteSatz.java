package ch.sth.dojo.beh.csatz.domain;

public record GegnerPunkteSatz(Integer value) {

    static GegnerPunkteSatz zero() {
        return new GegnerPunkteSatz(0);
    }
}
