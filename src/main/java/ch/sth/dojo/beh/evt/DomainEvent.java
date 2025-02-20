package ch.sth.dojo.beh.evt;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.domain.Game;
import io.vavr.control.Either;

public sealed interface DomainEvent permits GameGestartet, GegnerDomainEvent, SpielerDomainEvent {

    static Either<DomainProblem, Game> handleEvent(DomainEvent event, Game prev) {
        return Match(event).of(
            Case($(instanceOf(SpielerDomainEvent.class)), evt -> SpielerDomainEvent.handleEvent(evt, prev)),
            Case($(instanceOf(GegnerDomainEvent.class)), evt -> GegnerDomainEvent.handleEvent(evt, prev))
        );
    }

    static <I extends DomainEvent> DomainEvent narrow(I domainEvent) {
        return domainEvent;
    }
}

