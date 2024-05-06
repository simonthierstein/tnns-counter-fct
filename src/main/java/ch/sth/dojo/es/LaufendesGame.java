/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es;

import static ch.sth.dojo.es.AbgeschlossenesGame.GegnerHatGameGewonnen;
import static ch.sth.dojo.es.AbgeschlossenesGame.SpielerHatGameGewonnen;
import static ch.sth.dojo.es.AbgeschlossenesGame.abgeschlossenesGame;
import static ch.sth.dojo.es.Punkt.punkt;
import static ch.sth.dojo.es.events.GegnerHatPunktGewonnen.gegnerHatPunktGewonnen;
import static ch.sth.dojo.es.events.SpielerHatPunktGewonnen.spielerHatPunktGewonnen;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.events.GegnerHatGameGewonnen;
import ch.sth.dojo.es.events.GegnerHatPunktGewonnen;
import ch.sth.dojo.es.events.SpielerHatGameGewonnen;
import ch.sth.dojo.es.events.SpielerHatPunktGewonnen;
import io.vavr.Tuple;
import io.vavr.Tuple2;
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

    static LaufendesGame initial() {
        return laufendesGame(List.empty(), List.empty());
    }

    static LaufendesGame laufendesGame(final List<Punkt> punkteSpieler, final List<Punkt> punkteGegner) {
        return new LaufendesGame(punkteSpieler, punkteGegner);
    }

    static SpielerHatPunktGewonnen SpielerHatPunktGewonnen(final List<Punkt> punkteSpieler, final List<Punkt> punkteGegner) {
        return spielerHatPunktGewonnen();
    }

    static GegnerHatPunktGewonnen GegnerHatPunktGewonnen(final List<Punkt> punkteSpieler, final List<Punkt> punkteGegner) {
        return gegnerHatPunktGewonnen();
    }

    public static Function<LaufendesGame, DomainEvent> gegnerPunktetFct() {
        return LaufendesGame::gegnerPunktet;
    }

    public static Function<LaufendesGame, DomainEvent> spielerPunktetFct() {
        return LaufendesGame::spielerPunktet;
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

    static Function<LaufendesGame, Tuple2<Integer, Integer>> export2Integer() {
        return game -> {
            final Integer spielerPts = game.punkteSpieler.foldLeft(0, (st, acc) -> st + 1);
            final Integer gegnerPts = game.punkteGegner.foldLeft(0, (st, acc) -> st + 1);
            return Tuple.of(spielerPts, gegnerPts);
        };
    }

    <T> T eval(final Function<LaufendesGame, T> mapper) {
        return mapper.apply(this);
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
