package ch.sth.dojo.beh.cgame.domain;

import ch.sth.dojo.beh.DomainProblem;
import io.vavr.control.Either;

public record TiebreakGegnerPunkteBisGame(TiebreakPunkteBisGame tiebreakPunkteBisGame) {

    static Either<DomainProblem, TiebreakGegnerPunkteBisGame> of(final Integer gegnerPunkteBisGame) {
        return TiebreakPunkteBisGame.of(gegnerPunkteBisGame)
            .map(TiebreakGegnerPunkteBisGame::new);
    }

    static TiebreakGegnerPunkteBisGame zero() {
        return new TiebreakGegnerPunkteBisGame(TiebreakPunkteBisGame.zero());
    }

    TiebreakGegnerPunkteBisGame decrement() {
        return new TiebreakGegnerPunkteBisGame(tiebreakPunkteBisGame.decrement());
    }
}
