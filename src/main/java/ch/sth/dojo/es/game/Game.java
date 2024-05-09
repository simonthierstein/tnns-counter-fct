package ch.sth.dojo.es.game;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

import ch.sth.dojo.es.events.DomainEvent;
import io.vavr.collection.List;
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

    static Game eventHandler(List<DomainEvent> domainEvents) {
        return domainEvents.foldLeft(LaufendesGame.initial(), Game::handleEvent);
    }

    static Game handleEvent(Game state, DomainEvent event) {
        return Game.apply(state,
                laufendesGame -> handleLaufendesGame(laufendesGame, event),
                abgeschlossenesGame -> abgeschlossenesGame,
                preInitializedGame -> preInitializedGame);
    }

    private static Game handleLaufendesGame(final LaufendesGame laufendesGame, final DomainEvent event) {
        return DomainEvent.handleEvent(event,
                LaufendesGame.shpg(laufendesGame),
                LaufendesGame.ghpg(laufendesGame),
                LaufendesGame.shgg(laufendesGame),
                LaufendesGame.ghgg(laufendesGame));
    }

}
