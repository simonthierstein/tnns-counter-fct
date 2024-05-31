package ch.sth.dojo.es.match.evt;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.match.StandardMatch;
import io.vavr.Function2;
import io.vavr.control.Either;

public interface MatchEventHandler {

    static Either<DomainError, StandardMatch> handleEvent(StandardMatch state, DomainEvent event) {
        return StandardMatch.apply(state,
                laufend -> LaufendesStandardMatchEventHandler.handleEvent(laufend, event),
                abgeschlossen -> AbgeschlossenesStandardMatchEventHandler.handleEvent(abgeschlossen, event));
    }


    static <S extends StandardMatch, E extends DomainEvent> Function2<S, E, DomainError> eventToErrorF2() {
        return (s, e) -> new DomainError.InvalidEventForMatch();
    }

}
