/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.csatz.evt;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cgame.domain.CGame;
import ch.sth.dojo.beh.cgame.evt.CGameEventHandler;
import ch.sth.dojo.beh.csatz.domain.AbgeschlossenerCSatz;
import ch.sth.dojo.beh.csatz.domain.CSatz;
import ch.sth.dojo.beh.csatz.domain.LaufenderCSatz;
import ch.sth.dojo.beh.csatz.domain.SpielerPunkteSatz;
import ch.sth.dojo.beh.evt.SpielerDomainEvent;
import ch.sth.dojo.beh.evt.SpielerGameGewonnen;
import ch.sth.dojo.beh.evt.SpielerPunktGewonnen;
import ch.sth.dojo.beh.evt.SpielerSatzGewonnen;
import io.vavr.control.Either;
import java.util.function.Function;

interface SpielerEventHandler {

    static Either<DomainProblem, CSatz> handleSpielerEvent(LaufenderCSatz state, SpielerDomainEvent event) {
        return switch (event) {
            case SpielerPunktGewonnen evt -> handlePunktEvent(state, evt);
            case SpielerGameGewonnen evt -> right(handleEvent(state, evt));
            case SpielerSatzGewonnen evt -> right(handleEvent(state, evt));
        };
    }

    private static Either<DomainProblem, CSatz> handlePunktEvent(LaufenderCSatz state, SpielerPunktGewonnen evt) {
        return CGameEventHandler.handleEvent(state.currentGame(), evt)
            .flatMap(toLaufenderCSatz(state));
    }

    private static Function<CGame, Either<DomainProblem, CSatz>> toLaufenderCSatz(final LaufenderCSatz prev) {
        return cGame -> cGame.apply(
            laufendesCGame -> right(new LaufenderCSatz(prev.spielerPunkteSatz(), prev.gegnerPunkteSatz(), laufendesCGame)),
            x -> left(DomainProblem.invalidValue));
    }

    private static CSatz handleEvent(LaufenderCSatz state, SpielerGameGewonnen event) {
        return new LaufenderCSatz(new SpielerPunkteSatz(state.spielerPunkteSatz().value() + 1),
            state.gegnerPunkteSatz(), state.currentGame());
    }

    private static CSatz handleEvent(LaufenderCSatz state, SpielerSatzGewonnen event) {
        return new AbgeschlossenerCSatz();
    }
}
