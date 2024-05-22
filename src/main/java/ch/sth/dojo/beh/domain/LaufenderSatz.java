/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.domain;

public record LaufenderSatz(SpielerSatzPunktestand spielerSatzPunktestand, GegnerSatzPunktestand gegnerSatzPunktestand) implements Satz {

}
