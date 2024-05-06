package ch.sth.dojo.es;

import ch.sth.dojo.es.events.DomainEvent;
import io.vavr.Tuple2;
import java.util.function.Function;

public interface GameAggregateRoot {

    static Function<PreInitializedGame, DomainEvent> erzeugeSpiel() {
        return PreInitializedGame::erzeugeGame;
    }

    static Function<LaufendesGame, DomainEvent> gegnerPunktetFct() {
        return LaufendesGame::gegnerPunktet;
    }

    static Function<LaufendesGame, DomainEvent> spielerPunktetFct() {
        return LaufendesGame::spielerPunktet;
    }

    static Game handleEvent(Game state, DomainEvent event) {
        return Game.handleEvent(state, event);
    }
}
