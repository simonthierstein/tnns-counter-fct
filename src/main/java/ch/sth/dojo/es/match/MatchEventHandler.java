package ch.sth.dojo.es.match;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.events.DomainEvent;
import io.vavr.Function2;
import io.vavr.control.Either;

public interface MatchEventHandler {

    static Either<DomainError, StandardMatch> handleEvent(StandardMatch state, DomainEvent event) {
        return StandardMatch.apply(state,
                laufend -> laufend.handleEvent(event),
                abgeschlossen -> abgeschlossen.handleEvent(event));
    }


    static <S extends StandardMatch, E extends DomainEvent> Function2<S, E, DomainError> eventToErrorF2() {
        return (s, e) -> new DomainError.InvalidEventForMatch();
    }

}
