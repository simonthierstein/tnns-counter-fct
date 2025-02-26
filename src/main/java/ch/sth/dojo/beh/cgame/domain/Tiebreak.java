/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cgame.domain;

import ch.sth.dojo.beh.DomainProblem;
import io.vavr.control.Either;

public record Tiebreak(TiebreakSpielerPunkteBisGame tiebreakSpielerPunkteBisGame, TiebreakGegnerPunkteBisGame tiebreakGegnerPunkteBisGame) implements CGame {

    public static Either<DomainProblem, Tiebreak> of(Integer spielerPunkteBisGame, Integer gegnerPunkteBisGame) {
        Either<DomainProblem, TiebreakSpielerPunkteBisGame> spieler = TiebreakSpielerPunkteBisGame.of(spielerPunkteBisGame);
        Either<DomainProblem, TiebreakGegnerPunkteBisGame> gegner = TiebreakGegnerPunkteBisGame.of(gegnerPunkteBisGame);

        return spieler.flatMap(validSpieler ->
            gegner.map(validGegner ->
                Tiebreak(validSpieler, validGegner)));

    }

    private static Tiebreak Tiebreak(TiebreakSpielerPunkteBisGame tiebreakSpielerPunkteBisGame, TiebreakGegnerPunkteBisGame tiebreakGegnerPunkteBisGame) {
        return new Tiebreak(tiebreakSpielerPunkteBisGame, tiebreakGegnerPunkteBisGame);
    }

    Tiebreak spielerPunktGewonnen() {
        return Tiebreak(tiebreakSpielerPunkteBisGame.decrement(), tiebreakGegnerPunkteBisGame);
    }

    Tiebreak gegnerPunktGewonnen() {
        return Tiebreak(tiebreakSpielerPunkteBisGame, tiebreakGegnerPunkteBisGame.decrement());
    }
}
