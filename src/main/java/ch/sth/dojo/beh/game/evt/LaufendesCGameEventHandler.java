/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.game.evt;

import static io.vavr.control.Either.right;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.game.domain.AbgeschlossenesGame;
import ch.sth.dojo.beh.game.domain.CGame;
import ch.sth.dojo.beh.game.domain.GegnerPunkteBisGame;
import ch.sth.dojo.beh.game.domain.LaufendesCGame;
import ch.sth.dojo.beh.game.domain.SpielerPunkteBisGame;
import ch.sth.dojo.beh.shared.domain.Gewinner;
import ch.sth.dojo.beh.shared.domain.Verlierer;
import io.vavr.control.Either;

class LaufendesCGameEventHandler {
    //
    //    static Either<DomainProblem, CGame> handleEvent(final LaufendesCGame state, final DomainEvent event) {
    //       var res=  DomainEvent.apply(event,
    //            x -> x,
    //            x -> x,
    //            x -> x,
    //            x -> x,
    //            x -> x,
    //            x -> gegnerMatchGewonnen(state,x),
    //            x -> gegnerPunktGewonnen(state,x),
    //            x -> gegnerSatzGewonnen(state,x)
    //        );
    //
    //        return null;
    //    }

    static Either<DomainProblem, CGame> gegnerPunktGewonnen(LaufendesCGame state) {
        return LaufendesCGame.punktGewonnen(state, new Gewinner(state.gegnerPunkteBisGame().value()), new Verlierer(state.spielerPunkteBisGame().value()),
            (gewinner, verlierer) -> LaufendesCGame.LaufendesCGame(new SpielerPunkteBisGame(verlierer.value()), new GegnerPunkteBisGame(gewinner.value())));
    }

    static Either<DomainProblem, CGame> gegnerMatchGewonnen(final LaufendesCGame laufendesCGame) {
        return right(new AbgeschlossenesGame());
    }

    static Either<DomainProblem, CGame> gegnerSatzGewonnen(final LaufendesCGame laufendesCGame) {
        return right(LaufendesCGame.zero());
    }

    static Either<DomainProblem, CGame> spielerMatchGewonnen(final LaufendesCGame state) {
        return right(new AbgeschlossenesGame());
    }

    static Either<DomainProblem, CGame> spielerSatzGewonnen(LaufendesCGame state) {
        return right(LaufendesCGame.zero());
    }

    static Either<DomainProblem, CGame> spielerGameGewonnen(LaufendesCGame state) {
        return right(LaufendesCGame.zero());
    }

    static Either<DomainProblem, CGame> spielerPunktGewonnen(LaufendesCGame state) {
        return LaufendesCGame.punktGewonnen(state, new Gewinner(state.spielerPunkteBisGame().value()), new Verlierer(state.gegnerPunkteBisGame().value()),
            (gewinner, verlierer) -> LaufendesCGame.LaufendesCGame(new SpielerPunkteBisGame(gewinner.value()), new GegnerPunkteBisGame(verlierer.value())));
    }
}
