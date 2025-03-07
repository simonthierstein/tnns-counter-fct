package ch.sth.dojo.beh.cgame.domain;

import ch.sth.dojo.beh.DomainProblem;
import static ch.sth.dojo.beh.PredicateUtils.compose;
import static ch.sth.dojo.beh.PredicateUtils.eq;
import static ch.sth.dojo.beh.PredicateUtils.gte;
import io.vavr.Predicates;
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.util.function.Predicate;

public record TiebreakPunkteBisGame(Integer value) {

    static Predicate<TiebreakPunkteBisGame> eq1 = compose(eq(1), TiebreakPunkteBisGame::value);

    static Either<DomainProblem, TiebreakPunkteBisGame> of(final Integer punkteBisGame) {
        return Option.of(punkteBisGame)
            .filter(Predicates.allOf(gte(0)))
            .map(TiebreakPunkteBisGame::new)
            .toEither(DomainProblem.valueNotValid);
    }

    static TiebreakPunkteBisGame zero() {
        return new TiebreakPunkteBisGame(7);
    }

    TiebreakPunkteBisGame decrement() {
        return new TiebreakPunkteBisGame(value - 1);
    }

    TiebreakPunkteBisGame increment() {
        return new TiebreakPunkteBisGame(value + 1);
    }

}