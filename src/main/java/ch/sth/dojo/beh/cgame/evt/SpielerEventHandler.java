/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cgame.evt;

import static ch.sth.dojo.beh.cgame.evt.CGameEventHandler.abgeschlossenToLeft;
import static ch.sth.dojo.beh.cgame.evt.CGameEventHandler.tiebreakToLeft;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cgame.domain.CGame;
import ch.sth.dojo.beh.evt.SpielerDomainEvent;
import ch.sth.dojo.beh.evt.SpielerGameGewonnen;
import ch.sth.dojo.beh.evt.SpielerMatchGewonnen;
import ch.sth.dojo.beh.evt.SpielerPunktGewonnen;
import ch.sth.dojo.beh.evt.SpielerSatzGewonnen;
import io.vavr.control.Either;

public interface SpielerEventHandler {

    static Either<DomainProblem, CGame> handleSpielerEvent(CGame state, SpielerDomainEvent event) {
        return switch (event) {
            case SpielerPunktGewonnen evt -> handleEvent(state, evt);
            case SpielerGameGewonnen evt -> handleEvent(state, evt);
            case SpielerSatzGewonnen evt -> handleEvent(state, evt);
            case SpielerMatchGewonnen evt -> handleEvent(state, evt);
        };
    }

    static Either<DomainProblem, CGame> handleEvent(CGame state, SpielerMatchGewonnen evt) {
        return state.apply(
            LaufendesCGameEventHandler::spielerMatchGewonnen,
            TiebreakEventHandler::spielerMatchGewonnen,
            abgeschlossenToLeft
        );
    }

    static Either<DomainProblem, CGame> handleEvent(CGame state, SpielerSatzGewonnen evt) {
        return state.apply(
            LaufendesCGameEventHandler::spielerSatzGewonnen,
            TiebreakEventHandler::spielerSatzGewonnen,
            abgeschlossenToLeft
        );
    }

    static Either<DomainProblem, CGame> handleEvent(CGame state, SpielerGameGewonnen evt) {
        return state.apply(
            LaufendesCGameEventHandler::spielerGameGewonnen,
            tiebreakToLeft,
            abgeschlossenToLeft
        );
    }

    static Either<DomainProblem, CGame> handleEvent(CGame state, SpielerPunktGewonnen evt) {
        return state.apply(
            LaufendesCGameEventHandler::spielerPunktGewonnen,
            TiebreakEventHandler::spielerPunktGewonnen,
            abgeschlossenToLeft
        );
    }

}
