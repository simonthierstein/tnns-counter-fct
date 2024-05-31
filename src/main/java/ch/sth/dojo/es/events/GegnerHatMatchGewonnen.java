/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.events;

public record GegnerHatMatchGewonnen() implements DomainEvent {
    public static GegnerHatMatchGewonnen spielerHatSatzGewonnen() {
        return new GegnerHatMatchGewonnen();
    }
}
