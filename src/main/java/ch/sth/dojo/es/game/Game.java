package ch.sth.dojo.es.game;

import static ch.sth.dojo.es.game.Punkt.punkt;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

import io.vavr.collection.List;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Game {

    static Predicate<LaufendesGame> passIfSpielerSize4() {
        return prev -> isSize4(prev.punkteSpieler.append(punkt()));
    }

    static Predicate<LaufendesGame> passIfGegnerSize4() {
        return prev -> isSize4(prev.punkteGegner.append(punkt()));
    }

    static boolean isSize4(final List<Punkt> incremented) {
        return incremented.size() == 4;
    }


    static <T> T apply(Game game,
                       Function<LaufendesGame, T> laufendesGameTFunction,
                       Function<AbgeschlossenesGame, T> abgeschlossenesGameTFunction,
                       Function<PreInitializedGame, T> preInitializedGameTFunction) {
        return Match(game).of(
                Case($(instanceOf(LaufendesGame.class)), laufendesGameTFunction),
                Case($(instanceOf(AbgeschlossenesGame.class)), abgeschlossenesGameTFunction),
                Case($(instanceOf(PreInitializedGame.class)), preInitializedGameTFunction)
        );
    }

    static Game zero() {
        return LaufendesGame.initial();
    }
}
