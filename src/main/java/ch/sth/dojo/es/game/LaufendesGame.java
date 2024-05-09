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

import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.events.GameErzeugt;
import ch.sth.dojo.es.events.GegnerHatGameGewonnen;
import ch.sth.dojo.es.events.GegnerHatPunktGewonnen;
import ch.sth.dojo.es.events.SpielerHatGameGewonnen;
import ch.sth.dojo.es.events.SpielerHatPunktGewonnen;
import io.vavr.collection.List;
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

    static LaufendesGame initial() {
        return laufendesGame(List.empty(), List.empty());
    }

    static Function<GameErzeugt, LaufendesGame> handleGameErzeugt() {
        return gameErzeugt -> initial();
    }

    // commands


     DomainEvent spielerPunktet() {
        final List<Punkt> incremented = punkteSpieler.append(punkt());
        return incremented.size() == 4
                ? SpielerHatGameGewonnen(incremented, punkteGegner)
                : SpielerHatPunktGewonnen(incremented, punkteGegner);
    }

     DomainEvent gegnerPunktet() {
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


    public Game handleEvent(final DomainEvent elem) {
        return DomainEvent.handleEvent(elem,
                shpg(),
                ghpg(),
                shgg(),
                ghgg());
    }

    private Function<SpielerHatGameGewonnen, Game> shgg() {
        return event -> abgeschlossenesGame().apply(punkteSpieler.append(punkt()), punkteGegner);
    }

    private Function<GegnerHatGameGewonnen, Game> ghgg() {
        return event -> abgeschlossenesGame().apply(punkteSpieler, punkteGegner.append(punkt()));
    }

    private Function<GegnerHatPunktGewonnen, Game> ghpg() {
        return event -> laufendesGame(punkteSpieler, punkteGegner.append(punkt()));
    }

    private Function<SpielerHatPunktGewonnen, Game> shpg() {
        return event -> laufendesGame(punkteSpieler.append(punkt()), punkteGegner);
    }
}
