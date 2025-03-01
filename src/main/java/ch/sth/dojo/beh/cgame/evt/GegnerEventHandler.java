/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cgame.evt;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cgame.domain.CGame;
import ch.sth.dojo.beh.cgame.domain.LaufendesCGame;
import ch.sth.dojo.beh.evt.GegnerDomainEvent;
import ch.sth.dojo.beh.evt.GegnerGameGewonnen;
import ch.sth.dojo.beh.evt.GegnerMatchGewonnen;
import ch.sth.dojo.beh.evt.GegnerPunktGewonnen;
import ch.sth.dojo.beh.evt.GegnerSatzGewonnen;
import io.vavr.control.Either;

interface GegnerEventHandler {

    static Either<DomainProblem, CGame> handleGegnerEvent(CGame state, GegnerDomainEvent event) {
        return switch (event) {
            case GegnerGameGewonnen evt -> handleEvent(state, evt);
            case GegnerPunktGewonnen evt -> handleEvent(state, evt);
            case GegnerSatzGewonnen evt -> handleEvent(state, evt);
            case GegnerMatchGewonnen evt -> handleEvent(state, evt);
        };
    }

    static Either<DomainProblem, CGame> handleEvent(CGame state, GegnerMatchGewonnen evt) {
        return state.apply(
            laufendesCGame -> LaufendesCGameEventHandler.gegnerMatchGewonnen(laufendesCGame, evt),
            tiebreak -> TiebreakEventHandler.gegnerMatchGewonnen(tiebreak, evt),
            abgeschlossenesCGame -> AbgeschlossenesCGameEventHandler.gegnerMatchGewonnen(abgeschlossenesCGame, evt)
        );
    }

    static Either<DomainProblem, CGame> handleEvent(CGame state, GegnerSatzGewonnen evt) {
        return state.apply(
            laufendesCGame -> LaufendesCGameEventHandler.gegnerSatzGewonnen(laufendesCGame, evt),
            tiebreak -> TiebreakEventHandler.gegnerSatzGewonnen(tiebreak, evt),
            abgeschlossenesCGame -> AbgeschlossenesCGameEventHandler.gegnerSatzGewonnen(abgeschlossenesCGame, evt)
        );
    }

    static Either<DomainProblem, CGame> handleEvent(CGame state, GegnerPunktGewonnen evt) {
        return state.apply(
            laufendesCGame -> LaufendesCGameEventHandler.handleEvent(laufendesCGame, evt),
            tiebreak -> TiebreakEventHandler.handleEvent(tiebreak, evt),
            abgeschlossenesCGame -> left(DomainProblem.eventNotValid)
        );
    }

    static Either<DomainProblem, CGame> handleEvent(CGame state, GegnerGameGewonnen evt) {
        return right(LaufendesCGame.zero());
    }

}

