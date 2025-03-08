/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cgame.evt;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cgame.domain.CGame;
import ch.sth.dojo.beh.cgame.domain.LaufendesCGame;
import ch.sth.dojo.beh.evt.GegnerDomainEvent;
import ch.sth.dojo.beh.evt.GegnerGameGewonnen;
import ch.sth.dojo.beh.evt.GegnerMatchGewonnen;
import ch.sth.dojo.beh.evt.GegnerPunktGewonnen;
import ch.sth.dojo.beh.evt.GegnerSatzGewonnen;
import io.vavr.control.Either;
import static io.vavr.control.Either.right;

interface GegnerEventHandler {

    static Either<DomainProblem, CGame> handleGegnerEvent(CGame state, GegnerDomainEvent event) {
        return GegnerDomainEvent.apply(event,
            evt -> handleEvent(state, evt),
            evt -> handleEvent(state, evt),
            evt -> handleEvent(state, evt),
            evt -> handleEvent(state, evt)
        );
    }

    private static Either<DomainProblem, CGame> handleEvent(CGame state, GegnerMatchGewonnen evt) {
        return state.apply(
            LaufendesCGameEventHandler::gegnerMatchGewonnen,
            CGameEventHandler.abgeschlossenToLeft
        );
    }

    private static Either<DomainProblem, CGame> handleEvent(CGame state, GegnerSatzGewonnen evt) {
        return state.apply(
            LaufendesCGameEventHandler::gegnerSatzGewonnen,
            CGameEventHandler.abgeschlossenToLeft
        );
    }

    private static Either<DomainProblem, CGame> handleEvent(CGame state, GegnerPunktGewonnen evt) {
        return state.apply(
            LaufendesCGameEventHandler::gegnerPunktGewonnen,
            CGameEventHandler.abgeschlossenToLeft
        );
    }

    private static Either<DomainProblem, CGame> handleEvent(CGame state, GegnerGameGewonnen evt) {
        return right(LaufendesCGame.zero());
    }

}

