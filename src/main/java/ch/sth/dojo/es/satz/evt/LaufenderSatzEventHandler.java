/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.satz.evt;

import static ch.sth.dojo.es.Util.leftF2;
import static ch.sth.dojo.es.Util.rightF2;
import static ch.sth.dojo.es.satz.AbgeschlossenerSatz.AbgeschlossenerSatz;
import static ch.sth.dojo.es.satz.evt.SatzEventHandler.eventToErrorF2;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.Identity;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.events.GegnerHatGameGewonnen;
import ch.sth.dojo.es.events.GegnerHatMatchGewonnen;
import ch.sth.dojo.es.events.GegnerHatPunktGewonnen;
import ch.sth.dojo.es.events.GegnerHatSatzGewonnen;
import ch.sth.dojo.es.events.SpielerHatGameGewonnen;
import ch.sth.dojo.es.events.SpielerHatMatchGewonnen;
import ch.sth.dojo.es.events.SpielerHatPunktGewonnen;
import ch.sth.dojo.es.events.SpielerHatSatzGewonnen;
import ch.sth.dojo.es.satz.AbgeschlossenerSatz;
import ch.sth.dojo.es.satz.LaufenderSatz;
import ch.sth.dojo.es.satz.Satz;
import io.vavr.Function2;
import io.vavr.control.Either;
import java.util.function.Function;

class LaufenderSatzEventHandler {
    //event
    static Either<DomainError, Satz> handleLaufenderSatz(final LaufenderSatz state, final DomainEvent event) {
        return DomainEvent.handleEventF2(
                event, state,
                rightF2(spielerHatPunktGewonnen()),
                rightF2(gegnerHatPunktGewonnen()),
                rightF2(spielerHatGameGewonnen()),
                rightF2(gegnerHatGameGewonnen()),
                leftF2(eventToErrorF2()),
                rightF2(spielerHatSatzGewonnen()),
                rightF2(gegnerHatSatzGewonnen()),
                rightF2(spielerHatMatchGewonnen()),
                rightF2(gegnerHatMatchGewonnen())
        );
    }

    private static Function2<LaufenderSatz, GegnerHatPunktGewonnen, Satz> gegnerHatPunktGewonnen() {
        return (state, event) -> state;
    }

    private static Function2<LaufenderSatz, SpielerHatPunktGewonnen, Satz> spielerHatPunktGewonnen() {
        return (state, event) -> state;
    }

    private static Function2<LaufenderSatz, SpielerHatMatchGewonnen, Satz> spielerHatMatchGewonnen() {
        return (state, event) -> toAbgeschlossenerSatzSpieler().apply(state);
    }

    private static Function2<LaufenderSatz, GegnerHatMatchGewonnen, Satz> gegnerHatMatchGewonnen() {
        return (state1, event1) -> toAbgeschlossenerSatzGegner().apply(state1);
    }
    private static Function2<LaufenderSatz, SpielerHatGameGewonnen, Satz> spielerHatGameGewonnen() {
        return (state, event) -> toLaufenderSatzSpieler().apply(state);
    }

    private static Function2<LaufenderSatz, GegnerHatGameGewonnen, Satz> gegnerHatGameGewonnen() {
        return (state, event) -> toLaufenderSatzGegner().apply(state);
    }

    private static Function2<LaufenderSatz, SpielerHatSatzGewonnen, Satz> spielerHatSatzGewonnen() {
        return (state, event) -> toAbgeschlossenerSatzSpieler().apply(state);
    }

    private static Function2<LaufenderSatz, GegnerHatSatzGewonnen, Satz> gegnerHatSatzGewonnen() {
        return (state, event) -> toAbgeschlossenerSatzGegner().apply(state);
    }

    private static Function<LaufenderSatz, AbgeschlossenerSatz> toAbgeschlossenerSatzSpieler() {
        return state -> Identity.unit(state)
                .map(LaufenderSatz::incrementSpieler)
                .map(toAbgeschlossenerSatz())
                .eval();
    }

    private static Function<LaufenderSatz, AbgeschlossenerSatz> toAbgeschlossenerSatzGegner() {
        return state -> Identity.unit(state)
                .map(LaufenderSatz::incrementGegner)
                .map(toAbgeschlossenerSatz())
                .eval();
    }

    private static Function<LaufenderSatz, LaufenderSatz> toLaufenderSatzSpieler() {
        return LaufenderSatz::incrementSpieler;
    }

    private static Function<LaufenderSatz, LaufenderSatz> toLaufenderSatzGegner() {
        return LaufenderSatz::incrementGegner;
    }

    private static Function<LaufenderSatz, AbgeschlossenerSatz> toAbgeschlossenerSatz() {
        return laufenderSatz -> AbgeschlossenerSatz(laufenderSatz.punkteSpieler().size(), laufenderSatz.punkteGegner().size());
    }
}
