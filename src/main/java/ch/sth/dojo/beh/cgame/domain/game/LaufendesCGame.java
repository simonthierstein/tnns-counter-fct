package ch.sth.dojo.beh.cgame.domain.game;

import ch.sth.dojo.beh.DomainProblem;
import io.vavr.Function2;
import io.vavr.control.Either;
import java.util.function.Function;
import java.util.function.Predicate;

public record LaufendesCGame(SpielerPunkteBisGame spielerPunkteBisGame, GegnerPunkteBisGame gegnerPunkteBisGame) implements CGame {

    public static Predicate<LaufendesCGame> passIfGegnerOnePunktBisCGame = game -> GegnerPunkteBisGame.passIfOnePunktBisGame.test(game.gegnerPunkteBisGame);
    public static Predicate<LaufendesCGame> passIfSpielerOnePunktBisCGame = game -> SpielerPunkteBisGame.passIfOnePunktBisGame.test(game.spielerPunkteBisGame);

    public static CGame zero() {
        return new LaufendesCGame(SpielerPunkteBisGame.zero(), GegnerPunkteBisGame.zero());
    }

    public static Either<DomainProblem, CGame> punktGewonnen(final CGame state, final Gewinner gewinner, final Verlierer verlierer,
        Function2<Gewinner, Verlierer, CGame> cgameCreator) {
        final Function<GewinnerVerlierer, CGame> tuple2CGameFunction = t2 -> cgameCreator.tupled().apply(t2.tupled());

        return Either.<DomainProblem, GewinnerVerlierer>right(GewinnerVerlierer.of(gewinner, verlierer))
            .flatMap(StateTransition.apply())
            .map(tuple2CGameFunction);
    }
}
