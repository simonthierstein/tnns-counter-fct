package ch.sth.dojo.es.game;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.events.DomainEvent;
import io.vavr.control.Either;
import java.util.function.Function;

public interface Game {

    private static <T> T apply(Game game, Function<LaufendesGame, T> laufendesGameTFunction, Function<AbgeschlossenesGame, T> abgeschlossenesGameTFunction,
                               Function<PreInitializedGame, T> preInitializedGameTFunction) {
        return Match(game).of(
                Case($(instanceOf(LaufendesGame.class)), laufendesGameTFunction),
                Case($(instanceOf(AbgeschlossenesGame.class)), abgeschlossenesGameTFunction),
                Case($(instanceOf(PreInitializedGame.class)), preInitializedGameTFunction)
        );
    }

    static Either<DomainError, Game> handleEvent(Game state, DomainEvent event) {
        return Game.apply(state,
                laufendesGame -> handleLaufendesGame(laufendesGame, event),
                abgeschlossenesGame -> handleAbgeschlossenesGame(abgeschlossenesGame, event),
                preInitializedGame -> handlePreInitializedGame(preInitializedGame, event));
    }

    static Either<DomainError, Game> handlePreInitializedGame(PreInitializedGame preInitializedGame, DomainEvent event) {
        return DomainEvent.handleEvent(event,
                left(eventToError(preInitializedGame)),
                left(eventToError(preInitializedGame)),
                left(eventToError(preInitializedGame)),
                left(eventToError(preInitializedGame)),
                right(PreInitializedGame.gameErzeugt())
        );
    }

    static Either<DomainError, Game> handleAbgeschlossenesGame(AbgeschlossenesGame state, DomainEvent event) {
        return Either.left(new DomainError.InvalidEventForState(state, event));
    }

    private static Either<DomainError, Game> handleLaufendesGame(final LaufendesGame laufendesGame, final DomainEvent event) {
        return DomainEvent.handleEvent(event,
                right(LaufendesGame.shpg(laufendesGame)),
                right(LaufendesGame.ghpg(laufendesGame)),
                right(LaufendesGame.shgg(laufendesGame)),
                right(LaufendesGame.ghgg(laufendesGame)),
                left(eventToError(laufendesGame)));
    }

    static <I extends DomainEvent, L extends DomainError, R extends Game> Function<I, Either<L, Game>> right(Function<I, R> inputFunction) {
        return i -> Either.<L, I>right(i).map(inputFunction);
    }
    static <I extends DomainEvent, L extends DomainError, R extends Game> Function<I, Either<DomainError, R>> left(Function<I, L> inputFunction) {
        return i -> Either.<I, R>left(i).mapLeft(inputFunction);
    }

    static <E extends DomainEvent> Function<E, DomainError> eventToError(Game state) {
        return event -> new DomainError.InvalidEventForState(state, event);
    }

}
