/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.game.evt;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.game.domain.Game;
import static ch.sth.dojo.beh.game.evt.GameEventHandler.abgeschlossenToLeft;
import ch.sth.dojo.beh.evt.SpielerDomainEvent;
import ch.sth.dojo.beh.evt.SpielerGameGewonnen;
import ch.sth.dojo.beh.evt.SpielerMatchGewonnen;
import ch.sth.dojo.beh.evt.SpielerPunktGewonnen;
import ch.sth.dojo.beh.evt.SpielerSatzGewonnen;
import io.vavr.control.Either;

public interface SpielerEventHandler {

    static Either<DomainProblem, Game> handleSpielerEvent(Game state, SpielerDomainEvent event) {
        return switch (event) {
            case SpielerPunktGewonnen evt -> handleEvent(state, evt);
            case SpielerGameGewonnen evt -> handleEvent(state, evt);
            case SpielerSatzGewonnen evt -> handleEvent(state, evt);
            case SpielerMatchGewonnen evt -> handleEvent(state, evt);
        };
    }

    private static Either<DomainProblem, Game> handleEvent(Game state, SpielerMatchGewonnen evt) {
        return state.apply(
            LaufendesGameEventHandler::spielerMatchGewonnen,
            abgeschlossenToLeft,
            TiebreakEventHandler.handleWithNarrow(evt)
        );
    }

    private static Either<DomainProblem, Game> handleEvent(Game state, SpielerSatzGewonnen evt) {
        return state.apply(
            LaufendesGameEventHandler::spielerSatzGewonnen,
            abgeschlossenToLeft,
            TiebreakEventHandler.handleWithNarrow(evt)
        );
    }

    private static Either<DomainProblem, Game> handleEvent(Game state, SpielerGameGewonnen evt) {
        return state.apply(
            LaufendesGameEventHandler::spielerGameGewonnen,
            abgeschlossenToLeft,
            TiebreakEventHandler.handleWithNarrow(evt)
        );
    }

    private static Either<DomainProblem, Game> handleEvent(Game state, SpielerPunktGewonnen evt) {
        return state.apply(
            LaufendesGameEventHandler::spielerPunktGewonnen,
            abgeschlossenToLeft,
            TiebreakEventHandler.handleWithNarrow(evt)
        );
    }

}
