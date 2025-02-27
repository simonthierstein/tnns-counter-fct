package ch.sth.dojo.beh.cgame.domain;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.PredicateUtils;
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.util.function.Predicate;

public record TiebreakGegnerPunkteBisGame(TiebreakPunkteBisGame tiebreakPunkteBisGame) {

    static final Predicate<TiebreakGegnerPunkteBisGame> eq1 = PredicateUtils.compose(TiebreakPunkteBisGame.eq1, TiebreakGegnerPunkteBisGame::tiebreakPunkteBisGame);

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

    TiebreakGegnerPunkteBisGame adaptTo(final TiebreakSpielerPunkteBisGame decremented) {
        return Option.some(decremented)
            .filter(TiebreakSpielerPunkteBisGame.eq1)
            .map(x -> increment())
            .getOrElse(this);
    }

    private TiebreakGegnerPunkteBisGame increment() {
        return new TiebreakGegnerPunkteBisGame(tiebreakPunkteBisGame.increment());
    }
}
