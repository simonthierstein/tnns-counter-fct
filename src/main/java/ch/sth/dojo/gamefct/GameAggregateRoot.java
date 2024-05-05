package ch.sth.dojo.gamefct;

import io.vavr.Tuple2;
import java.util.function.Function;

public interface GameAggregateRoot {

    static LaufendesGame initial() {
        return LaufendesGame.initial();
    }

    static Game spielerPunktet(LaufendesGame prev) {
        return prev.spielerPunktet();
    }

    static Game gegnerPunktet(LaufendesGame prev) {
        return prev.gegnerPunktet();
    }

    Function<LaufendesGame, Tuple2<Integer, Integer>> eval2Integer = LaufendesGame.export2Integer();

    static <T> T eval(final Function<LaufendesGame, T> mapper, LaufendesGame target) {
        return target.eval(mapper);
    }
}
