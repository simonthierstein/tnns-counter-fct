/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cmatch.evt;

import static io.vavr.control.Either.right;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cmatch.domain.Match;
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

    public static Either<DomainProblem, Match> handleEvent(Match state, DomainEvent event) {
        return switch (event) {
            case SpielerDomainEvent evt -> handleSpielerEvent(state, evt);
            case GegnerDomainEvent evt -> handleGegnerEvent(state, evt);
        };
    }

    private static Either<DomainProblem, Match> handleSpielerEvent(final Match state, final SpielerDomainEvent evt) {
        return switch (evt) {
            case SpielerPunktGewonnen event -> right(state);
            case SpielerGameGewonnen event -> right(state);
            case SpielerSatzGewonnen event -> spielerSatzGewonnen(state, event);
            case SpielerMatchGewonnen event -> spielerMatchGewonnen(state, event);
        };
    }

    private static Either<DomainProblem, Match> spielerMatchGewonnen(final Match state, final SpielerMatchGewonnen event) {
        return Match.spielerMatchGewonnen(state);
    }

    private static Either<DomainProblem, Match> spielerSatzGewonnen(final Match state, final SpielerSatzGewonnen event) {
        return Match.spielerSatzGewonnen(state);
    }

    private static Either<DomainProblem, Match> handleGegnerEvent(final Match state, final GegnerDomainEvent evt) {
        return switch (evt) {
            case GegnerPunktGewonnen event -> right(state);
            case GegnerGameGewonnen event -> right(state);
            case GegnerSatzGewonnen event -> gegnerSatzGewonnen(state, event);
            case GegnerMatchGewonnen event -> gegnerMatchGewonnen(state, event);
        };
    }

    private static Either<DomainProblem, Match> gegnerSatzGewonnen(final Match state, final GegnerSatzGewonnen event) {
        return Match.gegnerSatzGewonnen(state);
    }

    private static Either<DomainProblem, Match> gegnerMatchGewonnen(final Match state, final GegnerMatchGewonnen event) {
        return Match.gegnerMatchGewonnen(state);
    }

}
