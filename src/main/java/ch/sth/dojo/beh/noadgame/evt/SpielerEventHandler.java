/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.noadgame.evt;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.evt.SpielerDomainEvent;
import ch.sth.dojo.beh.evt.SpielerGameGewonnen;
import ch.sth.dojo.beh.evt.SpielerMatchGewonnen;
import ch.sth.dojo.beh.evt.SpielerPunktGewonnen;
import ch.sth.dojo.beh.evt.SpielerSatzGewonnen;
import ch.sth.dojo.beh.noadgame.domain.NoAdGame;
import static ch.sth.dojo.beh.noadgame.evt.NoAdGameEventHandler.abgeschlossenToLeft;
import io.vavr.control.Either;

interface SpielerEventHandler {

    static Either<DomainProblem, NoAdGame> handleSpielerEvent(NoAdGame state, SpielerDomainEvent event) {
        return switch (event) {
            case SpielerPunktGewonnen evt -> handleEvent(state, evt);
            case SpielerGameGewonnen evt -> handleEvent(state, evt);
            case SpielerSatzGewonnen evt -> handleEvent(state, evt);
            case SpielerMatchGewonnen evt -> handleEvent(state, evt);
        };
    }

    private static Either<DomainProblem, NoAdGame> handleEvent(NoAdGame state, SpielerMatchGewonnen evt) {
        return state.apply(
            LaufendesNoAdGameEventHandler::spielerMatchGewonnen,
            abgeschlossenToLeft
        );
    }

    private static Either<DomainProblem, NoAdGame> handleEvent(NoAdGame state, SpielerSatzGewonnen evt) {
        return state.apply(
            LaufendesNoAdGameEventHandler::spielerSatzGewonnen,
            abgeschlossenToLeft
        );
    }

    private static Either<DomainProblem, NoAdGame> handleEvent(NoAdGame state, SpielerGameGewonnen evt) {
        return state.apply(
            LaufendesNoAdGameEventHandler::spielerGameGewonnen,
            abgeschlossenToLeft
        );
    }

    private static Either<DomainProblem, NoAdGame> handleEvent(NoAdGame state, SpielerPunktGewonnen evt) {
        return state.apply(
            LaufendesNoAdGameEventHandler::spielerPunktGewonnen,
            abgeschlossenToLeft
        );
    }

}
