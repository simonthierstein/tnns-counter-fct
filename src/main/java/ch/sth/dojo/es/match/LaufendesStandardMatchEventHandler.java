/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.match;


import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.Util;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.events.GegnerHatSatzGewonnen;
import ch.sth.dojo.es.events.SpielerHatSatzGewonnen;
import io.vavr.Function2;
import io.vavr.control.Either;

public final class LaufendesStandardMatchEventHandler implements StandardMatch {
    Either<DomainError, StandardMatch> handleEvent(final DomainEvent event) {
        return DomainEvent.handleEventF2(event,
                this,
                Util.leftF2(MatchEventHandler.eventToErrorF2()),
                Util.leftF2(MatchEventHandler.eventToErrorF2()),
                Util.leftF2(MatchEventHandler.eventToErrorF2()),
                Util.leftF2(MatchEventHandler.eventToErrorF2()),
                Util.leftF2(MatchEventHandler.eventToErrorF2()),
                Util.rightF2(spielerHatSatzGewonnen()),
                Util.rightF2(gegnerHatSatzGewonnen())
        );
    }

    private static Function2<LaufendesStandardMatchEventHandler, SpielerHatSatzGewonnen, StandardMatch> spielerHatSatzGewonnen() {
        return null;
    }

    private static Function2<LaufendesStandardMatchEventHandler, GegnerHatSatzGewonnen, StandardMatch> gegnerHatSatzGewonnen() {
        return null;
    }
}
