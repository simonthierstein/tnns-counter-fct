package ch.sth.dojo.beh.noadgame.domain;

import ch.sth.dojo.beh.DomainProblem;
import static ch.sth.dojo.beh.noadgame.domain.LaufendesNoAdGame.LaufendesNoAdGame;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.util.function.Function;

public sealed interface NoAdGame permits LaufendesNoAdGame, AbgeschlossenesNoAdGame {

    static NoAdGame zero() {
        return LaufendesNoAdGame.zero();
    }

    static Either<DomainProblem, NoAdGame> of(Integer spieler, Integer gegner) {
        return Option.of(spieler).toEither(DomainProblem.nullValueNotValid).flatMap(SpielerPunkte::SpielerPunkte)
            .flatMap(spielerx ->
                Option.of(gegner).toEither(DomainProblem.nullValueNotValid).flatMap(GegnerPunkte::GegnerPunkte)
                    .map(gegnerx -> LaufendesNoAdGame(spielerx, gegnerx)));
    }

    default <T> T apply(
        Function<LaufendesNoAdGame, T> laufendesCGameTFunction,
        Function<AbgeschlossenesNoAdGame, T> abgeschlossenesNoAdGameTFunction) {
        return Match(this).of(
            Case($(instanceOf(LaufendesNoAdGame.class)), laufendesCGameTFunction),
            Case($(instanceOf(AbgeschlossenesNoAdGame.class)), abgeschlossenesNoAdGameTFunction)
        );
    }

    static <T> T apply(NoAdGame target,
        Function<LaufendesNoAdGame, T> laufendesCGameTFunction,
        Function<AbgeschlossenesNoAdGame, T> abgeschlossenesCGameTFunction) {
        return target.apply(laufendesCGameTFunction, abgeschlossenesCGameTFunction);
    }

}
