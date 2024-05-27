package ch.sth.dojo.es.game;

import static ch.sth.dojo.es.game.Punkt.punkt;

import ch.sth.dojo.es.cmd.Pre2LaufendCommandHandler;
import ch.sth.dojo.es.events.GameErzeugt;
import io.vavr.collection.List;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Game {

    static Function<PreInitializedGame, GameErzeugt> erzeugeGame() {
        return Pre2LaufendCommandHandler.erzeugeGame();
    }

    static Predicate<LaufendesGame> passIfSpielerSize4() {
        return prev -> isSize4(prev.punkteSpieler.append(punkt()));
    }

    static Predicate<LaufendesGame> passIfGegnerSize4() {
        return prev -> isSize4(prev.punkteGegner.append(punkt()));
    }

    static boolean isSize4(final List<Punkt> incremented) {
        return incremented.size() == 4;
    }


}
