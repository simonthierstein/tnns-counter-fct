/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.matchstate;

import ch.sth.dojo.beh.game.domain.Game;
import ch.sth.dojo.beh.cmatch.domain.Match;
import ch.sth.dojo.beh.satz.domain.Satz;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;
import java.util.function.Function;

public interface MatchState {

    static MatchState zero() {
        return gameMatchState(Match.zero(), Satz.zero(), Game.zero());
    }

    static GameMatchState gameMatchState(final Match nextMatch, final Satz nextSatz, final Game nextGame) {
        return new GameMatchState(nextMatch, nextSatz, nextGame);
    }

    default <T> T apply(Function<GameMatchState, T> gameMatchStateTFunction) {
        return Match(this).of(
            Case($(instanceOf(GameMatchState.class)), gameMatchStateTFunction)
        );
    }

}
