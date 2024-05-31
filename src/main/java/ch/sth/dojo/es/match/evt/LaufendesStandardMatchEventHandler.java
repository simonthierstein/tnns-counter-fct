/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.match.evt;

import static ch.sth.dojo.es.Util.leftF2;
import static ch.sth.dojo.es.match.AbgeschlossenesStandardMatch.AbgeschlossenesStandardMatch;
import static ch.sth.dojo.es.match.LaufendesStandardMatch.LaufendesStandardMatch;
import static ch.sth.dojo.es.match.evt.MatchEventHandler.eventToErrorF2;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.Routing;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.events.GegnerHatSatzGewonnen;
import ch.sth.dojo.es.events.SpielerHatSatzGewonnen;
import ch.sth.dojo.es.match.LaufendesStandardMatch;
import ch.sth.dojo.es.match.PunkteGegner;
import ch.sth.dojo.es.match.PunkteSpieler;
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
                gegnerHatSatzGewonnen()
        );
    }

    private static Function2<LaufendesStandardMatch, SpielerHatSatzGewonnen, Either<DomainError, StandardMatch>> spielerHatSatzGewonnen() {
        return (state, event) -> Either.narrow(incrementSpieler(state));
    }

    private static Function2<LaufendesStandardMatch, GegnerHatSatzGewonnen, Either<DomainError, StandardMatch>> gegnerHatSatzGewonnen() {
        return (state, event) -> Either.narrow(incrementGegner(state));
    }

    private static Either<DomainError, ? extends StandardMatch> incrementSpieler(LaufendesStandardMatch prev) {
        return Routing.selection(prev.punkteSpieler().increment(),
                PunkteSpieler.passIfNotWon(),
                nextPt -> LaufendesStandardMatch(nextPt, prev.punkteGegner()),
                nextPt -> AbgeschlossenesStandardMatch(nextPt.current(), prev.punkteGegner().current()));
    }

    private static Either<DomainError, ? extends StandardMatch> incrementGegner(LaufendesStandardMatch prev) {
        return Routing.selection(prev.punkteGegner().increment(),
                PunkteGegner.passIfNotWon(),
                nextPt -> LaufendesStandardMatch(prev.punkteSpieler(), nextPt),
                nextPt -> AbgeschlossenesStandardMatch(prev.punkteSpieler().current(), nextPt.current()));
    }
}
