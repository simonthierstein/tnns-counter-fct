/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cgame.evt;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cgame.domain.CGame;
import ch.sth.dojo.beh.cgame.domain.GegnerPunkteBisGame;
import ch.sth.dojo.beh.cgame.domain.LaufendesCGame;
import ch.sth.dojo.beh.cgame.domain.SpielerPunkteBisGame;
import ch.sth.dojo.beh.evt.GegnerPunktGewonnen;
import ch.sth.dojo.beh.shared.domain.Gewinner;
import ch.sth.dojo.beh.shared.domain.Verlierer;
import io.vavr.control.Either;

class LaufendesCGameEventHandler {

    public static Either<DomainProblem, CGame> handleEvent(LaufendesCGame state, GegnerPunktGewonnen event) {
        return LaufendesCGame.punktGewonnen(state, new Gewinner(state.gegnerPunkteBisGame().value()), new Verlierer(state.spielerPunkteBisGame().value()),
            (gewinner, verlierer) -> LaufendesCGame.LaufendesCGame(new SpielerPunkteBisGame(verlierer.value()), new GegnerPunkteBisGame(gewinner.value())));
    }
}
