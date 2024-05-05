/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es;

public final class GegnerHatPunktGewonnen implements DomainEvent {
    static GegnerHatPunktGewonnen gegnerHatPunktGewonnen() {
        return new GegnerHatPunktGewonnen();
    }
}
