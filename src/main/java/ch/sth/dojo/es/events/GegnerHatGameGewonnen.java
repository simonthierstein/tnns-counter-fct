/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.events;

public final class GegnerHatGameGewonnen implements DomainEvent {
    public static GegnerHatGameGewonnen gegnerHatGameGewonnen() {
        return new GegnerHatGameGewonnen();
    }
}
