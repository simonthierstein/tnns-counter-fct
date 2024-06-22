package ch.sth.dojo.es.game;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

import io.vavr.control.Option;
import java.util.function.Function;

public interface SatzTiebreak extends Game {

    private static <T> T apply(SatzTiebreak target,
                               Function<LaufendesSatzTiebreak, T> f1,
                               Function<AbgeschlossenesSatzTiebreak, T> f2) {
        return Match(target).of(
                Case($(instanceOf(LaufendesSatzTiebreak.class)), f1),
                Case($(instanceOf(AbgeschlossenesSatzTiebreak.class)), f2)
        );
    }


    static Option<SatzTiebreak> incrementSpieler(SatzTiebreak state) {
        return apply(state,
                prev -> Option.some(LaufendesSatzTiebreak.incrementSpieler(prev)),
                prev -> Option.none());
    }

    static Option<SatzTiebreak> incrementGegner(SatzTiebreak state) {
        return apply(state,
                prev -> Option.some(LaufendesSatzTiebreak.incrementGegner(prev)),
                prev -> Option.none());
    }

}

