/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.scoring.cmd;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.Identity;
import ch.sth.dojo.es.cmd.game.GameCommandHandler;
import ch.sth.dojo.es.cmd.satz.SatzCommandHandler;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.match.cmd.MatchCommandHandler;
import ch.sth.dojo.es.scoring.StandardScoring;
import io.vavr.control.Either;

public class StandardScoringCommandHandler {

    static Either<DomainError, DomainEvent> spielerGewinnePunkt(StandardScoring state) {
        return Identity.unit(state)
                .map(standardScoring -> standardScoring.currentGame().current())
                .map(GameCommandHandler.handleSpielerPunktet())
                .map(eith -> handleSatz(state, eith))
                .map(eith -> handleMatch(state, eith))
                .eval();
    }

    static Either<DomainError, DomainEvent> gegnerGewinnePunkt(StandardScoring state) {
        return Identity.unit(state)
                .map(standardScoring -> standardScoring.currentGame().current())
                .map(GameCommandHandler.handleGegnerPunktet())
                .map(eith -> handleSatz(state, eith))
                .map(eith -> handleMatch(state, eith))
                .eval();
    }

    private static Either<DomainError, DomainEvent> handleMatch(final StandardScoring state, final Either<DomainError, DomainEvent> eith) {
        return eith.flatMap(event -> DomainEvent.handleEvent(event,
                Either::right,
                Either::right,
                Either::right,
                Either::right,
                Either::right,
                x -> MatchCommandHandler.spielerGewinneSatzCommand(state.match()),
                x -> MatchCommandHandler.gegnerGewinneSatzCommand(state.match()),
                Either::right,
                Either::right
        ));
    }

    private static Either<DomainError, DomainEvent> handleSatz(StandardScoring state, final Either<DomainError, DomainEvent> eith) {
        return eith.flatMap(event -> DomainEvent.handleEvent(event,
                Either::right,
                Either::right,
                x -> SatzCommandHandler.handleCommand(state.currentSatz().current(), x),
                x -> SatzCommandHandler.handleCommand(state.currentSatz().current(), x),
                Either::right,
                Either::right,
                Either::right,
                Either::right,
                Either::right
        ));
    }

}
