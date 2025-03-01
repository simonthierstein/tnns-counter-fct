/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.csatz.evt;

import static ch.sth.dojo.beh.csatz.evt.CSatzEventHandler.abgeschlossenerSatzToProblem;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.csatz.domain.AbgeschlossenerCSatz;
import ch.sth.dojo.beh.csatz.domain.CSatz;
import ch.sth.dojo.beh.csatz.domain.GegnerPunkteSatz;
import ch.sth.dojo.beh.csatz.domain.LaufenderCSatz;
import ch.sth.dojo.beh.csatz.domain.SpielerPunkteSatz;
import ch.sth.dojo.beh.evt.GegnerDomainEvent;
import ch.sth.dojo.beh.evt.GegnerGameGewonnen;
import ch.sth.dojo.beh.evt.GegnerMatchGewonnen;
import ch.sth.dojo.beh.evt.GegnerPunktGewonnen;
import ch.sth.dojo.beh.evt.GegnerSatzGewonnen;
import io.vavr.control.Either;

interface GegnerEventHandler {

    static Either<DomainProblem, CSatz> handleGegnerEvent(CSatz prev, GegnerDomainEvent event) {
        return switch (event) {
            case GegnerGameGewonnen evt -> gegnerGameGewonnen(prev, evt);
            case GegnerMatchGewonnen evt -> gegnerMatchGewonnen(prev, evt);
            case GegnerPunktGewonnen evt -> gegnerPunktGewonnen(prev, evt);
            case GegnerSatzGewonnen evt -> gegnerSatzGewonnen(prev, evt);
        };
    }

    static Either<DomainProblem, CSatz> gegnerSatzGewonnen(CSatz prev, GegnerSatzGewonnen evt) {
        return CSatz.apply(prev,
            LaufenderCSatzEventHandler::gegnerSatzGewonnen,
            abgeschlossenerSatzToProblem
        );
    }

    static Either<DomainProblem, CSatz> gegnerPunktGewonnen(CSatz prev, GegnerPunktGewonnen evt) {
        return CSatz.apply(prev,
            LaufenderCSatzEventHandler::gegnerPunktGewonnen,
            abgeschlossenerSatzToProblem
        );

    }

    static Either<DomainProblem, CSatz> gegnerMatchGewonnen(CSatz prev, GegnerMatchGewonnen evt) {
        return CSatz.apply(prev,
            LaufenderCSatzEventHandler::gegnerMatchGewonnen,
            abgeschlossenerSatzToProblem
        );

    }

    static Either<DomainProblem, CSatz> gegnerGameGewonnen(CSatz prev, GegnerGameGewonnen evt) {
        return CSatz.apply(prev,
            LaufenderCSatzEventHandler::gegnerGameGewonnen,
            abgeschlossenerSatzToProblem
        );
    }

    static Either<DomainProblem, CSatz> handleGegnerEvent(LaufenderCSatz state, GegnerDomainEvent event) {
        return switch (event) {
            case GegnerGameGewonnen evt -> right(handleEvent(state, evt));
            case GegnerSatzGewonnen evt -> right(handleEvent(state, evt));
            default -> left(DomainProblem.eventNotValid);
        };
    }

    private static CSatz handleEvent(LaufenderCSatz state, GegnerSatzGewonnen evt) {
        return new AbgeschlossenerCSatz();
    }

    static CSatz handleEvent(LaufenderCSatz state, GegnerGameGewonnen event) {
        return new LaufenderCSatz(new SpielerPunkteSatz(state.spielerPunkteSatz().value()),
            new GegnerPunkteSatz(state.gegnerPunkteSatz().value() + 1));
    }
}
