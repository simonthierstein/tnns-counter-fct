package ch.sth.dojo.beh.cgame.domain.game;

import java.util.function.Function;
import java.util.function.Predicate;

public record SpielerPunkteBisGame(Integer value) {

    public static Predicate<SpielerPunkteBisGame> passIfOnePunktBisGame = spielerPunkteBisGame -> spielerPunkteBisGame.value == 1;

    static SpielerPunkteBisGame zero() {
        return new SpielerPunkteBisGame(0);
    }

    public <T> T map(Function<Integer, T> mapper) {
        return mapper.apply(value);
    }
}
