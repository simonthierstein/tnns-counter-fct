package ch.sth.dojo.beh.cgame.domain;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.PredicateUtils;
import io.vavr.control.Either;
import java.util.function.Predicate;

public record TiebreakSpielerPunkteBisGame(TiebreakPunkteBisGame punkteBisGame) {

    static final Predicate<TiebreakSpielerPunkteBisGame> eq1 = PredicateUtils.compose(TiebreakPunkteBisGame.eq1, TiebreakSpielerPunkteBisGame::punkteBisGame);

    static Either<DomainProblem, TiebreakSpielerPunkteBisGame> of(final Integer spielerPunkteBisGame) {
        return TiebreakPunkteBisGame.of(spielerPunkteBisGame)
            .map(TiebreakSpielerPunkteBisGame::new);
    }

    static TiebreakSpielerPunkteBisGame zero() {
        return new TiebreakSpielerPunkteBisGame(TiebreakPunkteBisGame.zero());
    }

    TiebreakSpielerPunkteBisGame decrement() {
        return new TiebreakSpielerPunkteBisGame(punkteBisGame.decrement());
    }
}
