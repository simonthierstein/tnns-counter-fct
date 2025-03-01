/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.csatz.evt;

import static io.vavr.control.Either.right;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.csatz.domain.AbgeschlossenerCSatz;
import ch.sth.dojo.beh.csatz.domain.CSatz;
import ch.sth.dojo.beh.csatz.domain.GegnerPunkteSatz;
import ch.sth.dojo.beh.csatz.domain.LaufenderCSatz;
import ch.sth.dojo.beh.csatz.domain.SpielerPunkteSatz;
import io.vavr.control.Either;

final class LaufenderCSatzEventHandler {

    static Either<DomainProblem, CSatz> gegnerSatzGewonnen(LaufenderCSatz state) {
        return right(LaufenderCSatz.zero());
    }

    static Either<DomainProblem, CSatz> gegnerPunktGewonnen(LaufenderCSatz state) {
        return right(state);
    }

    static Either<DomainProblem, CSatz> gegnerMatchGewonnen(LaufenderCSatz state) {
        return right(new AbgeschlossenerCSatz());
    }

    static Either<DomainProblem, CSatz> gegnerGameGewonnen(LaufenderCSatz state) {
        return right(new LaufenderCSatz(new SpielerPunkteSatz(state.spielerPunkteSatz().value()),
            new GegnerPunkteSatz(state.gegnerPunkteSatz().value() + 1)));
    }

    static Either<DomainProblem, CSatz> spielerSatzGewonnen(LaufenderCSatz state) {
        return right(LaufenderCSatz.zero());
    }

    static Either<DomainProblem, CSatz> spielerPunktGewonnen(LaufenderCSatz state) {
        return right(state);
    }

    static Either<DomainProblem, CSatz> spielerMatchGewonnen(LaufenderCSatz state) {
        return right(new AbgeschlossenerCSatz());
    }

    static Either<DomainProblem, CSatz> spielerGameGewonnen(LaufenderCSatz state) {
        return right(new LaufenderCSatz(new SpielerPunkteSatz(state.spielerPunkteSatz().value() + 1),
            state.gegnerPunkteSatz()));
    }
}
