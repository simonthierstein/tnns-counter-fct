package ch.sth.dojo.gamefct;

import io.vavr.Tuple2;
import java.util.function.Function;

public interface GameAggregateRoot {
    static Game spielerPunktet(Game prev) {
        return prev.spielerPunktet();
    }

    static Game gegnerPunktet(Game prev) {
        return prev.gegnerPunktet();
    }

    static Tuple2<Integer,Integer> eval2Integer(Game game) {
        return game.eval(Game.export2Integer());
    }
}
