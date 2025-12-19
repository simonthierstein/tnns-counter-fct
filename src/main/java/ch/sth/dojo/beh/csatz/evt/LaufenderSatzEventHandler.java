/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.csatz.evt;

import static io.vavr.control.Either.right;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.csatz.domain.AbgeschlossenerSatz;
import ch.sth.dojo.beh.csatz.domain.Satz;
import ch.sth.dojo.beh.csatz.domain.GegnerPunkteSatz;
import ch.sth.dojo.beh.csatz.domain.LaufenderSatz;
import ch.sth.dojo.beh.csatz.domain.SpielerPunkteSatz;
import io.vavr.control.Either;

final class LaufenderSatzEventHandler {

    static Either<DomainProblem, Satz> gegnerSatzGewonnen(LaufenderSatz state) {
        return right(LaufenderSatz.zero());
    }

    static Either<DomainProblem, Satz> gegnerPunktGewonnen(LaufenderSatz state) {
        return right(state);
    }

    static Either<DomainProblem, Satz> gegnerMatchGewonnen(LaufenderSatz state) {
        return right(new AbgeschlossenerSatz());
    }

    static Either<DomainProblem, Satz> gegnerGameGewonnen(LaufenderSatz state) {
        return right(new LaufenderSatz(new SpielerPunkteSatz(state.spielerPunkteSatz().value()),
            new GegnerPunkteSatz(state.gegnerPunkteSatz().value() + 1)));
    }

    static Either<DomainProblem, Satz> spielerSatzGewonnen(LaufenderSatz state) {
        return right(LaufenderSatz.zero());
    }

    static Either<DomainProblem, Satz> spielerPunktGewonnen(LaufenderSatz state) {
        return right(state);
    }

    static Either<DomainProblem, Satz> spielerMatchGewonnen(LaufenderSatz state) {
        return right(new AbgeschlossenerSatz());
    }

    static Either<DomainProblem, Satz> spielerGameGewonnen(LaufenderSatz state) {
        return right(new LaufenderSatz(new SpielerPunkteSatz(state.spielerPunkteSatz().value() + 1),
            state.gegnerPunkteSatz()));
    }
}
