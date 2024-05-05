/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.events;

public record SpielerHatPunktGewonnen() implements DomainEvent {
    public static SpielerHatPunktGewonnen spielerHatPunktGewonnen() {
        return new SpielerHatPunktGewonnen();
    }
}
