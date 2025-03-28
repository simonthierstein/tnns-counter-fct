/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cgame.domain;

import ch.sth.dojo.beh.DomainProblem;
import static ch.sth.dojo.beh.PredicateUtils.compose;
import io.vavr.control.Either;
import java.util.function.Predicate;

public record Tiebreak(TiebreakSpielerPunkteBisGame tiebreakSpielerPunkteBisGame, TiebreakGegnerPunkteBisGame tiebreakGegnerPunkteBisGame) implements CGame {

    public static final Predicate<Tiebreak> passIfGegnerOnePunktBisSatz = compose(TiebreakGegnerPunkteBisGame.eq1, Tiebreak::tiebreakGegnerPunkteBisGame);
    public static final Predicate<Tiebreak> passIfSpielerOnePunktBisSatz = compose(TiebreakSpielerPunkteBisGame.eq1, Tiebreak::tiebreakSpielerPunkteBisGame);
    public static final Predicate<Tiebreak> passIfTiebreakNotOver = x -> true;

    public static Either<DomainProblem, Tiebreak> of(Integer spielerPunkteBisGame, Integer gegnerPunkteBisGame) {
        Either<DomainProblem, TiebreakSpielerPunkteBisGame> spieler = TiebreakSpielerPunkteBisGame.of(spielerPunkteBisGame);
        Either<DomainProblem, TiebreakGegnerPunkteBisGame> gegner = TiebreakGegnerPunkteBisGame.of(gegnerPunkteBisGame);

        return spieler.flatMap(validSpieler ->
            gegner.map(validGegner ->
                Tiebreak(validSpieler, validGegner)));

    }

    public static Tiebreak zero() {
        return Tiebreak(TiebreakSpielerPunkteBisGame.zero(), TiebreakGegnerPunkteBisGame.zero());
    }

    private static Tiebreak Tiebreak(TiebreakSpielerPunkteBisGame tiebreakSpielerPunkteBisGame, TiebreakGegnerPunkteBisGame tiebreakGegnerPunkteBisGame) {
        return new Tiebreak(tiebreakSpielerPunkteBisGame, tiebreakGegnerPunkteBisGame);
    }

    public Tiebreak spielerPunktGewonnen() {
        return Tiebreak(tiebreakSpielerPunkteBisGame.decrement(), tiebreakGegnerPunkteBisGame.adaptTo(tiebreakSpielerPunkteBisGame.decrement()));
    }

    public Tiebreak gegnerPunktGewonnen() {
        return Tiebreak(tiebreakSpielerPunkteBisGame.adaptTo(tiebreakGegnerPunkteBisGame.decrement()), tiebreakGegnerPunkteBisGame.decrement());
    }
}
