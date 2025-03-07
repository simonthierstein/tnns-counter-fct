/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.noadgame.evt;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.evt.GegnerDomainEvent;
import ch.sth.dojo.beh.evt.GegnerGameGewonnen;
import ch.sth.dojo.beh.evt.GegnerMatchGewonnen;
import ch.sth.dojo.beh.evt.GegnerPunktGewonnen;
import ch.sth.dojo.beh.evt.GegnerSatzGewonnen;
import ch.sth.dojo.beh.noadgame.domain.LaufendesNoAdGame;
import ch.sth.dojo.beh.noadgame.domain.NoAdGame;
import static ch.sth.dojo.beh.noadgame.evt.NoAdGameEventHandler.abgeschlossenToLeft;
import io.vavr.control.Either;
import static io.vavr.control.Either.right;

interface GegnerEventHandler {

    static Either<DomainProblem, NoAdGame> handleGegnerEvent(NoAdGame state, GegnerDomainEvent event) {
        return GegnerDomainEvent.apply(event,
            evt -> handleEvent(state, evt),
            evt -> handleEvent(state, evt),
            evt -> handleEvent(state, evt),
            evt -> handleEvent(state, evt)
        );
    }

    private static Either<DomainProblem, NoAdGame> handleEvent(NoAdGame state, GegnerMatchGewonnen evt) {
        return state.apply(
            LaufendesNoAdGameEventHandler::gegnerMatchGewonnen,
            abgeschlossenToLeft
        );
    }

    private static Either<DomainProblem, NoAdGame> handleEvent(NoAdGame state, GegnerSatzGewonnen evt) {
        return state.apply(
            LaufendesNoAdGameEventHandler::gegnerSatzGewonnen,
            abgeschlossenToLeft
        );
    }

    private static Either<DomainProblem, NoAdGame> handleEvent(NoAdGame state, GegnerPunktGewonnen evt) {
        return state.apply(
            LaufendesNoAdGameEventHandler::gegnerPunktGewonnen,
            abgeschlossenToLeft
        );
    }

    private static Either<DomainProblem, NoAdGame> handleEvent(NoAdGame state, GegnerGameGewonnen evt) {
        return right(LaufendesNoAdGame.zero());
    }

}

