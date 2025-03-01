/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cgame.evt;

import static io.vavr.control.Either.right;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cgame.domain.AbgeschlossenesCGame;
import ch.sth.dojo.beh.cgame.domain.CGame;
import ch.sth.dojo.beh.cgame.domain.GegnerPunkteBisGame;
import ch.sth.dojo.beh.cgame.domain.LaufendesCGame;
import ch.sth.dojo.beh.cgame.domain.SpielerPunkteBisGame;
import ch.sth.dojo.beh.evt.GegnerMatchGewonnen;
import ch.sth.dojo.beh.evt.GegnerPunktGewonnen;
import ch.sth.dojo.beh.evt.GegnerSatzGewonnen;
import ch.sth.dojo.beh.shared.domain.Gewinner;
import ch.sth.dojo.beh.shared.domain.Verlierer;
import io.vavr.control.Either;

class LaufendesCGameEventHandler {

    static Either<DomainProblem, CGame> handleEvent(LaufendesCGame state, GegnerPunktGewonnen event) {
        return LaufendesCGame.punktGewonnen(state, new Gewinner(state.gegnerPunkteBisGame().value()), new Verlierer(state.spielerPunkteBisGame().value()),
            (gewinner, verlierer) -> LaufendesCGame.LaufendesCGame(new SpielerPunkteBisGame(verlierer.value()), new GegnerPunkteBisGame(gewinner.value())));
    }

    static Either<DomainProblem, CGame> gegnerMatchGewonnen(final LaufendesCGame laufendesCGame, final GegnerMatchGewonnen evt) {
        return right(new AbgeschlossenesCGame());
    }

    static Either<DomainProblem, CGame> gegnerSatzGewonnen(final LaufendesCGame laufendesCGame, final GegnerSatzGewonnen evt) {
        return right(new AbgeschlossenesCGame());
    }
}
