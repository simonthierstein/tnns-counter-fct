/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.csatz.evt;

import static ch.sth.dojo.beh.csatz.evt.SatzEventHandler.abgeschlossenerSatzToProblem;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.csatz.domain.AbgeschlossenerSatz;
import ch.sth.dojo.beh.csatz.domain.Satz;
import ch.sth.dojo.beh.csatz.domain.GegnerPunkteSatz;
import ch.sth.dojo.beh.csatz.domain.LaufenderSatz;
import ch.sth.dojo.beh.csatz.domain.SpielerPunkteSatz;
import ch.sth.dojo.beh.evt.GegnerDomainEvent;
import ch.sth.dojo.beh.evt.GegnerGameGewonnen;
import ch.sth.dojo.beh.evt.GegnerMatchGewonnen;
import ch.sth.dojo.beh.evt.GegnerPunktGewonnen;
import ch.sth.dojo.beh.evt.GegnerSatzGewonnen;
import io.vavr.control.Either;

interface GegnerEventHandler {

    static Either<DomainProblem, Satz> handleGegnerEvent(Satz prev, GegnerDomainEvent event) {
        return switch (event) {
            case GegnerGameGewonnen evt -> gegnerGameGewonnen(prev, evt);
            case GegnerMatchGewonnen evt -> gegnerMatchGewonnen(prev, evt);
            case GegnerPunktGewonnen evt -> gegnerPunktGewonnen(prev, evt);
            case GegnerSatzGewonnen evt -> gegnerSatzGewonnen(prev, evt);
        };
    }

    static Either<DomainProblem, Satz> gegnerSatzGewonnen(Satz prev, GegnerSatzGewonnen evt) {
        return Satz.apply(prev,
            LaufenderSatzEventHandler::gegnerSatzGewonnen,
            abgeschlossenerSatzToProblem
        );
    }

    static Either<DomainProblem, Satz> gegnerPunktGewonnen(Satz prev, GegnerPunktGewonnen evt) {
        return Satz.apply(prev,
            LaufenderSatzEventHandler::gegnerPunktGewonnen,
            abgeschlossenerSatzToProblem
        );

    }

    static Either<DomainProblem, Satz> gegnerMatchGewonnen(Satz prev, GegnerMatchGewonnen evt) {
        return Satz.apply(prev,
            LaufenderSatzEventHandler::gegnerMatchGewonnen,
            abgeschlossenerSatzToProblem
        );

    }

    static Either<DomainProblem, Satz> gegnerGameGewonnen(Satz prev, GegnerGameGewonnen evt) {
        return Satz.apply(prev,
            LaufenderSatzEventHandler::gegnerGameGewonnen,
            abgeschlossenerSatzToProblem
        );
    }

    static Either<DomainProblem, Satz> handleGegnerEvent(LaufenderSatz state, GegnerDomainEvent event) {
        return switch (event) {
            case GegnerGameGewonnen evt -> right(handleEvent(state, evt));
            case GegnerSatzGewonnen evt -> right(handleEvent(state, evt));
            default -> left(DomainProblem.eventNotValid);
        };
    }

    private static Satz handleEvent(LaufenderSatz state, GegnerSatzGewonnen evt) {
        return new AbgeschlossenerSatz();
    }

    static Satz handleEvent(LaufenderSatz state, GegnerGameGewonnen event) {
        return new LaufenderSatz(new SpielerPunkteSatz(state.spielerPunkteSatz().value()),
            new GegnerPunkteSatz(state.gegnerPunkteSatz().value() + 1));
    }
}
