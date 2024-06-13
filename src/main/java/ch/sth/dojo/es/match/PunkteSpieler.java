package ch.sth.dojo.es.match;

import io.vavr.control.Option;
import java.util.function.Function;
import java.util.function.Predicate;

public record PunkteSpieler(Punkte punkte) {

    public static Predicate<PunkteSpieler> passIfNotWon() {
        return ps -> Punkte.lteOne().test(ps.punkte);
    }

    public static PunkteSpieler punkteSpieler(final Punkte punkte) {
        return new PunkteSpieler(punkte);
    }

    public static PunkteSpieler zero() {
        return new PunkteSpieler(Punkte.zero());
    }

    public static Option<PunkteSpieler> fromInteger(final Integer anzPunkte) {
        return Option.of(anzPunkte)
                .flatMap(Punkte::ofInteger)
                .filter(Punkte.passIfInRange(0, 2))
                .map(PunkteSpieler::new);
    }

    public <T> T eval(Function<Punkte, T> mapper) {
        return mapper.apply(punkte);
    }

    public int evalToInt() {
        return eval(Punkte::asInteger);
    }

    public PunkteSpieler increment() {
        return new PunkteSpieler(Punkte.increment(punkte));
    }
}
