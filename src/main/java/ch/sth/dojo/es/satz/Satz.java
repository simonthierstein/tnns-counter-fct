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
import ch.sth.dojo.es.events.GegnerHatGameGewonnen;
import ch.sth.dojo.es.events.SpielerHatGameGewonnen;
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.util.function.Function;

public interface Satz {


    static Either<DomainError, DomainEvent> handleCommand(Satz prev, DomainEvent event) {
        return apply(prev,
                state -> handleLaufenderSatzCmd(state, event),
                state -> handleAbgeschlossenerSatzCmd(state, event)
        );
    }

    static Either<DomainError, DomainEvent> handleLaufenderSatzCmd(LaufenderSatz state, DomainEvent event) {
        return DomainEvent.handleEvent(
                event,
                left(eventToError(state, "handleLaufenderSatzCmd")),
                left(eventToError(state, "handleLaufenderSatzCmd")),
                right(x -> LaufenderSatz.spielerGewinneGameCmd(state, x)),
                right(x -> LaufenderSatz.gegnerGewinneGameCmd(state, x)),
                left(eventToError(state, "handleLaufenderSatzCmd")),
                left(eventToError(state, "handleLaufenderSatzCmd")),
                left(eventToError(state, "handleLaufenderSatzCmd"))
        );
    }

    static Either<DomainError, DomainEvent> handleAbgeschlossenerSatzCmd(AbgeschlossenerSatz state, DomainEvent event) {
        return null;
    }

    static Either<DomainError, Satz> handleEvent(Satz prev, DomainEvent event) {
        return apply(prev,
                state -> handleLaufenderSatz(state, event),
                state -> handleAbgeschlossenerSatz(state, event)
        );
    }

    private static Either<DomainError, Satz> handleAbgeschlossenerSatz(final AbgeschlossenerSatz state, final DomainEvent event) {
        return Either.left(new DomainError.InvalidEventForSatz(state, event));
    }

    private static Either<DomainError, Satz> handleLaufenderSatz(final LaufenderSatz state, final DomainEvent event) {
        return DomainEvent.handleEvent(
                event,
                left(eventToError(state)),
                left(eventToError(state)),
                right(spielerHatGameGewonnen(state)),
                right(gegnerHatGameGewonnen(state)),
                left(eventToError(state)),
                left(eventToError(state)),
                left(eventToError(state))
        );
    }

    static Satz zero() {
        return LaufenderSatz.zero();
    }


    static <T extends Satz> Function<LaufenderSatz, Satz> narrowSatz(Function<LaufenderSatz, T> toNarrow) {
        return toNarrow::apply;
    }


    private static <T> T apply(final Satz prev,
                               Function<LaufenderSatz, T> f1,
                               Function<AbgeschlossenerSatz, T> f2
    ) {
        return Match(prev).of(
                Case($(instanceOf(LaufenderSatz.class)), f1),
                Case($(instanceOf(AbgeschlossenerSatz.class)), f2)
        );
    }

    private static <I extends DomainEvent, L extends DomainError, R> Function<I, Either<L, R>> right(Function<I, R> inputFunction) {
        return i -> Either.<L, I>right(i).map(inputFunction);
    }

    private static <I extends DomainEvent, L extends DomainError, R> Function<I, Either<DomainError, R>> left(Function<I, L> inputFunction) {
        return i -> Either.<I, R>left(i).mapLeft(inputFunction);
    }

    private static <E extends DomainEvent> Function<E, DomainError> eventToError(Satz state, String command) {
        return event -> new DomainError.InvalidCommandForSatz(state, command);
    }

    private static <E extends DomainEvent> Function<E, DomainError> eventToError(Satz state) {
        return event -> new DomainError.InvalidEventForSatz(state, event);
    }

    static Function<SpielerHatGameGewonnen, Satz> spielerHatGameGewonnen(LaufenderSatz prev) {
        return evt -> Option.of(prev)
                .map(LaufenderSatz::spielerGewinneGame)
                .get();
    }

    static Function<GegnerHatGameGewonnen, Satz> gegnerHatGameGewonnen(LaufenderSatz prev) {
        return evt -> Option.of(prev)
                .map(LaufenderSatz::gegnerGewinneGame)
                .get();
    }
}
