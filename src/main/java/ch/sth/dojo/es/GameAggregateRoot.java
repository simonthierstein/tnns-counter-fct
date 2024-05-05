package ch.sth.dojo.es;

import ch.sth.dojo.es.events.DomainEvent;
import io.vavr.Tuple2;
import java.util.function.Function;

public interface GameAggregateRoot {

    static LaufendesGame initial() {
        return LaufendesGame.initial();
    }

    static DomainEvent spielerPunktet(LaufendesGame prev) {
        return prev.spielerPunktet();
    }

    static DomainEvent gegnerPunktet(LaufendesGame prev) {
        return prev.gegnerPunktet();
    }

    static Game handleEvent(Game state, DomainEvent event) {
        return Game.handleEvent(state, event);
    }



    Function<LaufendesGame, Tuple2<Integer, Integer>> eval2Integer = LaufendesGame.export2Integer();

    static <T> T eval(final Function<LaufendesGame, T> mapper, LaufendesGame target) {
        return target.eval(mapper);
    }
}
