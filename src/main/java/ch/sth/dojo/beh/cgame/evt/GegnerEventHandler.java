/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cgame.evt;

import static io.vavr.control.Either.right;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cgame.domain.game.AbgeschlossenesCGame;
import ch.sth.dojo.beh.cgame.domain.game.CGame;
import ch.sth.dojo.beh.cgame.domain.game.GegnerPunkteBisGame;
import ch.sth.dojo.beh.cgame.domain.game.Gewinner;
import ch.sth.dojo.beh.cgame.domain.game.LaufendesCGame;
import ch.sth.dojo.beh.cgame.domain.game.SpielerPunkteBisGame;
import ch.sth.dojo.beh.cgame.domain.game.Verlierer;
import ch.sth.dojo.beh.evt.GegnerDomainEvent;
import ch.sth.dojo.beh.evt.GegnerGameGewonnen;
import ch.sth.dojo.beh.evt.GegnerPunktGewonnen;
import io.vavr.control.Either;

public interface GegnerEventHandler {

    static Either<DomainProblem, CGame> handleGegnerEvent(LaufendesCGame state, GegnerDomainEvent event) {
        return switch (event) {
            case GegnerGameGewonnen evt -> right(handleEvent(state, evt));
            case GegnerPunktGewonnen evt -> handleEvent(state, evt);
        };
    }

    static CGame handleEvent(LaufendesCGame state, GegnerGameGewonnen event) {
        return new AbgeschlossenesCGame();
    }

    static Either<DomainProblem, CGame> handleEvent(LaufendesCGame state, GegnerPunktGewonnen event) {
        return LaufendesCGame.punktGewonnen(state, new Gewinner(state.gegnerPunkteBisGame().value()), new Verlierer(state.spielerPunkteBisGame().value()),
            (gewinner, verlierer) -> new LaufendesCGame(new SpielerPunkteBisGame(verlierer.value()), new GegnerPunkteBisGame(gewinner.value())));
    }
}
