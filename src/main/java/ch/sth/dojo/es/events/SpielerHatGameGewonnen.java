/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.events;

public record SpielerHatGameGewonnen(int punkteSpieler, int punkteGegner) implements DomainEvent {
    public static SpielerHatGameGewonnen spielerHatGameGewonnen(int punkteSpieler, int punkteGegner) {
        return new SpielerHatGameGewonnen(punkteSpieler, punkteGegner);
    }
}
