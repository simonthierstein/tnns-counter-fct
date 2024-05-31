/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.match.evt;

import static ch.sth.dojo.es.match.AbgeschlossenesStandardMatch.AbgeschlossenesStandardMatch;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.Routing;
import ch.sth.dojo.es.Util;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.events.GegnerHatSatzGewonnen;
import ch.sth.dojo.es.events.SpielerHatSatzGewonnen;
import ch.sth.dojo.es.match.LaufendesStandardMatch;
import ch.sth.dojo.es.match.MatchEventHandler;
import ch.sth.dojo.es.match.PunkteGegner;
import ch.sth.dojo.es.match.PunkteSpieler;
import ch.sth.dojo.es.match.StandardMatch;
import io.vavr.Function2;
import io.vavr.control.Either;

public class LaufendesStandardMatchEventHandler {

    public static Either<DomainError, StandardMatch> handleEvent(LaufendesStandardMatch state, final DomainEvent event) {
        return DomainEvent.handleEventF2(event,
                state,
                Util.leftF2(MatchEventHandler.eventToErrorF2()),
                Util.leftF2(MatchEventHandler.eventToErrorF2()),
                Util.leftF2(MatchEventHandler.eventToErrorF2()),
                Util.leftF2(MatchEventHandler.eventToErrorF2()),
                Util.leftF2(MatchEventHandler.eventToErrorF2()),
                Util.rightF2(spielerHatSatzGewonnen()),
                Util.rightF2(gegnerHatSatzGewonnen())
        );
    }

    private static Function2<LaufendesStandardMatch, SpielerHatSatzGewonnen, StandardMatch> spielerHatSatzGewonnen() {
        return (state, event) -> incrementSpieler(state);
    }

    private static Function2<LaufendesStandardMatch, GegnerHatSatzGewonnen, StandardMatch> gegnerHatSatzGewonnen() {
        return (state, event) -> incrementGegner(state);
    }

    private static StandardMatch incrementSpieler(LaufendesStandardMatch prev) {
        return Routing.selection(prev.punkteSpieler().increment(),
                PunkteSpieler.passIfNotWon(),
                nextPt -> new LaufendesStandardMatch(nextPt, prev.punkteGegner()),
                nextPt -> AbgeschlossenesStandardMatch(nextPt.current(), prev.punkteGegner().current()));
    }

    private static StandardMatch incrementGegner(LaufendesStandardMatch prev) {
        return Routing.selection(prev.punkteGegner().increment(),
                PunkteGegner.passIfNotWon(),
                nextPt -> new LaufendesStandardMatch(prev.punkteSpieler(), nextPt),
                nextPt -> AbgeschlossenesStandardMatch(prev.punkteSpieler().current(), nextPt.current()));
    }
}
