/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.satz;

import static ch.sth.dojo.es.satz.AbgeschlossenerSatz.handleAbgeschlossenerSatz;
import static ch.sth.dojo.es.satz.AbgeschlossenerSatz.handleAbgeschlossenerSatzCmd;
import static ch.sth.dojo.es.satz.LaufenderSatz.handleLaufenderSatz;
import static ch.sth.dojo.es.satz.LaufenderSatz.handleLaufenderSatzCmd;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.events.DomainEvent;
import io.vavr.Function2;
import io.vavr.control.Either;
import java.util.function.Function;

public interface Satz {

    // command

    static Either<DomainError, DomainEvent> handleCommand(Satz prev, DomainEvent event) {
        return apply(prev,
                state -> handleLaufenderSatzCmd(state, event),
                state -> handleAbgeschlossenerSatzCmd(state, event)
        );
    }


    // event

    static Either<DomainError, Satz> handleEvent(Satz prev, DomainEvent event) {
        return apply(prev,
                state -> handleLaufenderSatz(state, event),
                state -> handleAbgeschlossenerSatz(state, event)
        );
    }

    static Satz zero() {
        return LaufenderSatz.zero();
    }

    // util

    private static <T> T apply(final Satz prev,
                               Function<LaufenderSatz, T> f1,
                               Function<AbgeschlossenerSatz, T> f2) {
        return Match(prev).of(
                Case($(instanceOf(LaufenderSatz.class)), f1),
                Case($(instanceOf(AbgeschlossenerSatz.class)), f2)
        );
    }

    static <S, E extends DomainEvent, L extends DomainError, R> Function2<S, E, Either<L, R>> rightF2(Function2<S, E, R> inputFunction) {
        return (s, e) -> Either.<L, E>right(e).map(inputFunction.apply(s));
    }

    static <S, E extends DomainEvent, L extends DomainError, R> Function2<S, E, Either<L, R>> leftF2(Function2<S, E, L> inputFunction) {
        return (s, e) -> Either.<E, R>left(e).mapLeft(inputFunction.apply(s));
    }

    static <I extends DomainEvent, L extends DomainError, R> Function<I, Either<L, R>> right(Function<I, R> inputFunction) {
        return i -> Either.<L, I>right(i).map(inputFunction);
    }

    static <I extends DomainEvent, L extends DomainError, R> Function<I, Either<DomainError, R>> left(Function<I, L> inputFunction) {
        return i -> Either.<I, R>left(i).mapLeft(inputFunction);
    }

    static <E extends DomainEvent> Function<E, DomainError> eventToError(Satz state, String command) {
        return event -> new DomainError.InvalidCommandForSatz(state, command);
    }

    static <E extends DomainEvent> Function<E, DomainError> eventToError(Satz state) {
        return event -> new DomainError.InvalidEventForSatz(state, event);
    }

    static <S extends Satz, E extends DomainEvent> Function2<S, E, DomainError> eventToErrorF2() {
        return DomainError.InvalidEventForSatz::new;
    }

}
