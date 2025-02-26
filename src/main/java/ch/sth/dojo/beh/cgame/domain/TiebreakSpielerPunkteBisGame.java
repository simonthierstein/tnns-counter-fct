package ch.sth.dojo.beh.cgame.domain;

import ch.sth.dojo.beh.DomainProblem;
import io.vavr.control.Either;

public record TiebreakSpielerPunkteBisGame(TiebreakPunkteBisGame punkteBisGame) {

    static Either<DomainProblem, TiebreakSpielerPunkteBisGame> of(final Integer spielerPunkteBisGame) {
        return TiebreakPunkteBisGame.of(spielerPunkteBisGame)
            .map(TiebreakSpielerPunkteBisGame::new);
    }

    TiebreakSpielerPunkteBisGame decrement() {
        return new TiebreakSpielerPunkteBisGame(punkteBisGame.decrement());
    }
}
