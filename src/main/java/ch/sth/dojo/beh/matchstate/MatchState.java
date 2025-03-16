/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.matchstate;

import ch.sth.dojo.beh.cgame.domain.CGame;
import ch.sth.dojo.beh.cmatch.domain.CMatch;
import ch.sth.dojo.beh.csatz.domain.CSatz;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;
import java.util.function.Function;

public interface MatchState {

    static MatchState zero() {
        return gameMatchState(CMatch.zero(), CSatz.zero(), CGame.zero());
    }

    static GameMatchState gameMatchState(final CMatch nextMatch, final CSatz nextSatz, final CGame nextGame) {
        return new GameMatchState(nextMatch, nextSatz, nextGame);
    }

    default <T> T apply(Function<GameMatchState, T> gameMatchStateTFunction) {
        return Match(this).of(
            Case($(instanceOf(GameMatchState.class)), gameMatchStateTFunction)
        );
    }

}
