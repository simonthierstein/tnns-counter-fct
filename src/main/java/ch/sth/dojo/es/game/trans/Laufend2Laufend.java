/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.game.trans;

import static ch.sth.dojo.es.events.GegnerHatPunktGewonnen.gegnerHatPunktGewonnen;
import static ch.sth.dojo.es.events.SpielerHatPunktGewonnen.spielerHatPunktGewonnen;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.events.GegnerHatPunktGewonnen;
import ch.sth.dojo.es.events.SpielerHatPunktGewonnen;
import ch.sth.dojo.es.game.LaufendesGame;
import ch.sth.dojo.es.game.Punkt;
import io.vavr.collection.List;
import io.vavr.control.Either;
import java.util.function.Function;

public class Laufend2Laufend {

    public static Function<GegnerHatPunktGewonnen, LaufendesGame> ghpg(LaufendesGame prev) {
        return event -> LaufendesGame.laufendesGame(prev.punkteSpieler, LaufendesGame.increment(prev.punkteGegner));
    }

    public static Function<SpielerHatPunktGewonnen, LaufendesGame> shpg(LaufendesGame prev) {
        return event -> LaufendesGame.laufendesGame(LaufendesGame.increment(prev.punkteSpieler), prev.punkteGegner);
    }

    public static Function<LaufendesGame, Either<DomainError, DomainEvent>> spielerGewinnePunkt() {
        return laufendesGame -> Either.right(SpielerHatPunktGewonnen(LaufendesGame.increment(laufendesGame.punkteSpieler), laufendesGame.punkteGegner));
    }

    public static Function<LaufendesGame, Either<DomainError, DomainEvent>> gegnerGewinnePunkt() {
        return laufendesGame -> Either.right(GegnerHatPunktGewonnen(laufendesGame.punkteSpieler, LaufendesGame.increment(laufendesGame.punkteGegner)));
    }

    private static SpielerHatPunktGewonnen SpielerHatPunktGewonnen(final List<Punkt> punkteSpieler, final List<Punkt> punkteGegner) {
        return spielerHatPunktGewonnen();
    }

    private static GegnerHatPunktGewonnen GegnerHatPunktGewonnen(final List<Punkt> punkteSpieler, final List<Punkt> punkteGegner) {
        return gegnerHatPunktGewonnen();
    }
}
