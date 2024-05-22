package ch.sth.dojo.beh.cgame.domain.game;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

import java.util.function.Function;

public sealed interface CGame permits LaufendesCGame, AbgeschlossenesCGame {

    default <T> T apply(
        Function<LaufendesCGame, T> laufendesCGameTFunction,
        Function<AbgeschlossenesCGame, T> abgeschlossenesCGameTFunction) {
        return Match(this).of(
            Case($(instanceOf(LaufendesCGame.class)), laufendesCGameTFunction),
            Case($(instanceOf(AbgeschlossenesCGame.class)), abgeschlossenesCGameTFunction)
        );
    }

}
