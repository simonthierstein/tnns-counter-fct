package ch.sth.dojo.beh.csatz.domain;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

import java.util.function.Function;

public interface CSatz {

    static <T> T apply(CSatz target,
        Function<LaufenderCSatz, T> laufenderCSatzTFunction,
        Function<AbgeschlossenerCSatz, T> abgeschlossenerCSatzTFunction) {
        return Match(target).of(
            Case($(instanceOf(LaufenderCSatz.class)), laufenderCSatzTFunction),
            Case($(instanceOf(AbgeschlossenerCSatz.class)), abgeschlossenerCSatzTFunction)
        );
    }
}
