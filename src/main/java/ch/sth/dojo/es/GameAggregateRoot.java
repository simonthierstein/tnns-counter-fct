package ch.sth.dojo.es;

import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.events.GameErzeugt;
import io.vavr.Tuple2;
import java.util.function.Function;

public interface GameAggregateRoot {

    static GameErzeugt erzeugeGame() {
        return new GameErzeugt();
    }
    static PreInitializedGame empty() {
        return PreInitializedGame.preInitializedGame();
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
