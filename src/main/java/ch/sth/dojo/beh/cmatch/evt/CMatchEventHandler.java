/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cmatch.evt;

import static io.vavr.control.Either.right;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cmatch.domain.MatchScoreEvent;
import ch.sth.dojo.beh.cmatch.domain.state.MatchState;
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

    public static Either<DomainProblem, MatchState> handleEvent(MatchState state, DomainEvent event) {
        return switch (event) {
            case SpielerDomainEvent evt -> handleSpielerEvent(state, evt);
            case GegnerDomainEvent evt -> handleGegnerEvent(state, evt);
        };
    }

    private static Either<DomainProblem, MatchState> handleSpielerEvent(final MatchState state, final SpielerDomainEvent evt) {
        return switch (evt) {
            case SpielerPunktGewonnen event -> right(state);
            case SpielerGameGewonnen event -> right(state);
            case SpielerSatzGewonnen event -> spielerSatzGewonnen(state, event);
            case SpielerMatchGewonnen event -> spielerMatchGewonnen(state, event);
        };
    }

    private static Either<DomainProblem, MatchState> spielerMatchGewonnen(final MatchState state, final SpielerMatchGewonnen event) {
        return MatchScoreEvent.spielerMatchGewonnen(state);
    }

    private static Either<DomainProblem, MatchState> spielerSatzGewonnen(final MatchState state, final SpielerSatzGewonnen event) {
        return MatchScoreEvent.spielerSatzGewonnen(state);
    }

    private static Either<DomainProblem, MatchState> handleGegnerEvent(final MatchState state, final GegnerDomainEvent evt) {
        return switch (evt) {
            case GegnerPunktGewonnen event -> right(state);
            case GegnerGameGewonnen event -> right(state);
            case GegnerSatzGewonnen event -> gegnerSatzGewonnen(state, event);
            case GegnerMatchGewonnen event -> gegnerMatchGewonnen(state, event);
        };
    }

    private static Either<DomainProblem, MatchState> gegnerSatzGewonnen(final MatchState state, final GegnerSatzGewonnen event) {
        return MatchScoreEvent.gegnerSatzGewonnen(state);
    }

    private static Either<DomainProblem, MatchState> gegnerMatchGewonnen(final MatchState state, final GegnerMatchGewonnen event) {
        return MatchScoreEvent.gegnerMatchGewonnen(state);
    }

}
