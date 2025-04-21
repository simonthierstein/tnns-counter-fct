/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.matchstate;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cgame.domain.CGame;
import ch.sth.dojo.beh.cmatch.domain.state.MatchState;
import ch.sth.dojo.beh.csatz.domain.CSatz;
import io.vavr.Function3;
import io.vavr.Tuple;
import io.vavr.Tuple3;
import io.vavr.control.Either;
import java.util.function.Function;

public record GameMatchState(MatchState nextMatch, CSatz nextSatz, CGame nextGame) implements ch.sth.dojo.beh.matchstate.MatchState {

    private static Function3<Either<DomainProblem, MatchState>, Either<DomainProblem, CSatz>, Either<DomainProblem, CGame>, Either<DomainProblem, Tuple3<MatchState, CSatz, CGame>>> tuple3EithersToEitherTuple3 =
        (eith1, eith2, eith3) ->
            eith1.flatMap(match -> eith2.flatMap(satz -> eith3.map(game -> Tuple.of(match, satz, game))));

    static ch.sth.dojo.beh.matchstate.GameMatchState untuple(Tuple3<MatchState, CSatz, CGame> tuple3) {
        return tuple3.apply(ch.sth.dojo.beh.matchstate.GameMatchState::new);
    }

    ch.sth.dojo.beh.matchstate.MatchState apply(Function3<MatchState, CSatz, CGame, ch.sth.dojo.beh.matchstate.MatchState> applicative) {
        return applicative.apply(nextMatch, nextSatz, nextGame);
    }

    public Either<DomainProblem, ch.sth.dojo.beh.matchstate.GameMatchState> apply(
        Function<MatchState, Either<DomainProblem, MatchState>> matchFunction,
        Function<CSatz, Either<DomainProblem, CSatz>> satzFunction,
        Function<CGame, Either<DomainProblem, CGame>> gameFunction
    ) {
        return Tuple.of(nextMatch, nextSatz, nextGame)
            .map(matchFunction, satzFunction, gameFunction)
            .apply(tuple3EithersToEitherTuple3)
            .map(ch.sth.dojo.beh.matchstate.GameMatchState::untuple);
    }

    public Tuple3<MatchState, CSatz, CGame> tupled() {
        return Tuple.of(nextMatch, nextSatz, nextGame);
    }
}
