package ch.sth.dojo.es.game;

import static ch.sth.dojo.es.game.Punkt.punkt;

import io.vavr.collection.List;
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


}
