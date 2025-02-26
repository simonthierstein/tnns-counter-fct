package ch.sth.dojo.beh.cgame.domain;

import static ch.sth.dojo.beh.cgame.domain.LaufendesCGame.LaufendesCGame;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

import ch.sth.dojo.beh.DomainProblem;
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.util.function.Function;

public sealed interface CGame permits LaufendesCGame, AbgeschlossenesCGame, Tiebreak {

    static CGame zero() {
        return LaufendesCGame.zero();
    }

    static Either<DomainProblem, CGame> of(Integer spieler, Integer gegner) {
        return Option.of(spieler).toEither(DomainProblem.nullValueNotValid).flatMap(SpielerPunkteBisGame::SpielerPunkteBisGame)
            .flatMap(spielerx ->
                Option.of(gegner).toEither(DomainProblem.nullValueNotValid).flatMap(GegnerPunkteBisGame::GegnerPunkteBisGame)
                    .map(gegnerx -> LaufendesCGame(spielerx, gegnerx)));
    }

    default <T> T apply(
        Function<LaufendesCGame, T> laufendesCGameTFunction,
        Function<Tiebreak, T> tiebreakTFunction,
        Function<AbgeschlossenesCGame, T> abgeschlossenesCGameTFunction) {
        return Match(this).of(
            Case($(instanceOf(LaufendesCGame.class)), laufendesCGameTFunction),
            Case($(instanceOf(Tiebreak.class)), tiebreakTFunction),
            Case($(instanceOf(AbgeschlossenesCGame.class)), abgeschlossenesCGameTFunction)
        );
    }

}
