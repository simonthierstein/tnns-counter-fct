package ch.sth.dojo.beh.domain;

import java.util.function.Function;
import java.util.function.Predicate;

public record GegnerPunktestand(Punkte punkte) {

    static GegnerPunktestand zero() {
        return new GegnerPunktestand(Punkte.zero());
    }

    public <T> T eval(Function<Punkte, T> mapper) {
        return mapper.apply(punkte);
    }

    Predicate<GegnerPunktestand> asPredicate(final Predicate<Punkte> punktePredicate) {
        return gegnerPunktestand -> punktePredicate.test(gegnerPunktestand.punkte);
    }

}
