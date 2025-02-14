/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.csatz.evt;

import static io.vavr.control.Either.right;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.csatz.domain.CSatz;
import ch.sth.dojo.beh.csatz.domain.LaufenderCSatz;
import ch.sth.dojo.beh.evt.GegnerDomainEvent;
import ch.sth.dojo.beh.evt.GegnerGameGewonnen;
import ch.sth.dojo.beh.evt.GegnerPunktGewonnen;
import io.vavr.control.Either;

interface GegnerEventHandler {

    static Either<DomainProblem, CSatz> handleGegnerEvent(LaufenderCSatz state, GegnerDomainEvent event) {
        return switch (event) {
            case GegnerGameGewonnen evt -> right(handleEvent(state, evt));
            case GegnerPunktGewonnen evt -> handleEvent(state, evt);
        };
    }

    static CSatz handleEvent(LaufenderCSatz state, GegnerGameGewonnen event) {
        throw new RuntimeException();
    }

    static Either<DomainProblem, CSatz> handleEvent(LaufenderCSatz state, GegnerPunktGewonnen event) {
        new GameGewinner(state.gegnerPunkteBisSatz().value());
        new GameVerlierer(state.spielerPunkteBisSatz().value());

    }
}
