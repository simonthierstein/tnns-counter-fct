package ch.sth.dojo.beh.cgame.domain;

import static ch.sth.dojo.beh.cgame.domain.PunkteBisGame.PunkteBisGame;

import ch.sth.dojo.beh.DomainProblem;
import io.vavr.control.Either;
import java.util.function.Function;
import java.util.function.Predicate;

public record GegnerPunkteBisGame(Integer value) {

    public static final Predicate<GegnerPunkteBisGame> passIfOnePunktBisGame = gegnerPunkteBisGame -> gegnerPunkteBisGame.value == 1;

    static GegnerPunkteBisGame zero() {
        return new GegnerPunkteBisGame(4);
    }

    static Either<DomainProblem, GegnerPunkteBisGame> GegnerPunkteBisGame(Integer value) {
        return PunkteBisGame(value, GegnerPunkteBisGame::new);
    }

    public <T> T map(Function<Integer, T> mapper) {
        return mapper.apply(value);
    }

}
