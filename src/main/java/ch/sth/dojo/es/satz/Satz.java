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
import java.util.function.Function;

public interface Satz {


    static Either<DomainError, Satz> handleEvent(Satz prev, DomainEvent event) {
        return DomainEvent.handleEvent(
                event,
                left(eventToError(prev)),
                left(eventToError(prev)),
                evt -> spielerHatGameGewonnen(prev, evt),
                evt -> gegnerHatGameGewonnen(prev, evt),
                left(eventToError(prev)),
                left(eventToError(prev)),
                left(eventToError(prev))
        );
    }

    static Either<DomainError, Satz> spielerHatGameGewonnen(Satz prev, SpielerHatGameGewonnen event) {
        return apply(prev,
                laufenderSatz -> Either.right(laufenderSatz.incrementSpieler()),
                abgeschlossenerSatz -> Either.left(new DomainError.InvalidEventForSatz(abgeschlossenerSatz, event))
        );
    }

    static Either<DomainError, Satz> gegnerHatGameGewonnen(Satz prev, GegnerHatGameGewonnen event) {
        return apply(prev,
                laufenderSatz -> Either.right(laufenderSatz.incrementGegner()),
                abgeschlossenerSatz -> Either.left(new DomainError.InvalidEventForSatz(abgeschlossenerSatz, event))
        );
    }

    static Satz zero() {
        return LaufenderSatz.zero();
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

    private static <I extends DomainEvent, L extends DomainError, R extends Satz> Function<I, Either<L, Satz>> right(Function<I, R> inputFunction) {
        return i -> Either.<L, I>right(i).map(inputFunction);
    }

    private static <I extends DomainEvent, L extends DomainError, R extends Satz> Function<I, Either<DomainError, R>> left(Function<I, L> inputFunction) {
        return i -> Either.<I, R>left(i).mapLeft(inputFunction);
    }

    private static <E extends DomainEvent> Function<E, DomainError> eventToError(Satz state) {
        return event -> new DomainError.InvalidEventForSatz(state, event);
    }

}
