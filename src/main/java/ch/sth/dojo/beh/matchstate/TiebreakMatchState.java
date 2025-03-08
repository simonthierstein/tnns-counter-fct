/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.matchstate;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cmatch.domain.CMatch;
import ch.sth.dojo.beh.csatz.domain.CSatz;
import ch.sth.dojo.beh.tiebreak.domain.Tiebreak;
import io.vavr.Function3;
import io.vavr.Tuple;
import io.vavr.Tuple3;
import io.vavr.control.Either;
import java.util.function.Function;

public record TiebreakMatchState(CMatch nextMatch, CSatz nextSatz, Tiebreak tiebreak) implements MatchState {

    private static Function3<Either<DomainProblem, CMatch>, Either<DomainProblem, CSatz>, Either<DomainProblem, Tiebreak>, Either<DomainProblem, Tuple3<CMatch, CSatz, Tiebreak>>> tuple3EithersToEitherTuple3 =
        (eith1, eith2, eith3) ->
            eith1.flatMap(match -> eith2.flatMap(satz -> eith3.map(game -> Tuple.of(match, satz, game))));

    static ch.sth.dojo.beh.matchstate.TiebreakMatchState untuple(Tuple3<CMatch, CSatz, Tiebreak> tuple3) {
        return tuple3.apply(ch.sth.dojo.beh.matchstate.TiebreakMatchState::new);
    }

    public Either<DomainProblem, ch.sth.dojo.beh.matchstate.TiebreakMatchState> apply(
        Function<CMatch, Either<DomainProblem, CMatch>> matchFunction,
        Function<CSatz, Either<DomainProblem, CSatz>> satzFunction,
        Function<Tiebreak, Either<DomainProblem, Tiebreak>> gameFunction
    ) {
        return Tuple.of(nextMatch, nextSatz, tiebreak).map(matchFunction, satzFunction, gameFunction)
            .apply(tuple3EithersToEitherTuple3)
            .map(ch.sth.dojo.beh.matchstate.TiebreakMatchState::untuple);
    }

    public Tuple3<CMatch, CSatz, Tiebreak> tupled() {
        return Tuple.of(nextMatch, nextSatz, tiebreak);
    }
}
