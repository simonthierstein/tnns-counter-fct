package ch.sth.dojo.beh.domain;

import java.util.function.Predicate;

public record SpielerPunktestand(Punkte punkte) {

    static SpielerPunktestand zero() {
        return new SpielerPunktestand(Punkte.zero());
    }

    Predicate<SpielerPunktestand> asPredicate(final Predicate<Punkte> punktePredicate) {
        return spielerPunktestand -> punktePredicate.test(spielerPunktestand.punkte);
    }
}
