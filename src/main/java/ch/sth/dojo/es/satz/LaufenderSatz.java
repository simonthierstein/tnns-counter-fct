/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.satz;

import static ch.sth.dojo.es.game.Punkt.punkt;
import static ch.sth.dojo.es.satz.AbgeschlossenerSatz.AbgeschlossenerSatz;

import ch.sth.dojo.es.Routing;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.events.GegnerHatGameGewonnen;
import ch.sth.dojo.es.events.GegnerHatSatzGewonnen;
import ch.sth.dojo.es.events.SpielerHatGameGewonnen;
import ch.sth.dojo.es.events.SpielerHatSatzGewonnen;
import ch.sth.dojo.es.game.Punkt;
import io.vavr.Function2;
import io.vavr.Predicates;
import io.vavr.collection.List;
import io.vavr.control.Option;
import java.util.function.Function;

public record LaufenderSatz(List<Punkt> punkteSpieler, List<Punkt> punkteGegner) implements Satz {
    static LaufenderSatz zero() {
        return new LaufenderSatz(List.empty(), List.empty());
    }

    //command

    static DomainEvent spielerGewinneGameCmd(LaufenderSatz state, SpielerHatGameGewonnen event) {
        return Routing.selection(state.incrementSpieler(),
                LaufenderSatz::passIfSpielerWon,
                toAbgeschlossenEventSpieler(),
                toLaufendEvent(event));
    }

    static DomainEvent gegnerGewinneGameCmd(final LaufenderSatz state, final GegnerHatGameGewonnen event) {
        return Routing.selection(state.incrementGegner(),
                LaufenderSatz::passIfGegnerWon,
                toAbgeschlossenEventGegner(),
                toLaufendEvent(event));
    }

    private static Function<LaufenderSatz, DomainEvent> toLaufendEvent(final DomainEvent event) {
        return prev -> event;
    }

    private static Function<LaufenderSatz, DomainEvent> toAbgeschlossenEventSpieler() {
        return prev -> SpielerHatSatzGewonnen.spielerHatSatzGewonnen();
    }

    private static Function<LaufenderSatz, DomainEvent> toAbgeschlossenEventGegner() {
        return prev -> GegnerHatSatzGewonnen.gegnerHatSatzGewonnen();
    }


    //event

    static Function2<LaufenderSatz, SpielerHatGameGewonnen, Satz> spielerHatGameGewonnen() {
        return LaufenderSatz::handleEvent;
    }

    static Function2<LaufenderSatz, GegnerHatGameGewonnen, Satz> gegnerHatGameGewonnen() {
        return LaufenderSatz::handleEvent;
    }

    static Function2<LaufenderSatz, SpielerHatSatzGewonnen, Satz> spielerHatSatzGewonnen() {
        return LaufenderSatz::handleEvent;
    }

    static Function2<LaufenderSatz, GegnerHatSatzGewonnen, Satz> gegnerHatSatzGewonnen() {
        return LaufenderSatz::handleEvent;
    }

    static Satz handleEvent(LaufenderSatz state, SpielerHatGameGewonnen event) {
        return toLaufenderSatzSpieler().apply(state);
    }

    static Satz handleEvent(LaufenderSatz state, GegnerHatGameGewonnen event) {
        return toLaufenderSatzGegner().apply(state);
    }

    static Satz handleEvent(LaufenderSatz state, GegnerHatSatzGewonnen event) {
        return toAbgeschlossenerSatz().apply(state);
    }

    static Satz handleEvent(LaufenderSatz state, SpielerHatSatzGewonnen event) {
        return toAbgeschlossenerSatz().apply(state);
    }


    private static Function<LaufenderSatz, LaufenderSatz> toLaufenderSatzSpieler() {
        return laufenderSatz -> new LaufenderSatz(laufenderSatz.punkteSpieler.append(punkt()), laufenderSatz.punkteGegner);
    }

    private static Function<LaufenderSatz, LaufenderSatz> toLaufenderSatzGegner() {
        return laufenderSatz -> new LaufenderSatz(laufenderSatz.punkteSpieler, laufenderSatz.punkteGegner.append(punkt()));
    }

    private static Function<LaufenderSatz, AbgeschlossenerSatz> toAbgeschlossenerSatz() {
        return laufenderSatz -> AbgeschlossenerSatz(laufenderSatz.punkteSpieler.size(), laufenderSatz.punkteGegner.size());
    }


    private static boolean passIfSpielerWon(final LaufenderSatz laufenderSatz) {
        return Option.of(laufenderSatz)
                .filter(Predicates.anyOf(x -> x.punkteSpieler.size() == 6 && x.punkteGegner.size() <= 4,
                        x -> x.punkteSpieler.size() == 7))
                .isDefined();
    }

    private static boolean passIfGegnerWon(final LaufenderSatz laufenderSatz) {
        return Option.of(laufenderSatz)
                .filter(Predicates.anyOf(x -> x.punkteGegner.size() == 6 && x.punkteSpieler.size() <= 4,
                        x -> x.punkteGegner.size() == 7))
                .isDefined();
    }

    private LaufenderSatz incrementSpieler() {
        return new LaufenderSatz(punkteSpieler.append(punkt()), punkteGegner);
    }

    private LaufenderSatz incrementGegner() {
        return new LaufenderSatz(punkteSpieler, punkteGegner.append(punkt()));
    }
}
