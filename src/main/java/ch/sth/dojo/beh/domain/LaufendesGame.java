/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.domain;

public record LaufendesGame(SpielerPunktestand spielerPunktestand, GegnerPunktestand gegnerPunktestand) implements Game {

}
