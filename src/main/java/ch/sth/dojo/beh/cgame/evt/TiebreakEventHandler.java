/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cgame.evt;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cgame.domain.CGame;
import ch.sth.dojo.beh.cgame.domain.Tiebreak;
import ch.sth.dojo.beh.evt.GegnerDomainEvent;
import ch.sth.dojo.beh.evt.GegnerGameGewonnen;
import ch.sth.dojo.beh.evt.GegnerMatchGewonnen;
import ch.sth.dojo.beh.evt.GegnerPunktGewonnen;
import ch.sth.dojo.beh.evt.GegnerSatzGewonnen;
import io.vavr.control.Either;

class TiebreakEventHandler {

    public static Either<DomainProblem, CGame> handleGegnerTBEvent(Tiebreak state, GegnerDomainEvent evt) {
        return switch (evt) {
            case GegnerGameGewonnen event -> handleEvent(state, event);
            case GegnerPunktGewonnen event -> handleEvent(state, event);
            case GegnerSatzGewonnen event -> handleEvent(state, event);
            case GegnerMatchGewonnen event -> gegnerMatchGewonnen(state, event);
        };

    }

    public static Either<DomainProblem, CGame> gegnerMatchGewonnen(Tiebreak state, GegnerMatchGewonnen event) {
        return right(state.gegnerPunktGewonnen());
    }

    public static Either<DomainProblem, CGame> handleEvent(Tiebreak state, GegnerSatzGewonnen event) {
        return right(state.gegnerPunktGewonnen());
    }

    public static Either<DomainProblem, CGame> handleEvent(Tiebreak state, GegnerPunktGewonnen event) {
        return right(state.gegnerPunktGewonnen());
    }

    public static Either<DomainProblem, CGame> handleEvent(Tiebreak state, GegnerGameGewonnen event) {
        return left(DomainProblem.eventNotValid);
    }

    public static Either<DomainProblem, CGame> gegnerSatzGewonnen(final Tiebreak state, final GegnerSatzGewonnen evt) {
        return right(state.gegnerPunktGewonnen());
    }
}
