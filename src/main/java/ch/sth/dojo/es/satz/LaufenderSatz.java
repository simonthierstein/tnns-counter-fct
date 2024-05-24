/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.satz;

import static ch.sth.dojo.es.events.GegnerHatSatzGewonnen.gegnerHatSatzGewonnen;
import static ch.sth.dojo.es.events.SpielerHatSatzGewonnen.spielerHatSatzGewonnen;
import static ch.sth.dojo.es.game.Punkt.punkt;
import static ch.sth.dojo.es.satz.Satz.narrowSatz;

import ch.sth.dojo.es.Routing;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.events.GegnerHatGameGewonnen;
import ch.sth.dojo.es.events.SpielerHatGameGewonnen;
import ch.sth.dojo.es.game.Punkt;
import io.vavr.collection.List;
import io.vavr.control.Option;
import java.util.function.Function;

public record LaufenderSatz(List<Punkt> punkteSpieler, List<Punkt> punkteGegner) implements Satz {
    static LaufenderSatz zero() {
        return new LaufenderSatz(List.empty(), List.empty());
    }

    //command

    static DomainEvent spielerGewinneGameCmd(LaufenderSatz state, SpielerHatGameGewonnen event) {
        return Routing.selection(state,
                LaufenderSatz::passIfSpielerWon,
                toAbgeschlossenEventSpieler(),
                toLaufendEvent(event));
    }

    static DomainEvent gegnerGewinneGameCmd(final LaufenderSatz state, final GegnerHatGameGewonnen event) {
        return Routing.selection(state,
                LaufenderSatz::passIfGegnerWon,
                toAbgeschlossenEventGegner(),
                toLaufendEvent(event));
    }

    private static Function<LaufenderSatz, DomainEvent> toLaufendEvent(final DomainEvent event) {
        return prev -> event;
    }

    private static Function<LaufenderSatz, DomainEvent> toAbgeschlossenEventSpieler() {
        return prev -> spielerHatSatzGewonnen();
    }

    private static Function<LaufenderSatz, DomainEvent> toAbgeschlossenEventGegner() {
        return prev -> gegnerHatSatzGewonnen();
    }


    //event


    static Satz spielerGewinneGame(final LaufenderSatz prev) {// TODO sth/24.05.2024 :
        return Routing.selection(prev,
                LaufenderSatz::passIfSpielerWon,
                narrowSatz(toAbgeschlossenerSatz()),
                narrowSatz(toLaufenderSatzSpieler()));
    }

    static Satz gegnerGewinneGame(final LaufenderSatz prev) {// TODO sth/24.05.2024 :
        return Routing.selection(prev,
                LaufenderSatz::passIfGegnerWon,
                narrowSatz(toAbgeschlossenerSatz()),
                narrowSatz(toLaufenderSatzGegner()));
    }

    private static Function<LaufenderSatz, LaufenderSatz> toLaufenderSatzSpieler() {
        return laufenderSatz -> new LaufenderSatz(laufenderSatz.punkteSpieler.append(punkt()), laufenderSatz.punkteGegner);
    }

    private static Function<LaufenderSatz, LaufenderSatz> toLaufenderSatzGegner() {
        return laufenderSatz -> new LaufenderSatz(laufenderSatz.punkteSpieler, laufenderSatz.punkteGegner.append(punkt()));
    }

    private static Function<LaufenderSatz, AbgeschlossenerSatz> toAbgeschlossenerSatz() {
        return laufenderSatz -> new AbgeschlossenerSatz(laufenderSatz.punkteSpieler.size(), laufenderSatz.punkteGegner.size());
    }


    private static boolean passIfSpielerWon(final LaufenderSatz laufenderSatz) {
        return Option.of(laufenderSatz)
                .filter(x -> x.punkteSpieler.size() == 6 && x.punkteGegner.size() <= 4)
                .filter(x -> x.punkteSpieler.size() == 7)
                .isDefined();
    }

    private static boolean passIfGegnerWon(final LaufenderSatz laufenderSatz) {
        return Option.of(laufenderSatz)
                .filter(x -> x.punkteGegner.size() == 6 && x.punkteSpieler.size() <= 4)
                .filter(x -> x.punkteGegner.size() == 7)
                .isDefined();
    }
}
