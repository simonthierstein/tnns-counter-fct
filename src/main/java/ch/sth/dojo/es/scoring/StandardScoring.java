/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.scoring;

import ch.sth.dojo.es.match.StandardMatch;

public record StandardScoring(CurrentGame currentGame, CurrentSatz currentSatz, StandardMatch match) {
    public static StandardScoring standardScoring(final CurrentGame currentGame, final CurrentSatz currentSatz, final StandardMatch match) {
        return new StandardScoring(currentGame, currentSatz, match);
    }

    public static StandardScoring zero() {
        return new StandardScoring(CurrentGame.zero(), CurrentSatz.zero(), StandardMatch.zero());
    }
}

