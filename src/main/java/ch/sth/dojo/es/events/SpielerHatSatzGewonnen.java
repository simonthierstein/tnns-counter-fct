/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.events;

public record SpielerHatSatzGewonnen() implements DomainEvent {
    public static SpielerHatSatzGewonnen spielerHatSatzGewonnen() {
        return new SpielerHatSatzGewonnen();
    }
}
