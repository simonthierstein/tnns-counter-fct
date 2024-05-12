/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.game.trans;

import static ch.sth.dojo.es.events.GegnerHatGameGewonnen.gegnerHatGameGewonnen;
import static ch.sth.dojo.es.events.GegnerHatPunktGewonnen.gegnerHatPunktGewonnen;
import static ch.sth.dojo.es.events.SpielerHatGameGewonnen.spielerHatGameGewonnen;
import static ch.sth.dojo.es.events.SpielerHatPunktGewonnen.spielerHatPunktGewonnen;
import static ch.sth.dojo.es.game.Punkt.punkt;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.events.GegnerHatGameGewonnen;
import ch.sth.dojo.es.events.GegnerHatPunktGewonnen;
import ch.sth.dojo.es.events.SpielerHatGameGewonnen;
import ch.sth.dojo.es.events.SpielerHatPunktGewonnen;
import ch.sth.dojo.es.game.LaufendesGame;
import ch.sth.dojo.es.game.Punkt;
import io.vavr.collection.List;
import io.vavr.control.Either;
import java.util.function.Function;

public class Laufend2Laufend {

    public static Function<LaufendesGame, Either<DomainError, DomainEvent>> handleSpielerPunktet() {
        return Laufend2Laufend::handleSpielerPunktet;
    }

    private static Either<DomainError, DomainEvent> handleSpielerPunktet(final LaufendesGame prev) {
        return Either.right(doSpielerPunktet(prev));
    }

    public static SpielerHatGameGewonnen SpielerHatGameGewonnen(final List<Punkt> punkteSpieler,
                                                                final List<Punkt> punkteGegner) {
        return spielerHatGameGewonnen(punkteSpieler.size(), punkteGegner.size());
    }

    public static SpielerHatPunktGewonnen SpielerHatPunktGewonnen(final List<Punkt> punkteSpieler, final List<Punkt> punkteGegner) {
        return spielerHatPunktGewonnen();
    }

    public static GegnerHatPunktGewonnen GegnerHatPunktGewonnen(final List<Punkt> punkteSpieler, final List<Punkt> punkteGegner) {
        return gegnerHatPunktGewonnen();
    }

    public static GegnerHatGameGewonnen GegnerHatGameGewonnen(final List<Punkt> punkteSpieler,
                                                              final List<Punkt> punkteGegner) {
        return gegnerHatGameGewonnen();
    }

    private static DomainEvent doSpielerPunktet(LaufendesGame prev) {
        final List<Punkt> incremented = prev.punkteSpieler.append(punkt());
        return incremented.size() == 4
                ? SpielerHatGameGewonnen(incremented, prev.punkteGegner)
                : SpielerHatPunktGewonnen(incremented, prev.punkteGegner);
    }

    private static DomainEvent doGegnerPunktet(LaufendesGame prev) {
        final List<Punkt> incremented = prev.punkteGegner.append(punkt());
        return incremented.size() == 4
                ? GegnerHatGameGewonnen(prev.punkteSpieler, incremented)
                : GegnerHatPunktGewonnen(prev.punkteSpieler, incremented);
    }

    public static Function<GegnerHatPunktGewonnen, LaufendesGame> ghpg(LaufendesGame prev) {
        return event -> LaufendesGame.laufendesGame(prev.punkteSpieler, prev.punkteGegner.append(punkt()));
    }

    public static Function<SpielerHatPunktGewonnen, LaufendesGame> shpg(LaufendesGame prev) {
        return event -> LaufendesGame.laufendesGame(prev.punkteSpieler.append(punkt()), prev.punkteGegner);
    }
}
