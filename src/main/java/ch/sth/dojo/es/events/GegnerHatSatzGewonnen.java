/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.events;

public record GegnerHatSatzGewonnen() implements DomainEvent {
    public static GegnerHatSatzGewonnen gegnerHatSatzGewonnen() {
        return new GegnerHatSatzGewonnen();
    }
}
