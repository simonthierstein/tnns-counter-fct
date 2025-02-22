package ch.sth.dojo.beh.cgame.domain;

import static ch.sth.dojo.beh.cgame.domain.PunkteBisGame.PunkteBisGame;

import ch.sth.dojo.beh.DomainProblem;
import io.vavr.control.Either;
import java.util.function.Function;
import java.util.function.Predicate;

public record SpielerPunkteBisGame(Integer value) {

    public static Predicate<SpielerPunkteBisGame> passIfOnePunktBisGame = spielerPunkteBisGame -> spielerPunkteBisGame.value == 1;

    static SpielerPunkteBisGame zero() {
        return new SpielerPunkteBisGame(4);
    }

    static Either<DomainProblem, SpielerPunkteBisGame> SpielerPunkteBisGame(Integer value) {
        return PunkteBisGame(value, SpielerPunkteBisGame::new);
    }

    public <T> T map(Function<Integer, T> mapper) {
        return mapper.apply(value);
    }
}

