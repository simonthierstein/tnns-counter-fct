/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.satz;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.events.DomainEvent;
import java.util.function.Function;

public interface Satz {

    static Satz zero() {
        return LaufenderSatz.zero();
    }


    static <E extends DomainEvent> Function<E, DomainError> eventToError(Satz state, String command) {
        return event -> new DomainError.InvalidCommandForSatz(state, command);
    }

    static <E extends DomainEvent> Function<E, DomainError> eventToError(Satz state) {
        return event -> new DomainError.InvalidEventForSatz(state, event);
    }

    static <T> T apply(final Satz prev,
                       Function<LaufenderSatz, T> f1,
                       Function<AbgeschlossenerSatz, T> f2) {
        return Match(prev).of(
                Case($(instanceOf(LaufenderSatz.class)), f1),
                Case($(instanceOf(AbgeschlossenerSatz.class)), f2)
        );
    }

    static Satz prepareNext(Satz satz) {
        return apply(satz, laufenderSatz -> laufenderSatz, abgeschlossenerSatz -> LaufenderSatz.zero());
    }
}

