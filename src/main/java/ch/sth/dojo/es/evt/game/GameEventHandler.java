package ch.sth.dojo.es.evt.game;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.Util;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.game.AbgeschlossenesGame;
import ch.sth.dojo.es.game.Game;
import ch.sth.dojo.es.game.LaufendesGame;
import ch.sth.dojo.es.game.PreInitializedGame;
import io.vavr.Function2;
import io.vavr.control.Either;
import java.util.function.Function;

public interface GameEventHandler {
    static Function2<Game, DomainEvent, Either<DomainError, Game>> handleEvent() {
        return (state, event) -> Game.apply(state,
                laufendesGame -> handleLaufendesGame(laufendesGame, event),
                abgeschlossenesGame -> handleAbgeschlossenesGame(abgeschlossenesGame, event),
                preInitializedGame -> handlePreInitializedGame(preInitializedGame, event));
    }

    static Either<DomainError, Game> handlePreInitializedGame(PreInitializedGame preInitializedGame, DomainEvent event) {
        return DomainEvent.handleEvent(event,
                Util.left(eventToError(preInitializedGame)),
                Util.left(eventToError(preInitializedGame)),
                Util.left(eventToError(preInitializedGame)),
                Util.left(eventToError(preInitializedGame)),
                Util.right(Pre2LaufendEventHandler.gameErzeugt()),
                Util.left(eventToError(preInitializedGame)),
                Util.left(eventToError(preInitializedGame))
        );
    }

    static Either<DomainError, Game> handleAbgeschlossenesGame(AbgeschlossenesGame state, DomainEvent event) {
        return Either.left(new DomainError.InvalidEventForGame(state, event));
    }

    static Either<DomainError, Game> handleLaufendesGame(final LaufendesGame laufendesGame, final DomainEvent event) {
        return DomainEvent.handleEvent(event,
                rightGame(Laufend2LaufendEventHandler.shpg(laufendesGame)),
                rightGame(Laufend2LaufendEventHandler.ghpg(laufendesGame)),
                rightGame(Laufend2AbgeschlossenEventHandler.shgg(laufendesGame)),
                rightGame(Laufend2AbgeschlossenEventHandler.ghgg(laufendesGame)),
                leftGame(eventToError(laufendesGame)),
                leftGame(eventToError(laufendesGame)),
                leftGame(eventToError(laufendesGame))
        );
    }

    static <E extends DomainEvent> Function<E, DomainError> eventToError(Game state) {
        return event -> new DomainError.InvalidEventForGame(state, event);
    }

    static <I extends DomainEvent, L extends DomainError, R extends Game> Function<I, Either<L, Game>> rightGame(Function<I, R> inputFunction) {
        return i -> Either.<L, I>right(i).map(inputFunction);
    }

    static <I extends DomainEvent, L extends DomainError, R extends Game> Function<I, Either<DomainError, R>> leftGame(Function<I, L> inputFunction) {
        return i -> Either.<I, R>left(i).mapLeft(inputFunction);
    }
}
