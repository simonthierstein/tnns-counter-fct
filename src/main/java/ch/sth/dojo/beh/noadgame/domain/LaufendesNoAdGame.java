package ch.sth.dojo.beh.noadgame.domain;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.shared.domain.Gewinner;
import ch.sth.dojo.beh.shared.domain.GewinnerVerlierer;
import ch.sth.dojo.beh.shared.domain.Verlierer;
import io.vavr.Function2;
import io.vavr.control.Either;
import java.util.function.Function;
import java.util.function.Predicate;

public record LaufendesNoAdGame(SpielerPunkte spielerPunkte, GegnerPunkte gegnerPunkte) implements NoAdGame {

    public static final Function2<Gewinner, Verlierer, NoAdGame> ofVerliererGewinner = (gewinner, verlierer) -> LaufendesNoAdGame(
        new SpielerPunkte(verlierer.value()), new GegnerPunkte(gewinner.value()));
    public static final Function2<Gewinner, Verlierer, NoAdGame> ofGewinnerVerlierer = (gewinner, verlierer) -> LaufendesNoAdGame(
        new SpielerPunkte(gewinner.value()), new GegnerPunkte(verlierer.value()));
    private static final Function<GewinnerVerlierer, GewinnerVerlierer> incrementGewinner =
        gewinnerVerlierer -> GewinnerVerlierer.of(new Gewinner(gewinnerVerlierer.gewinner().value() + 1), gewinnerVerlierer.verlierer());
    public static Predicate<LaufendesNoAdGame> passIfGegnerOnePunktBisCGame = game -> GegnerPunkte.passIfOnePunktBisGame.test(game.gegnerPunkte);
    public static Predicate<LaufendesNoAdGame> passIfSpielerOnePunktBisCGame = game -> SpielerPunkte.passIfOnePunktBisGame.test(game.spielerPunkte);

    public static LaufendesNoAdGame zero() {
        return new LaufendesNoAdGame(SpielerPunkte.zero(), GegnerPunkte.zero());
    }

    static LaufendesNoAdGame LaufendesNoAdGame(final SpielerPunkte spielerPunkte, final GegnerPunkte gegnerPunkte) {
        return new LaufendesNoAdGame(spielerPunkte, gegnerPunkte);
    }

    private static Either<DomainProblem, NoAdGame> punktGewonnen(final NoAdGame state, final Gewinner gewinner, final Verlierer verlierer,
        Function2<Gewinner, Verlierer, NoAdGame> cgameCreator) {
        final Function<GewinnerVerlierer, NoAdGame> tuple2CGameFunction = t2 -> cgameCreator.tupled().apply(t2.tupled());

        return Either.<DomainProblem, GewinnerVerlierer>right(GewinnerVerlierer.of(gewinner, verlierer))
            .map(incrementGewinner)
            .map(tuple2CGameFunction);
    }

    public static Either<DomainProblem, NoAdGame> gegnerPunktGewonnen(final LaufendesNoAdGame state, final Function2<Gewinner, Verlierer, NoAdGame> creator) {
        return punktGewonnen(state, new Gewinner(state.gegnerPunkte().value()), new Verlierer(state.spielerPunkte().value()),
            creator);
    }

    public static Either<DomainProblem, NoAdGame> spielerPunktGewonnen(final LaufendesNoAdGame state, final Function2<Gewinner, Verlierer, NoAdGame> ofGewinnerVerlierer) {
        return LaufendesNoAdGame.punktGewonnen(state, new Gewinner(state.spielerPunkte().value()), new Verlierer(state.gegnerPunkte().value()),
            ofGewinnerVerlierer);
    }
}
