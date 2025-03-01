/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cmatch.evt;

import static ch.sth.dojo.beh.DomainProblem.eventNotValid;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cmatch.domain.CMatch;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.GegnerDomainEvent;
import ch.sth.dojo.beh.evt.GegnerGameGewonnen;
import ch.sth.dojo.beh.evt.GegnerMatchGewonnen;
import ch.sth.dojo.beh.evt.GegnerPunktGewonnen;
import ch.sth.dojo.beh.evt.GegnerSatzGewonnen;
import ch.sth.dojo.beh.evt.SpielerDomainEvent;
import ch.sth.dojo.beh.evt.SpielerGameGewonnen;
import ch.sth.dojo.beh.evt.SpielerMatchGewonnen;
import ch.sth.dojo.beh.evt.SpielerPunktGewonnen;
import ch.sth.dojo.beh.evt.SpielerSatzGewonnen;
import io.vavr.control.Either;

public final class CMatchEventHandler {

    public static Either<DomainProblem, CMatch> handleEvent(CMatch state, DomainEvent event) {
        return switch (event) {
            case SpielerDomainEvent evt -> handleSpielerEvent(state, evt);
            case GegnerDomainEvent evt -> handleGegnerEvent(state, evt);
            default -> left(eventNotValid);
        };
    }

    private static Either<DomainProblem, CMatch> handleSpielerEvent(final CMatch state, final SpielerDomainEvent evt) {
        final Either<DomainProblem, CMatch> cMatches = switch (evt) {
            case SpielerGameGewonnen spielerGameGewonnen -> null;
            case SpielerPunktGewonnen spielerPunktGewonnen -> null;
            case SpielerSatzGewonnen spielerSatzGewonnen -> null;
            case SpielerMatchGewonnen spielerSatzGewonnen -> null;
        };
        return right(state);
    }

    private static Either<DomainProblem, CMatch> handleGegnerEvent(final CMatch state, final GegnerDomainEvent evt) {
        final Either<DomainProblem, CMatch> cMatches = switch (evt) {
            case GegnerGameGewonnen gegnerGameGewonnen -> null;
            case GegnerPunktGewonnen gegnerPunktGewonnen -> null;
            case GegnerSatzGewonnen gegnerSatzGewonnen -> null;
            case GegnerMatchGewonnen gegnerSatzGewonnen -> null;
        };
        return right(state);
    }

}
