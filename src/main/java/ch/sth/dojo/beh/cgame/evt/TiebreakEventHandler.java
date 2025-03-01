/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cgame.evt;

import static io.vavr.control.Either.right;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cgame.domain.CGame;
import ch.sth.dojo.beh.cgame.domain.Tiebreak;
import io.vavr.control.Either;

class TiebreakEventHandler {

    //    public static Either<DomainProblem, CGame> handleGegnerTBEvent(Tiebreak state, GegnerDomainEvent evt) {
    //        return switch (evt) {
    //            case GegnerGameGewonnen event -> handleEvent(state, event);
    //            case GegnerPunktGewonnen event -> gegnerPunktGewonnen(state);
    //            case GegnerSatzGewonnen event -> handleEvent(state, event);
    //            case GegnerMatchGewonnen event -> gegnerMatchGewonnen(state);
    //        };
    //
    //    }

    static Either<DomainProblem, CGame> gegnerMatchGewonnen(Tiebreak state) {
        return right(state.gegnerPunktGewonnen());
    }

    static Either<DomainProblem, CGame> spielerMatchGewonnen(final Tiebreak state) {
        return right(state.spielerPunktGewonnen());
    }

    public static Either<DomainProblem, CGame> gegnerPunktGewonnen(Tiebreak state) {
        return right(state.gegnerPunktGewonnen());
    }

    public static Either<DomainProblem, CGame> gegnerSatzGewonnen(final Tiebreak state) {
        return right(state.gegnerPunktGewonnen());
    }

    static Either<DomainProblem, CGame> spielerSatzGewonnen(Tiebreak state) {
        return right(state.spielerPunktGewonnen());
    }

    static Either<DomainProblem, CGame> spielerPunktGewonnen(Tiebreak state) {
        return right(state.spielerPunktGewonnen());
    }
}
