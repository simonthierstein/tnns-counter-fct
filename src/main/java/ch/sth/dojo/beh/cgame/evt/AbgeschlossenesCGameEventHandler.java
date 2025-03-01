/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cgame.evt;

import static io.vavr.control.Either.left;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cgame.domain.AbgeschlossenesCGame;
import ch.sth.dojo.beh.cgame.domain.CGame;
import ch.sth.dojo.beh.evt.GegnerMatchGewonnen;
import ch.sth.dojo.beh.evt.GegnerSatzGewonnen;
import io.vavr.control.Either;

final class AbgeschlossenesCGameEventHandler {

    static Either<DomainProblem, CGame> gegnerMatchGewonnen(final AbgeschlossenesCGame state, final GegnerMatchGewonnen event) {
        return left(DomainProblem.eventNotValid);
    }

    static Either<DomainProblem, CGame> gegnerSatzGewonnen(final AbgeschlossenesCGame abgeschlossenesCGame, final GegnerSatzGewonnen evt) {
        return left(DomainProblem.eventNotValid);
    }
}
