/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cgame.evt;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cgame.domain.AbgeschlossenesCGame;
import ch.sth.dojo.beh.cgame.domain.CGame;
import ch.sth.dojo.beh.cgame.domain.GegnerPunkteBisGame;
import ch.sth.dojo.beh.cgame.domain.LaufendesCGame;
import ch.sth.dojo.beh.cgame.domain.SpielerPunkteBisGame;
import ch.sth.dojo.beh.cgame.domain.Tiebreak;
import ch.sth.dojo.beh.evt.GegnerDomainEvent;
import ch.sth.dojo.beh.evt.GegnerGameGewonnen;
import ch.sth.dojo.beh.evt.GegnerMatchGewonnen;
import ch.sth.dojo.beh.evt.GegnerPunktGewonnen;
import ch.sth.dojo.beh.evt.GegnerSatzGewonnen;
import ch.sth.dojo.beh.shared.domain.Gewinner;
import ch.sth.dojo.beh.shared.domain.Verlierer;
import io.vavr.control.Either;

interface GegnerEventHandler {

    static Either<DomainProblem, CGame> handleGegnerEvent(LaufendesCGame state, GegnerDomainEvent event) {
        return switch (event) {
            case GegnerGameGewonnen evt -> right(handleEvent(state, evt));
            case GegnerPunktGewonnen evt -> handleEvent(state, evt);
            case GegnerSatzGewonnen evt -> right(handleEvent(state, evt));
            case GegnerMatchGewonnen evt -> right(handleEvent(state, evt));
        };
    }

    static CGame handleEvent(LaufendesCGame state, GegnerGameGewonnen event) {
        return LaufendesCGame.zero();
    }

    static AbgeschlossenesCGame handleEvent(LaufendesCGame state, GegnerMatchGewonnen event) {
        return new AbgeschlossenesCGame();
    }
    static AbgeschlossenesCGame handleEvent(LaufendesCGame state, GegnerSatzGewonnen event) {
        return new AbgeschlossenesCGame();
    }
    static Either<DomainProblem, CGame> handleEvent(LaufendesCGame state, GegnerPunktGewonnen event) {
        return LaufendesCGame.punktGewonnen(state, new Gewinner(state.gegnerPunkteBisGame().value()), new Verlierer(state.spielerPunkteBisGame().value()),
            (gewinner, verlierer) -> LaufendesCGame.LaufendesCGame(new SpielerPunkteBisGame(verlierer.value()), new GegnerPunkteBisGame(gewinner.value())));
    }

    static Either<DomainProblem, CGame> handleGegnerTBEvent(Tiebreak state, GegnerDomainEvent evt) {
        return switch (evt) {
            case GegnerGameGewonnen event -> handleEvent(state, event);
            case GegnerPunktGewonnen event -> handleEvent(state, event);
            case GegnerSatzGewonnen event -> handleEvent(state, event);
            case GegnerMatchGewonnen event -> handleEvent(state, event);
        };

    }

    static Either<DomainProblem, CGame> handleEvent(Tiebreak state, GegnerMatchGewonnen event) {
        return right(state.gegnerPunktGewonnen());
    }

    static Either<DomainProblem, CGame> handleEvent(Tiebreak state, GegnerSatzGewonnen event) {
        return right(state.gegnerPunktGewonnen());
    }

    static Either<DomainProblem, CGame> handleEvent(Tiebreak state, GegnerPunktGewonnen event) {
        return right(state.gegnerPunktGewonnen());
    }

    static Either<DomainProblem, CGame> handleEvent(Tiebreak state, GegnerGameGewonnen event) {
        return left(DomainProblem.eventNotValid);
    }

}
