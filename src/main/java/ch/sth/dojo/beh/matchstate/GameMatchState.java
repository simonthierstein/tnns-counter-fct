/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.matchstate;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.game.domain.Game;
import ch.sth.dojo.beh.match.domain.TennisMatch;
import ch.sth.dojo.beh.satz.domain.Satz;
import io.vavr.Function3;
import io.vavr.Tuple;
import io.vavr.Tuple3;
import io.vavr.control.Either;
import java.util.function.Function;

public record GameMatchState(TennisMatch nextMatch, Satz nextSatz, Game nextGame) implements MatchState {

    private static Function3<Either<DomainProblem, TennisMatch>, Either<DomainProblem, Satz>, Either<DomainProblem, Game>, Either<DomainProblem, Tuple3<TennisMatch, Satz, Game>>> tuple3EithersToEitherTuple3 =
        (eith1, eith2, eith3) ->
            eith1.flatMap(match -> eith2.flatMap(satz -> eith3.map(game -> Tuple.of(match, satz, game))));

    static ch.sth.dojo.beh.matchstate.GameMatchState untuple(Tuple3<TennisMatch, Satz, Game> tuple3) {
        return tuple3.apply(ch.sth.dojo.beh.matchstate.GameMatchState::new);
    }

    MatchState apply(Function3<TennisMatch, Satz, Game, MatchState> applicative) {
        return applicative.apply(nextMatch, nextSatz, nextGame);
    }

    public Either<DomainProblem, ch.sth.dojo.beh.matchstate.GameMatchState> apply(
        Function<TennisMatch, Either<DomainProblem, TennisMatch>> matchFunction,
        Function<Satz, Either<DomainProblem, Satz>> satzFunction,
        Function<Game, Either<DomainProblem, Game>> gameFunction
    ) {
        return Tuple.of(nextMatch, nextSatz, nextGame)
            .map(matchFunction, satzFunction, gameFunction)
            .apply(tuple3EithersToEitherTuple3)
            .map(ch.sth.dojo.beh.matchstate.GameMatchState::untuple);
    }

    public Tuple3<TennisMatch, Satz, Game> tupled() {
        return Tuple.of(nextMatch, nextSatz, nextGame);
    }
}
