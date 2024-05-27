/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.evt;

import static ch.sth.dojo.es.satz.AbgeschlossenerSatz.AbgeschlossenerSatz;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.Util;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.events.GegnerHatGameGewonnen;
import ch.sth.dojo.es.events.GegnerHatSatzGewonnen;
import ch.sth.dojo.es.events.SpielerHatGameGewonnen;
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
                Util.leftF2(SatzEventHandler.eventToErrorF2()),
                Util.leftF2(SatzEventHandler.eventToErrorF2()),
                Util.rightF2(spielerHatGameGewonnen()),
                Util.rightF2(gegnerHatGameGewonnen()),
                Util.leftF2(SatzEventHandler.eventToErrorF2()),
                Util.rightF2(spielerHatSatzGewonnen()),
                Util.rightF2(gegnerHatSatzGewonnen())
        );
    }

    static Function2<LaufenderSatz, SpielerHatGameGewonnen, Satz> spielerHatGameGewonnen() {
        return (state, event) -> toLaufenderSatzSpieler().apply(state);
    }

    static Function2<LaufenderSatz, GegnerHatGameGewonnen, Satz> gegnerHatGameGewonnen() {
        return (state, event) -> toLaufenderSatzGegner().apply(state);
    }

    static Function2<LaufenderSatz, SpielerHatSatzGewonnen, Satz> spielerHatSatzGewonnen() {
        return (state, event) -> toAbgeschlossenerSatz().apply(state);
    }

    static Function2<LaufenderSatz, GegnerHatSatzGewonnen, Satz> gegnerHatSatzGewonnen() {
        return (state, event) -> toAbgeschlossenerSatz().apply(state);
    }

    private static Function<LaufenderSatz, LaufenderSatz> toLaufenderSatzSpieler() {
        return laufenderSatz -> laufenderSatz.incrementSpieler();
    }

    private static Function<LaufenderSatz, LaufenderSatz> toLaufenderSatzGegner() {
        return laufenderSatz -> laufenderSatz.incrementGegner();
    }

    private static Function<LaufenderSatz, AbgeschlossenerSatz> toAbgeschlossenerSatz() {
        return laufenderSatz -> AbgeschlossenerSatz(laufenderSatz.punkteSpieler().size(), laufenderSatz.punkteGegner().size());
    }
}
