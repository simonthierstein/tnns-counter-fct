/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.game.evt;

import static io.vavr.control.Either.right;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.game.domain.AbgeschlossenesGame;
import ch.sth.dojo.beh.game.domain.Game;
import ch.sth.dojo.beh.game.domain.GegnerPunkteBisGame;
import ch.sth.dojo.beh.game.domain.LaufendesGame;
import ch.sth.dojo.beh.game.domain.SpielerPunkteBisGame;
import ch.sth.dojo.beh.shared.domain.Gewinner;
import ch.sth.dojo.beh.shared.domain.Verlierer;
import io.vavr.control.Either;

class LaufendesGameEventHandler {
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

    static Either<DomainProblem, Game> gegnerPunktGewonnen(LaufendesGame state) {
        return LaufendesGame.punktGewonnen(state, new Gewinner(state.gegnerPunkteBisGame().value()), new Verlierer(state.spielerPunkteBisGame().value()),
            (gewinner, verlierer) -> LaufendesGame.LaufendesGame(new SpielerPunkteBisGame(verlierer.value()), new GegnerPunkteBisGame(gewinner.value())));
    }

    static Either<DomainProblem, Game> gegnerMatchGewonnen(final LaufendesGame laufendesCGame) {
        return right(new AbgeschlossenesGame());
    }

    static Either<DomainProblem, Game> gegnerSatzGewonnen(final LaufendesGame laufendesCGame) {
        return right(LaufendesGame.zero());
    }

    static Either<DomainProblem, Game> spielerMatchGewonnen(final LaufendesGame state) {
        return right(new AbgeschlossenesGame());
    }

    static Either<DomainProblem, Game> spielerSatzGewonnen(LaufendesGame state) {
        return right(LaufendesGame.zero());
    }

    static Either<DomainProblem, Game> spielerGameGewonnen(LaufendesGame state) {
        return right(LaufendesGame.zero());
    }

    static Either<DomainProblem, Game> spielerPunktGewonnen(LaufendesGame state) {
        return LaufendesGame.punktGewonnen(state, new Gewinner(state.spielerPunkteBisGame().value()), new Verlierer(state.gegnerPunkteBisGame().value()),
            (gewinner, verlierer) -> LaufendesGame.LaufendesGame(new SpielerPunkteBisGame(gewinner.value()), new GegnerPunkteBisGame(verlierer.value())));
    }
}
