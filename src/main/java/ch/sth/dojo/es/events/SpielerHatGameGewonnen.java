/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.events;

public record SpielerHatGameGewonnen() implements DomainEvent {
    public static SpielerHatGameGewonnen spielerHatGameGewonnen() {
        return new SpielerHatGameGewonnen();
    }
}
