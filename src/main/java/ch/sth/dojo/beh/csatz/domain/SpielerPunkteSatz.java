package ch.sth.dojo.beh.csatz.domain;

import java.util.function.Predicate;

public record SpielerPunkteSatz(Integer value) {

    public static final Predicate<SpielerPunkteSatz> lte4 = in -> in.value <= 4;
    public static final Predicate<SpielerPunkteSatz> lte5 = in -> in.value <= 5;
    public static final Predicate<SpielerPunkteSatz> eq5 = in -> in.value == 5;
    public static final Predicate<SpielerPunkteSatz> eq6 = in -> in.value == 6;

    static SpielerPunkteSatz zero() {
        return new SpielerPunkteSatz(0);
    }

    boolean isOne() {
        return value == 1;
    }
}
