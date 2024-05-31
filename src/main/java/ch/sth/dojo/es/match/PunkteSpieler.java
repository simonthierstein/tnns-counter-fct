package ch.sth.dojo.es.match;

import java.util.function.Predicate;

public record PunkteSpieler(Punkte punkte) {

    public static Predicate<PunkteSpieler> passIfNotWon() {
        return ps -> Punkte.lteOne().test(ps.punkte);
    }

    public int current() {
        return punkte.asInteger();
    }

    public PunkteSpieler increment() {
        return new PunkteSpieler(punkte.increment());
    }
}
