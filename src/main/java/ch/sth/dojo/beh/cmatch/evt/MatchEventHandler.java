/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cmatch.evt;

import static io.vavr.control.Either.right;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cmatch.domain.TennisMatch;
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

public final class MatchEventHandler {

    public static Either<DomainProblem, TennisMatch> handleEvent(TennisMatch state, DomainEvent event) {
        return switch (event) {
            case SpielerDomainEvent evt -> handleSpielerEvent(state, evt);
            case GegnerDomainEvent evt -> handleGegnerEvent(state, evt);
        };
    }

    private static Either<DomainProblem, TennisMatch> handleSpielerEvent(final TennisMatch state, final SpielerDomainEvent evt) {
        return switch (evt) {
            case SpielerPunktGewonnen event -> right(state);
            case SpielerGameGewonnen event -> right(state);
            case SpielerSatzGewonnen event -> spielerSatzGewonnen(state, event);
            case SpielerMatchGewonnen event -> spielerMatchGewonnen(state, event);
        };
    }

    private static Either<DomainProblem, TennisMatch> spielerMatchGewonnen(final TennisMatch state, final SpielerMatchGewonnen event) {
        return TennisMatch.spielerMatchGewonnen(state);
    }

    private static Either<DomainProblem, TennisMatch> spielerSatzGewonnen(final TennisMatch state, final SpielerSatzGewonnen event) {
        return TennisMatch.spielerSatzGewonnen(state);
    }

    private static Either<DomainProblem, TennisMatch> handleGegnerEvent(final TennisMatch state, final GegnerDomainEvent evt) {
        return switch (evt) {
            case GegnerPunktGewonnen event -> right(state);
            case GegnerGameGewonnen event -> right(state);
            case GegnerSatzGewonnen event -> gegnerSatzGewonnen(state, event);
            case GegnerMatchGewonnen event -> gegnerMatchGewonnen(state, event);
        };
    }

    private static Either<DomainProblem, TennisMatch> gegnerSatzGewonnen(final TennisMatch state, final GegnerSatzGewonnen event) {
        return TennisMatch.gegnerSatzGewonnen(state);
    }

    private static Either<DomainProblem, TennisMatch> gegnerMatchGewonnen(final TennisMatch state, final GegnerMatchGewonnen event) {
        return TennisMatch.gegnerMatchGewonnen(state);
    }

}
