package ch.sth.dojo.beh.csatz.domain;

public record SpielerPunkteSatz(Integer value) {

    static SpielerPunkteSatz zero() {
        return new SpielerPunkteSatz(0);
    }

    boolean isOne() {
        return value == 1;
    }
}
