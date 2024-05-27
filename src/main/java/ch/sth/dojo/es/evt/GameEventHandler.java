package ch.sth.dojo.es.evt;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

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
        return (state, event) -> apply(state,
                laufendesGame -> handleLaufendesGame(laufendesGame, event),
                abgeschlossenesGame -> handleAbgeschlossenesGame(abgeschlossenesGame, event),
                preInitializedGame -> handlePreInitializedGame(preInitializedGame, event));
    }

    static <T> T apply(Game game, Function<LaufendesGame, T> laufendesGameTFunction, Function<AbgeschlossenesGame, T> abgeschlossenesGameTFunction,
                       Function<PreInitializedGame, T> preInitializedGameTFunction) {
        return Match(game).of(
                Case($(instanceOf(LaufendesGame.class)), laufendesGameTFunction),
                Case($(instanceOf(AbgeschlossenesGame.class)), abgeschlossenesGameTFunction),
                Case($(instanceOf(PreInitializedGame.class)), preInitializedGameTFunction)
        );
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
                Util.rightGame(Laufend2LaufendEventHandler.shpg(laufendesGame)),
                Util.rightGame(Laufend2LaufendEventHandler.ghpg(laufendesGame)),
                Util.rightGame(Laufend2AbgeschlossenEventHandler.shgg(laufendesGame)),
                Util.rightGame(Laufend2AbgeschlossenEventHandler.ghgg(laufendesGame)),
                Util.leftGame(eventToError(laufendesGame)),
                Util.leftGame(eventToError(laufendesGame)),
                Util.leftGame(eventToError(laufendesGame))
        );
    }

    static <E extends DomainEvent> Function<E, DomainError> eventToError(Game state) {
        return event -> new DomainError.InvalidEventForGame(state, event);
    }
}
