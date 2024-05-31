package ch.sth.dojo.es.match;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

import java.util.function.Function;

public sealed interface StandardMatch permits AbgeschlossenesStandardMatch, LaufendesStandardMatch {

    public static <T> T apply(StandardMatch state,
                              Function<LaufendesStandardMatch, T> f1,
                              Function<AbgeschlossenesStandardMatch, T> f2) {
        return Match(state).of(
                Case($(instanceOf(LaufendesStandardMatch.class)), f1),
                Case($(instanceOf(AbgeschlossenesStandardMatch.class)), f2)
        );
    }

    static <T extends StandardMatch> StandardMatch narrow(T in) {
        return in;
    }
}
