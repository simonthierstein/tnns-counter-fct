package ch.sth.dojo.es.match;

import java.util.function.Predicate;

public record PunkteSpieler(Punkte punkte) {

    static Predicate<PunkteSpieler> passIfNotWon() {
        return ps -> Punkte.lteOne().test(ps.punkte);
    }

    int current() {
        return punkte.asInteger();
    }

    PunkteSpieler increment() {
        return new PunkteSpieler(punkte.increment());
    }
}
