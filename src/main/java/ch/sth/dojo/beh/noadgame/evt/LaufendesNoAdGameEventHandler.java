/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.noadgame.evt;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.noadgame.domain.AbgeschlossenesNoAdGame;
import ch.sth.dojo.beh.noadgame.domain.LaufendesNoAdGame;
import ch.sth.dojo.beh.noadgame.domain.NoAdGame;
import io.vavr.control.Either;
import static io.vavr.control.Either.right;

class LaufendesNoAdGameEventHandler {

    static Either<DomainProblem, NoAdGame> gegnerPunktGewonnen(LaufendesNoAdGame state) {
        return LaufendesNoAdGame.gegnerPunktGewonnen(state, LaufendesNoAdGame.ofVerliererGewinner);
    }

    static Either<DomainProblem, NoAdGame> gegnerMatchGewonnen(final LaufendesNoAdGame laufendesNoAdGame) {
        return right(new AbgeschlossenesNoAdGame());
    }

    static Either<DomainProblem, NoAdGame> gegnerSatzGewonnen(final LaufendesNoAdGame laufendesNoAdGame) {
        return right(LaufendesNoAdGame.zero());
    }

    static Either<DomainProblem, NoAdGame> spielerMatchGewonnen(final LaufendesNoAdGame state) {
        return right(new AbgeschlossenesNoAdGame());
    }

    static Either<DomainProblem, NoAdGame> spielerSatzGewonnen(LaufendesNoAdGame state) {
        return right(LaufendesNoAdGame.zero());
    }

    static Either<DomainProblem, NoAdGame> spielerGameGewonnen(LaufendesNoAdGame state) {
        return right(LaufendesNoAdGame.zero());
    }

    static Either<DomainProblem, NoAdGame> spielerPunktGewonnen(LaufendesNoAdGame state) {
        return LaufendesNoAdGame.spielerPunktGewonnen(state, LaufendesNoAdGame.ofGewinnerVerlierer);
    }
}
