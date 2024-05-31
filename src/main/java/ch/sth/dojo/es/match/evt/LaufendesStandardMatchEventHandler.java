/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.match.evt;

import static ch.sth.dojo.es.Util.leftF2;
import static ch.sth.dojo.es.match.AbgeschlossenesStandardMatch.AbgeschlossenesStandardMatch;
import static ch.sth.dojo.es.match.LaufendesStandardMatch.incrementGegner;
import static ch.sth.dojo.es.match.LaufendesStandardMatch.incrementSpieler;
import static ch.sth.dojo.es.match.evt.MatchEventHandler.eventToErrorF2;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.events.GegnerHatMatchGewonnen;
import ch.sth.dojo.es.events.GegnerHatSatzGewonnen;
import ch.sth.dojo.es.events.SpielerHatMatchGewonnen;
import ch.sth.dojo.es.events.SpielerHatSatzGewonnen;
import ch.sth.dojo.es.match.LaufendesStandardMatch;
import ch.sth.dojo.es.match.StandardMatch;
import io.vavr.Function2;
import io.vavr.control.Either;

public class LaufendesStandardMatchEventHandler {

    public static Either<DomainError, StandardMatch> handleEvent(LaufendesStandardMatch state, final DomainEvent event) {
        return DomainEvent.handleEventF2(event,
                state,
                leftF2(eventToErrorF2()),
                leftF2(eventToErrorF2()),
                leftF2(eventToErrorF2()),
                leftF2(eventToErrorF2()),
                leftF2(eventToErrorF2()),
                spielerHatSatzGewonnen(),
                gegnerHatSatzGewonnen(),
                spielerHatMatchGewonnen(),
                gegnerHatMatchGewonnen()
        );
    }

    private static Function2<LaufendesStandardMatch, SpielerHatMatchGewonnen, Either<DomainError, StandardMatch>> spielerHatMatchGewonnen() {
        return (state, event) -> Either.narrow(AbgeschlossenesStandardMatch(state.punkteSpieler().increment().current(), state.punkteGegner().current()));
    }

    private static Function2<LaufendesStandardMatch, GegnerHatMatchGewonnen, Either<DomainError, StandardMatch>> gegnerHatMatchGewonnen() {
        return (state, event) -> Either.narrow(AbgeschlossenesStandardMatch(state.punkteSpieler().current(), state.punkteGegner().increment().current()));
    }

    private static Function2<LaufendesStandardMatch, SpielerHatSatzGewonnen, Either<DomainError, StandardMatch>> spielerHatSatzGewonnen() {
        return (state, event) -> Either.narrow(incrementSpieler(state));
    }

    private static Function2<LaufendesStandardMatch, GegnerHatSatzGewonnen, Either<DomainError, StandardMatch>> gegnerHatSatzGewonnen() {
        return (state, event) -> Either.narrow(incrementGegner(state));
    }

}
