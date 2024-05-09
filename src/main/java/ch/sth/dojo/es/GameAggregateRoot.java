package ch.sth.dojo.es;

import ch.sth.dojo.es.commands.DomainCommand;
import ch.sth.dojo.es.events.DomainEvent;
import io.vavr.control.Either;
import java.util.function.Function;

public interface GameAggregateRoot {

    static Function<PreInitializedGame, DomainEvent> erzeugeSpiel() {
        return PreInitializedGame::erzeugeGame;
    }

    static Function<LaufendesGame, Either<DomainError,DomainEvent>> command(final DomainCommand command) {
        return laufendesGame -> laufendesGame.handleCommand(command);
    }

    static Game handleEvent(Game state, DomainEvent event) {
        return Game.handleEvent(state, event);
    }
}
