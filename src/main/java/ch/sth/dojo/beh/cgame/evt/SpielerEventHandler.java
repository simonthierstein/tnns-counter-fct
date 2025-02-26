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
import ch.sth.dojo.beh.evt.SpielerDomainEvent;
import ch.sth.dojo.beh.evt.SpielerGameGewonnen;
import ch.sth.dojo.beh.evt.SpielerPunktGewonnen;
import ch.sth.dojo.beh.evt.SpielerSatzGewonnen;
import ch.sth.dojo.beh.shared.domain.Gewinner;
import ch.sth.dojo.beh.shared.domain.Verlierer;
import io.vavr.control.Either;

public interface SpielerEventHandler {

    static Either<DomainProblem, CGame> handleSpielerEvent(LaufendesCGame state, SpielerDomainEvent event) {
        return switch (event) {
            case SpielerPunktGewonnen evt -> handleEvent(state, evt);
            case SpielerGameGewonnen evt -> right(handleEvent(state, evt));
            case SpielerSatzGewonnen evt -> right(handleEvent(state, evt));
        };
    }

    static AbgeschlossenesCGame handleEvent(LaufendesCGame state, SpielerSatzGewonnen event) {
        return new AbgeschlossenesCGame();
    }

    static Either<DomainProblem, CGame> handleEvent(LaufendesCGame state, SpielerPunktGewonnen event) {
        return LaufendesCGame.punktGewonnen(state, new Gewinner(state.spielerPunkteBisGame().value()), new Verlierer(state.gegnerPunkteBisGame().value()),
            (gewinner, verlierer) -> LaufendesCGame.LaufendesCGame(new SpielerPunkteBisGame(gewinner.value()), new GegnerPunkteBisGame(verlierer.value())));
    }

    static CGame handleEvent(LaufendesCGame state, SpielerGameGewonnen event) {
        return LaufendesCGame.zero();
    }
}
