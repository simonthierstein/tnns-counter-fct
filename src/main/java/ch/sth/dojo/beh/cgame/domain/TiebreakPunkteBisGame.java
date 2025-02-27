package ch.sth.dojo.beh.cgame.domain;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.PredicateUtils;
import io.vavr.Predicates;
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.util.function.Predicate;

public record TiebreakPunkteBisGame(Integer value) {

    private static final Predicate<Integer> ieq1 = x -> x == 1;
    private static final Predicate<Integer> gte1 = x -> x >= 1;
    private static final Predicate<Integer> lte7 = x -> x <= 7;

    static Predicate<TiebreakPunkteBisGame> eq1 = PredicateUtils.compose(ieq1, TiebreakPunkteBisGame::value);

    static Either<DomainProblem, TiebreakPunkteBisGame> of(final Integer punkteBisGame) {
        return Option.of(punkteBisGame)
            .filter(Predicates.allOf(gte1, lte7))
            .map(TiebreakPunkteBisGame::new)
            .toEither(DomainProblem.valueNotValid);
    }

    static TiebreakPunkteBisGame zero() {
        return new TiebreakPunkteBisGame(7);
    }

    TiebreakPunkteBisGame decrement() {
        return new TiebreakPunkteBisGame(value - 1);
    }
}
