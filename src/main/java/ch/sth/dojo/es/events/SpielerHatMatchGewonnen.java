/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.events;

public record SpielerHatMatchGewonnen() implements DomainEvent {
    public static SpielerHatMatchGewonnen spielerHatMatchGewonnen() {
        return new SpielerHatMatchGewonnen();
    }
}
