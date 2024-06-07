package ch.sth.dojo.es.match.cmd;

import static ch.sth.dojo.es.match.LaufendesStandardMatch.LaufendesStandardMatch;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.match.AbgeschlossenesStandardMatch;
import ch.sth.dojo.es.match.LaufendesStandardMatch;
import ch.sth.dojo.es.match.Punkte;
import ch.sth.dojo.es.match.PunkteGegner;
import ch.sth.dojo.es.match.PunkteSpieler;
import ch.sth.dojo.es.match.StandardMatch;
import ch.sth.dojo.es.match.evt.MatchEventHandler;
import io.vavr.Tuple;
import io.vavr.collection.List;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;

class MatchCommandHandlerTest {

    @Test
    void spielerGewinneSatzCommand() {
        final LaufendesStandardMatch state = LaufendesStandardMatch(new PunkteSpieler(Punkte.punkte(List.empty())),
                new PunkteGegner(Punkte.punkte(List.empty()))).get();
        final Either<DomainError, DomainEvent> domainEvents = MatchCommandHandler.spielerGewinneSatzCommand(state);

        final Either<DomainError, StandardMatch> eith = domainEvents
                .flatMap(event1 -> MatchEventHandler.handleEvent(state, event1))
                .flatMap(state1 -> MatchCommandHandler.spielerGewinneSatzCommand(state1)
                        .map(x -> Tuple.of(state1, x)))
                .flatMap(t2 -> t2.apply(MatchEventHandler::handleEvent));

        assertThat(eith.isRight()).isTrue();
        assertThat(eith.get()).isInstanceOf(AbgeschlossenesStandardMatch.class);
    }

    @Test
    void gegnerGewinneSatzCommand() {
        final LaufendesStandardMatch state = LaufendesStandardMatch(new PunkteSpieler(Punkte.punkte(List.empty())),
                new PunkteGegner(Punkte.punkte(List.empty()))).get();
        final Either<DomainError, DomainEvent> domainEvents = MatchCommandHandler.spielerGewinneSatzCommand(state);

        final Either<DomainError, StandardMatch> eith = domainEvents
                .flatMap(event1 -> MatchEventHandler.handleEvent(state, event1))
                .flatMap(state1 -> MatchCommandHandler.gegnerGewinneSatzCommand(state1)
                        .map(x -> Tuple.of(state1, x)))
                .flatMap(t2 -> t2.apply(MatchEventHandler::handleEvent))
                .flatMap(state2 -> MatchCommandHandler.gegnerGewinneSatzCommand(state2).map(evt -> Tuple.of(state2, evt)))
                .flatMap(t2 -> t2.apply(MatchEventHandler::handleEvent));

        assertThat(eith.isRight()).isTrue();
        assertThat(eith.get()).isInstanceOf(AbgeschlossenesStandardMatch.class);
    }
}