/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.satz;

import static ch.sth.dojo.es.satz.AbgeschlossenerSatz.AbgeschlossenerSatz;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.events.GegnerHatGameGewonnen;
import ch.sth.dojo.es.events.GegnerHatSatzGewonnen;
import ch.sth.dojo.es.events.SpielerHatGameGewonnen;
import ch.sth.dojo.es.events.SpielerHatSatzGewonnen;
import io.vavr.Function2;
import io.vavr.control.Either;
import java.util.function.Function;

class LaufenderSatzEventHandler {
    //event
    static Either<DomainError, Satz> handleLaufenderSatz(final LaufenderSatz state, final DomainEvent event) {
        return DomainEvent.handleEventF2(
                event, state,
                Satz.leftF2(Satz.eventToErrorF2()),
                Satz.leftF2(Satz.eventToErrorF2()),
                Satz.rightF2(spielerHatGameGewonnen()),
                Satz.rightF2(gegnerHatGameGewonnen()),
                Satz.leftF2(Satz.eventToErrorF2()),
                Satz.rightF2(spielerHatSatzGewonnen()),
                Satz.rightF2(gegnerHatSatzGewonnen())
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
        return laufenderSatz -> laufenderSatz.export(((pSpieler, pGegner) -> AbgeschlossenerSatz(pSpieler.size(), pGegner.size())));
    }
}
