/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.csatz.evt;

import static ch.sth.dojo.beh.csatz.evt.CSatzEventHandler.abgeschlossenerSatzToProblem;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.csatz.domain.CSatz;
import ch.sth.dojo.beh.evt.SpielerDomainEvent;
import ch.sth.dojo.beh.evt.SpielerGameGewonnen;
import ch.sth.dojo.beh.evt.SpielerMatchGewonnen;
import ch.sth.dojo.beh.evt.SpielerPunktGewonnen;
import ch.sth.dojo.beh.evt.SpielerSatzGewonnen;
import io.vavr.control.Either;

interface SpielerEventHandler {

    static Either<DomainProblem, CSatz> handleSpielerEvent(CSatz prev, SpielerDomainEvent event) {
        return switch (event) {
            case SpielerGameGewonnen evt -> spielerGameGewonnen(prev, evt);
            case SpielerMatchGewonnen evt -> spielerMatchGewonnen(prev, evt);
            case SpielerPunktGewonnen evt -> spielerPunktGewonnen(prev, evt);
            case SpielerSatzGewonnen evt -> spielerSatzGewonnen(prev, evt);
        };
    }

    static Either<DomainProblem, CSatz> spielerSatzGewonnen(CSatz state, SpielerSatzGewonnen evt) {
        return CSatz.apply(state,
            LaufenderCSatzEventHandler::spielerSatzGewonnen,
            abgeschlossenerSatzToProblem
        );
    }

    static Either<DomainProblem, CSatz> spielerPunktGewonnen(CSatz state, SpielerPunktGewonnen evt) {
        return CSatz.apply(state,
            LaufenderCSatzEventHandler::spielerPunktGewonnen,
            abgeschlossenerSatzToProblem
        );
    }

    static Either<DomainProblem, CSatz> spielerMatchGewonnen(CSatz state, SpielerMatchGewonnen evt) {
        return CSatz.apply(state,
            LaufenderCSatzEventHandler::spielerMatchGewonnen,
            abgeschlossenerSatzToProblem
        );
    }

    static Either<DomainProblem, CSatz> spielerGameGewonnen(CSatz state, SpielerGameGewonnen evt) {
        return CSatz.apply(state,
            LaufenderCSatzEventHandler::spielerGameGewonnen,
            abgeschlossenerSatzToProblem
        );
    }
}
