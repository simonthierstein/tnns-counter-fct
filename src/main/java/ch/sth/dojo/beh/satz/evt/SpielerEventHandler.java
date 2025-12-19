/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.satz.evt;

import static ch.sth.dojo.beh.satz.evt.SatzEventHandler.abgeschlossenerSatzToProblem;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.satz.domain.Satz;
import ch.sth.dojo.beh.evt.SpielerDomainEvent;
import ch.sth.dojo.beh.evt.SpielerGameGewonnen;
import ch.sth.dojo.beh.evt.SpielerMatchGewonnen;
import ch.sth.dojo.beh.evt.SpielerPunktGewonnen;
import ch.sth.dojo.beh.evt.SpielerSatzGewonnen;
import io.vavr.control.Either;

interface SpielerEventHandler {

    static Either<DomainProblem, Satz> handleSpielerEvent(Satz prev, SpielerDomainEvent event) {
        return switch (event) {
            case SpielerGameGewonnen evt -> spielerGameGewonnen(prev, evt);
            case SpielerMatchGewonnen evt -> spielerMatchGewonnen(prev, evt);
            case SpielerPunktGewonnen evt -> spielerPunktGewonnen(prev, evt);
            case SpielerSatzGewonnen evt -> spielerSatzGewonnen(prev, evt);
        };
    }

    static Either<DomainProblem, Satz> spielerSatzGewonnen(Satz state, SpielerSatzGewonnen evt) {
        return Satz.apply(state,
            LaufenderSatzEventHandler::spielerSatzGewonnen,
            abgeschlossenerSatzToProblem
        );
    }

    static Either<DomainProblem, Satz> spielerPunktGewonnen(Satz state, SpielerPunktGewonnen evt) {
        return Satz.apply(state,
            LaufenderSatzEventHandler::spielerPunktGewonnen,
            abgeschlossenerSatzToProblem
        );
    }

    static Either<DomainProblem, Satz> spielerMatchGewonnen(Satz state, SpielerMatchGewonnen evt) {
        return Satz.apply(state,
            LaufenderSatzEventHandler::spielerMatchGewonnen,
            abgeschlossenerSatzToProblem
        );
    }

    static Either<DomainProblem, Satz> spielerGameGewonnen(Satz state, SpielerGameGewonnen evt) {
        return Satz.apply(state,
            LaufenderSatzEventHandler::spielerGameGewonnen,
            abgeschlossenerSatzToProblem
        );
    }
}
