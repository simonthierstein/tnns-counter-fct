/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.game.trans;

import static ch.sth.dojo.es.game.Punkt.punkt;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.game.LaufendesGame;
import ch.sth.dojo.es.game.Punkt;
import io.vavr.collection.List;
import io.vavr.control.Either;
import java.util.function.Function;

public class Laufend2X {


    public static Function<LaufendesGame, Either<DomainError, DomainEvent>> handleSpielerPunktet() {
        return Laufend2X::handleSpielerPunktet;
    }

    private static Either<DomainError, DomainEvent> handleSpielerPunktet(final LaufendesGame prev) {
        return Either.right(doSpielerPunktet(prev));
    }

    private static DomainEvent doSpielerPunktet(LaufendesGame prev) {
        final List<Punkt> incremented = prev.punkteSpieler.append(punkt());
        return incremented.size() == 4
                ? Laufend2Abgeschlossen.SpielerHatGameGewonnen(incremented, prev.punkteGegner)
                : Laufend2Laufend.SpielerHatPunktGewonnen(incremented, prev.punkteGegner);
    }

    private static DomainEvent doGegnerPunktet(LaufendesGame prev) {
        final List<Punkt> incremented = prev.punkteGegner.append(punkt());
        return incremented.size() == 4
                ? Laufend2Abgeschlossen.GegnerHatGameGewonnen(prev.punkteSpieler, incremented)
                : Laufend2Laufend.GegnerHatPunktGewonnen(prev.punkteSpieler, incremented);
    }
}
