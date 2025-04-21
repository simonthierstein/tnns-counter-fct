/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cmatch.domain.state;

public record LaufendesMatchState(SpielerPunkteMatchState spielerPunkteMatch, GegnerPunkteMatchState gegnerPunkteMatch) implements MatchState {

}
