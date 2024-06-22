/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.game.evt;

import static ch.sth.dojo.es.game.AbgeschlossenesGame.abgeschlossenesGame;
import static ch.sth.dojo.es.game.Punkt.punkt;

import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.events.GegnerHatGameGewonnen;
import ch.sth.dojo.es.events.GegnerHatMatchGewonnen;
import ch.sth.dojo.es.events.GegnerHatSatzGewonnen;
import ch.sth.dojo.es.events.SpielerHatGameGewonnen;
import ch.sth.dojo.es.events.SpielerHatMatchGewonnen;
import ch.sth.dojo.es.events.SpielerHatSatzGewonnen;
import ch.sth.dojo.es.game.AbgeschlossenesGame;
import ch.sth.dojo.es.game.LaufendesGame;
import io.vavr.Function2;
import java.util.function.Function;

public class Laufend2AbgeschlossenEventHandler {

    public static Function<SpielerHatGameGewonnen, AbgeschlossenesGame> shgg(LaufendesGame prev) {
        return evt -> incrementGameSpieler(prev).apply(evt);
    }

    public static Function<GegnerHatGameGewonnen, AbgeschlossenesGame> ghgg(LaufendesGame prev) {
        return evt -> incrementGameGegner(prev).apply(evt);
    }

    public static Function<SpielerHatSatzGewonnen, AbgeschlossenesGame> shsg(LaufendesGame prev) {
        return evt -> incrementGameSpieler(prev).apply(evt);
    }

    public static Function<GegnerHatSatzGewonnen, AbgeschlossenesGame> ghsg(LaufendesGame prev) {
        return evt -> incrementGameGegner(prev).apply(evt);
    }

    public static Function<SpielerHatMatchGewonnen, AbgeschlossenesGame> shmg(LaufendesGame prev) {
        return evt -> incrementGameSpieler(prev).apply(evt);
    }

    public static Function<GegnerHatMatchGewonnen, AbgeschlossenesGame> ghmg(LaufendesGame prev) {
        return evt -> incrementGameGegner(prev).apply(evt);
    }

    private static Function<DomainEvent, AbgeschlossenesGame> incrementGameGegner(final LaufendesGame prev) {
        return event -> abgeschlossenesGame(prev.punkteSpieler, prev.punkteGegner.append(punkt()));
    }

    private static Function<DomainEvent, AbgeschlossenesGame> incrementGameSpieler(final LaufendesGame prev) {
        return event -> abgeschlossenesGame(prev.punkteSpieler.append(punkt()), prev.punkteGegner);
    }

    public static Function2<LaufendesGame, SpielerHatGameGewonnen, AbgeschlossenesGame> toAbgeschlossenSpieler() {
        return (prev, event) -> shgg(prev).apply(event);
    }
}
