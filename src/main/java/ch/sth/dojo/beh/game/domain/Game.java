package ch.sth.dojo.beh.game.domain;

import ch.sth.dojo.beh.DomainProblem;
import static ch.sth.dojo.beh.game.domain.LaufendesGame.LaufendesGame;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.util.function.Function;

public sealed interface Game permits AbgeschlossenesGame, LaufendesGame, Tiebreak {

    static Game zero() {
        return LaufendesGame.zero();
    }

    static Either<DomainProblem, Game> of(Integer spieler, Integer gegner) {
        return Option.of(spieler).toEither(DomainProblem.nullValueNotValid).flatMap(SpielerPunkteBisGame::SpielerPunkteBisGame)
            .flatMap(spielerx ->
                Option.of(gegner).toEither(DomainProblem.nullValueNotValid).flatMap(GegnerPunkteBisGame::GegnerPunkteBisGame)
                    .map(gegnerx -> LaufendesGame(spielerx, gegnerx)));
    }

    default <T> T apply(
        Function<LaufendesGame, T> laufendesCGameTFunction,
        Function<AbgeschlossenesGame, T> abgeschlossenesCGameTFunction,
        Function<Tiebreak, T> tiebreakTFunction) {
        return Match(this).of(
            Case($(instanceOf(LaufendesGame.class)), laufendesCGameTFunction),
            Case($(instanceOf(AbgeschlossenesGame.class)), abgeschlossenesCGameTFunction),
            Case($(instanceOf(Tiebreak.class)), tiebreakTFunction)
        );
    }

    static <T extends Game> Game narrow(T widened) {
        return widened;
    }
}
