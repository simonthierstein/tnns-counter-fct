package ch.sth.dojo.beh.csatz.domain;

import java.util.function.Predicate;

public record GegnerPunkteSatz(Integer value) {

    public static final Predicate<GegnerPunkteSatz> lte4 = in -> in.value <= 4;
    public static final Predicate<GegnerPunkteSatz> lte5 = in -> in.value <= 5;
    public static final Predicate<GegnerPunkteSatz> eq5 = in -> in.value == 5;
    public static final Predicate<GegnerPunkteSatz> eq6 = in -> in.value == 6;
    
    static GegnerPunkteSatz zero() {
        return new GegnerPunkteSatz(0);
    }

    boolean isOne() {
        return value == 1;
    }
}
