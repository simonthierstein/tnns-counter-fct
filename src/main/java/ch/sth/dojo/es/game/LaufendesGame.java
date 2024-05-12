/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.game;

import static ch.sth.dojo.es.events.GegnerHatGameGewonnen.gegnerHatGameGewonnen;
import static ch.sth.dojo.es.events.GegnerHatPunktGewonnen.gegnerHatPunktGewonnen;
import static ch.sth.dojo.es.events.SpielerHatGameGewonnen.spielerHatGameGewonnen;
import static ch.sth.dojo.es.events.SpielerHatPunktGewonnen.spielerHatPunktGewonnen;
import static ch.sth.dojo.es.game.AbgeschlossenesGame.abgeschlossenesGame;
import static ch.sth.dojo.es.game.Punkt.punkt;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.events.GegnerHatGameGewonnen;
import ch.sth.dojo.es.events.GegnerHatPunktGewonnen;
import ch.sth.dojo.es.events.SpielerHatGameGewonnen;
import ch.sth.dojo.es.events.SpielerHatPunktGewonnen;
import io.vavr.collection.List;
import io.vavr.control.Either;
import java.util.function.Function;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode @ToString
public class LaufendesGame implements Game {
    private final List<Punkt> punkteSpieler;
    private final List<Punkt> punkteGegner;

    private static LaufendesGame laufendesGame(final List<Punkt> punkteSpieler, final List<Punkt> punkteGegner) {
        return new LaufendesGame(punkteSpieler, punkteGegner);
    }

    public static LaufendesGame initial() {
        return laufendesGame(List.empty(), List.empty());
    }

    // commands
    static Function<LaufendesGame, DomainEvent> spielerPunktet() {
        return LaufendesGame::doSpielerPunktet;
    }

    static Function<LaufendesGame, DomainEvent> gegnerPunktet() {
        return LaufendesGame::doGegnerPunktet;
    }

    static Function<LaufendesGame, Either<DomainError, DomainEvent>> handleSpielerPunktet() {
        return LaufendesGame::handleSpielerPunktet;
    }
    private static Either<DomainError, DomainEvent> handleSpielerPunktet(final LaufendesGame prev) {
        return Either.right(prev.doSpielerPunktet());
    }

    private DomainEvent doSpielerPunktet() {
        final List<Punkt> incremented = punkteSpieler.append(punkt());
        return incremented.size() == 4
                ? SpielerHatGameGewonnen(incremented, punkteGegner)
                : SpielerHatPunktGewonnen(incremented, punkteGegner);
    }

    private DomainEvent doGegnerPunktet() {
        final List<Punkt> incremented = punkteGegner.append(punkt());
        return incremented.size() == 4
                ? GegnerHatGameGewonnen(punkteSpieler, incremented)
                : GegnerHatPunktGewonnen(punkteSpieler, incremented);
    }

    private static SpielerHatGameGewonnen SpielerHatGameGewonnen(final List<Punkt> punkteSpieler,
                                                                 final List<Punkt> punkteGegner) {
        return spielerHatGameGewonnen(punkteSpieler.size(), punkteGegner.size());
    }

    private static SpielerHatPunktGewonnen SpielerHatPunktGewonnen(final List<Punkt> punkteSpieler, final List<Punkt> punkteGegner) {
        return spielerHatPunktGewonnen();
    }

    private static GegnerHatPunktGewonnen GegnerHatPunktGewonnen(final List<Punkt> punkteSpieler, final List<Punkt> punkteGegner) {
        return gegnerHatPunktGewonnen();
    }

    private static GegnerHatGameGewonnen GegnerHatGameGewonnen(final List<Punkt> punkteSpieler,
                                                               final List<Punkt> punkteGegner) {
        return gegnerHatGameGewonnen();
    }

    // events

    static Function<SpielerHatGameGewonnen, AbgeschlossenesGame> shgg(LaufendesGame prev) {
        return event -> abgeschlossenesGame().apply(prev.punkteSpieler.append(punkt()), prev.punkteGegner);
    }

    static Function<GegnerHatGameGewonnen, AbgeschlossenesGame> ghgg(LaufendesGame prev) {
        return event -> abgeschlossenesGame().apply(prev.punkteSpieler, prev.punkteGegner.append(punkt()));
    }

    static Function<GegnerHatPunktGewonnen, LaufendesGame> ghpg(LaufendesGame prev) {
        return event -> laufendesGame(prev.punkteSpieler, prev.punkteGegner.append(punkt()));
    }

    static Function<SpielerHatPunktGewonnen, LaufendesGame> shpg(LaufendesGame prev) {
        return event -> laufendesGame(prev.punkteSpieler.append(punkt()), prev.punkteGegner);
    }
}
