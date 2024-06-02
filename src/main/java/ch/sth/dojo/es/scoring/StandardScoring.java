/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.scoring;

import ch.sth.dojo.es.match.StandardMatch;

public record StandardScoring(CurrentGame currentGame, CurrentSet currentSet, StandardMatch match) {
}
