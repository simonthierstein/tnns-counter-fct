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
import ch.sth.dojo.beh.csatz.domain.GegnerPunkteSatz;
import ch.sth.dojo.beh.csatz.domain.LaufenderCSatz;
import ch.sth.dojo.beh.csatz.domain.SpielerPunkteSatz;
import ch.sth.dojo.beh.evt.GegnerDomainEvent;
import ch.sth.dojo.beh.evt.GegnerGameGewonnen;
import ch.sth.dojo.beh.evt.GegnerPunktGewonnen;
import ch.sth.dojo.beh.evt.GegnerSatzGewonnen;
import io.vavr.control.Either;
import java.util.function.Function;

interface GegnerEventHandler {

    static Either<DomainProblem, CSatz> handleGegnerEvent(LaufenderCSatz state, GegnerDomainEvent event) {
        return switch (event) {
            case GegnerGameGewonnen evt -> right(handleEvent(state, evt));
            case GegnerPunktGewonnen evt -> handleEvent(state, evt);
            case GegnerSatzGewonnen evt -> right(handleEvent(state, evt));
        };
    }

    private static CSatz handleEvent(LaufenderCSatz state, GegnerSatzGewonnen evt) {
        return new AbgeschlossenerCSatz();
    }

    static CSatz handleEvent(LaufenderCSatz state, GegnerGameGewonnen event) {
        return new LaufenderCSatz(new SpielerPunkteSatz(state.spielerPunkteSatz().value()),
            new GegnerPunkteSatz(state.gegnerPunkteSatz().value() + 1), state.currentGame());
    }

    static Either<DomainProblem, CSatz> handleEvent(LaufenderCSatz state, GegnerPunktGewonnen event) {
        return CGameEventHandler.handleEvent(state.currentGame(), event)
            .flatMap(nextLaufenderSatz(state));
    }

    static Function<CGame, Either<DomainProblem, CSatz>> nextLaufenderSatz(LaufenderCSatz prev) {
        return cgame -> cgame.apply(
            laufendesCGame -> right(new LaufenderCSatz(prev.spielerPunkteSatz(), prev.gegnerPunkteSatz(), laufendesCGame)),
            x -> left(DomainProblem.invalidEvent)
        );
    }

}
