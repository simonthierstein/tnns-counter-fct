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

    Function<Game, Tuple2<Integer, Integer>> eval2Integer = Game.export2Integer();

    static <T> T eval(final Function<Game, T> mapper, Game target) {
        return mapper.apply(target);
    }
}
