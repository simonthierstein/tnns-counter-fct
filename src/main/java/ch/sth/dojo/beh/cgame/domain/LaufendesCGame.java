package ch.sth.dojo.beh.cgame.domain;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.shared.domain.Gewinner;
import ch.sth.dojo.beh.shared.domain.GewinnerVerlierer;
import ch.sth.dojo.beh.shared.domain.StateTransition;
import ch.sth.dojo.beh.shared.domain.Verlierer;
import io.vavr.Function2;
import io.vavr.control.Either;
import java.util.function.Function;
import java.util.function.Predicate;

public record LaufendesCGame(SpielerPunkteBisGame spielerPunkteBisGame, GegnerPunkteBisGame gegnerPunkteBisGame) implements CGame {

    public static Predicate<LaufendesCGame> passIfGegnerOnePunktBisCGame = game -> GegnerPunkteBisGame.passIfOnePunktBisGame.test(game.gegnerPunkteBisGame);
    public static Predicate<LaufendesCGame> passIfSpielerOnePunktBisCGame = game -> SpielerPunkteBisGame.passIfOnePunktBisGame.test(game.spielerPunkteBisGame);

    public static LaufendesCGame zero() {
        return new LaufendesCGame(SpielerPunkteBisGame.zero(), GegnerPunkteBisGame.zero());
    }

    public static LaufendesCGame LaufendesCGame(final SpielerPunkteBisGame spielerPunkteBisGame, final GegnerPunkteBisGame gegnerPunkteBisGame) {
        return new LaufendesCGame(spielerPunkteBisGame, gegnerPunkteBisGame);
    }

    public static Either<DomainProblem, CGame> punktGewonnen(final CGame state, final Gewinner gewinner, final Verlierer verlierer,
        Function2<Gewinner, Verlierer, CGame> cgameCreator) {
        final Function<GewinnerVerlierer, CGame> tuple2CGameFunction = t2 -> cgameCreator.tupled().apply(t2.tupled());

        return Either.<DomainProblem, GewinnerVerlierer>right(GewinnerVerlierer.of(gewinner, verlierer))
            .flatMap(StateTransition.apply(CGameStateTransitions.stateTransitions))
            .map(tuple2CGameFunction);
    }
}
