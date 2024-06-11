/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.scoring;

import ch.sth.dojo.es.Identity;
import ch.sth.dojo.es.game.Game;
import ch.sth.dojo.es.match.StandardMatch;
import ch.sth.dojo.es.satz.Satz;

public record StandardScoring(CurrentGame currentGame, CurrentSatz currentSatz, StandardMatch match) {
    public static StandardScoring standardScoring(final CurrentGame currentGame, final CurrentSatz currentSatz, final StandardMatch match) {
        return new StandardScoring(currentGame, currentSatz, match);
    }

    public static StandardScoring zero() {
        return new StandardScoring(CurrentGame.zero(), CurrentSatz.zero(), StandardMatch.zero());
    }

    public static StandardScoring prepareNext(final StandardScoring current) {
        final Game game = Game.prepareNext(current.currentGame.current());
        final Satz satz = Satz.prepareNext(current.currentSatz.current());

        return Identity.unit(current)
                .map(prev -> StandardScoring.standardScoring(CurrentGame.currentGame(game), CurrentSatz.currentSatz(satz), prev.match))
                .eval();
    }
}

