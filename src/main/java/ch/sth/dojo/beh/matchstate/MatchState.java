/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.matchstate;

import ch.sth.dojo.beh.cgame.domain.CGame;
import ch.sth.dojo.beh.cmatch.domain.CMatch;
import ch.sth.dojo.beh.csatz.domain.CSatz;
import ch.sth.dojo.beh.tiebreak.domain.Tiebreak;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;
import java.util.function.Function;

public interface MatchState {

    default <T> T apply(Function<GameMatchState, T> gameMatchStateTFunction,
        Function<TiebreakMatchState, T> tiebreakMatchStateTFunction) {
        return Match(this).of(
            Case($(instanceOf(GameMatchState.class)), gameMatchStateTFunction),
            Case($(instanceOf(TiebreakMatchState.class)), tiebreakMatchStateTFunction)
        );
    }

    static GameMatchState gameMatchState(final CMatch nextMatch, final CSatz nextSatz, final CGame nextGame) {
        return new GameMatchState(nextMatch, nextSatz, nextGame);
    }

    static TiebreakMatchState tiebreakMatchState(final CMatch nextMatch, final CSatz nextSatz, final Tiebreak tiebreak) {
        return new TiebreakMatchState(nextMatch, nextSatz, tiebreak);
    }
}
