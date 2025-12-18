package ch.sth.dojo.beh.game.domain;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.shared.domain.Gewinner;
import ch.sth.dojo.beh.shared.domain.GewinnerVerlierer;
import ch.sth.dojo.beh.shared.domain.StateTransition;
import ch.sth.dojo.beh.shared.domain.Verlierer;
import io.vavr.Function2;
import io.vavr.control.Either;
import java.util.function.Function;
import java.util.function.Predicate;

public record LaufendesGame(SpielerPunkteBisGame spielerPunkteBisGame, GegnerPunkteBisGame gegnerPunkteBisGame) implements Game {

    public static Predicate<LaufendesGame> passIfGegnerOnePunktBisCGame = game -> GegnerPunkteBisGame.passIfOnePunktBisGame.test(game.gegnerPunkteBisGame);
    public static Predicate<LaufendesGame> passIfSpielerOnePunktBisCGame = game -> SpielerPunkteBisGame.passIfOnePunktBisGame.test(game.spielerPunkteBisGame);

    public static LaufendesGame zero() {
        return new LaufendesGame(SpielerPunkteBisGame.zero(), GegnerPunkteBisGame.zero());
    }

    public static LaufendesGame LaufendesGame(final SpielerPunkteBisGame spielerPunkteBisGame, final GegnerPunkteBisGame gegnerPunkteBisGame) {
        return new LaufendesGame(spielerPunkteBisGame, gegnerPunkteBisGame);
    }

    public static Either<DomainProblem, Game> punktGewonnen(final Game state, final Gewinner gewinner, final Verlierer verlierer,
                                                            Function2<Gewinner, Verlierer, Game> cgameCreator) {
        final Function<GewinnerVerlierer, Game> tuple2CGameFunction = t2 -> cgameCreator.tupled().apply(t2.tupled());

        return Either.<DomainProblem, GewinnerVerlierer>right(GewinnerVerlierer.of(gewinner, verlierer))
            .flatMap(StateTransition.apply(GameStateTransitions.stateTransitions))
            .map(tuple2CGameFunction);
    }
}
