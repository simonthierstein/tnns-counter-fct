package ch.sth.dojo.beh.cgame.domain;

import java.util.function.Function;
import java.util.function.Predicate;

public record SpielerPunkteBisGame(Integer value) {

    public static Predicate<SpielerPunkteBisGame> passIfOnePunktBisGame = spielerPunkteBisGame -> spielerPunkteBisGame.value == 1;

    static SpielerPunkteBisGame zero() {
        return new SpielerPunkteBisGame(4);
    }

    public <T> T map(Function<Integer, T> mapper) {
        return mapper.apply(value);
    }
}
