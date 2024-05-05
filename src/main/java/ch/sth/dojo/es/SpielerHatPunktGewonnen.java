/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es;

public final class SpielerHatPunktGewonnen implements DomainEvent {
    public static SpielerHatPunktGewonnen spielerHatPunktGewonnen() {
        return new SpielerHatPunktGewonnen();
    }
}
