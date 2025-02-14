package ch.sth.dojo.beh.cgame.domain;

import java.util.function.Function;
import java.util.function.Predicate;

public record GegnerPunkteBisGame(Integer value) {

    public static Predicate<GegnerPunkteBisGame> passIfOnePunktBisGame = gegnerPunkteBisGame -> gegnerPunkteBisGame.value == 1;

    static GegnerPunkteBisGame zero() {
        return new GegnerPunkteBisGame(0);
    }

    public <T> T map(Function<Integer, T> mapper) {
        return mapper.apply(value);
    }
}
