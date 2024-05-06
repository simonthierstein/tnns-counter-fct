/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es;

import static ch.sth.dojo.es.AbgeschlossenesGame.abgeschlossenesGame;
import static ch.sth.dojo.es.Punkt.punkt;
import static ch.sth.dojo.es.events.GegnerHatGameGewonnen.gegnerHatGameGewonnen;
import static ch.sth.dojo.es.events.GegnerHatPunktGewonnen.gegnerHatPunktGewonnen;
import static ch.sth.dojo.es.events.SpielerHatGameGewonnen.spielerHatGameGewonnen;
import static ch.sth.dojo.es.events.SpielerHatPunktGewonnen.spielerHatPunktGewonnen;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

import ch.sth.dojo.es.commands.DomainCommand;
import ch.sth.dojo.es.commands.GegnerPunktet;
import ch.sth.dojo.es.commands.SpielerPunktet;
import ch.sth.dojo.es.events.DomainEvent;
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

    DomainEvent handleCommand(DomainCommand command) {
        return Match(command).of(
                Case($(instanceOf(SpielerPunktet.class)), this::spielerPunktet),
                Case($(instanceOf(GegnerPunktet.class)), this::gegnerPunktet)
        );

    }

    private DomainEvent spielerPunktet() {
        final List<Punkt> incremented = punkteSpieler.append(punkt());
        return incremented.size() == 4
                ? SpielerHatGameGewonnen(incremented, punkteGegner)
                : SpielerHatPunktGewonnen(incremented, punkteGegner);
    }

    private DomainEvent gegnerPunktet() {
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

    public Game handleEvent(final DomainEvent elem) {
        return Match(elem).of(
                Case($(instanceOf(SpielerHatPunktGewonnen.class)), shpg()),
                Case($(instanceOf(GegnerHatPunktGewonnen.class)), ghpg()),
                Case($(instanceOf(SpielerHatGameGewonnen.class)), shgg()),
                Case($(instanceOf(GegnerHatGameGewonnen.class)), ghgg())
        );
    }

    private Function<SpielerHatGameGewonnen, Game> shgg() {
        return event -> abgeschlossenesGame(punkteSpieler.append(punkt()), punkteGegner);
    }

    private Function<GegnerHatGameGewonnen, Game> ghgg() {
        return event -> abgeschlossenesGame(punkteSpieler, punkteGegner.append(punkt()));
    }

    private Function<GegnerHatPunktGewonnen, Game> ghpg() {
        return event -> laufendesGame(punkteSpieler, punkteGegner.append(punkt()));
    }

    private Function<SpielerHatPunktGewonnen, Game> shpg() {
        return event -> laufendesGame(punkteSpieler.append(punkt()), punkteGegner);
    }
}
