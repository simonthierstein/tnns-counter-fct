package ch.sth.dojo.es.match;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

import java.util.function.Function;

public sealed interface StandardMatch permits AbgeschlossenesStandardMatchEventHandler, LaufendesStandardMatchEventHandler {

    public static <T> T apply(StandardMatch state,
                              Function<LaufendesStandardMatchEventHandler, T> f1,
                              Function<AbgeschlossenesStandardMatchEventHandler, T> f2) {
        return Match(state).of(
                Case($(instanceOf(LaufendesStandardMatchEventHandler.class)), f1),
                Case($(instanceOf(AbgeschlossenesStandardMatchEventHandler.class)), f2)
        );
    }
}
