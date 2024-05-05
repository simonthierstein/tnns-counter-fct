/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.events;

public record GegnerHatPunktGewonnen() implements DomainEvent {
    public static GegnerHatPunktGewonnen gegnerHatPunktGewonnen() {
        return new GegnerHatPunktGewonnen();
    }
}
