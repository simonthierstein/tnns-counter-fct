/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.game;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.game.domain.Game;
import ch.sth.dojo.beh.game.domain.LaufendesGame;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.GegnerGameGewonnen;
import ch.sth.dojo.beh.evt.GegnerPunktGewonnen;
import ch.sth.dojo.beh.evt.SpielerGameGewonnen;
import ch.sth.dojo.beh.evt.SpielerPunktGewonnen;
import io.vavr.control.Either;
import io.vavr.control.Option;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

public class GameCommand {

    public static Either<DomainProblem, DomainEvent> gegnerGewinntPunkt(final Game state) {
        return state.apply(
                laufendesCGame -> right(gegnerGewinntPunkt(laufendesCGame)),
                abgeschlossenesCGame -> left(DomainProblem.valueNotValid),
                TiebreakCommand::gegnerGewinntPunkt
        );
    }

    private static DomainEvent gegnerGewinntPunkt(final LaufendesGame state) {
        return Option.some(state)
                .filter(LaufendesGame.passIfGegnerOnePunktBisCGame)
                .fold(
                        GameCommand::gegnerPunktGewonnen,
                        x -> gegnerGameGewonnen());
    }

    public static Either<DomainProblem, DomainEvent> spielerGewinntPunkt(Game state) {
        return state.apply(
                laufendesCGame -> right(spielerGewinntPunkt(laufendesCGame)),
                abgeschlossenesCGame -> left(DomainProblem.valueNotValid),
                TiebreakCommand::spielerGewinntPunkt
        );
    }

    private static DomainEvent spielerGewinntPunkt(final LaufendesGame state) {
        return Option.some(state)
                .filter(LaufendesGame.passIfSpielerOnePunktBisCGame)
                .map(x -> spielerGameGewonnen())
                .getOrElse(spielerPunktGewonnen());
    }

    private static DomainEvent spielerGameGewonnen() {
        return new SpielerGameGewonnen();
    }

    private static DomainEvent spielerPunktGewonnen() {
        return new SpielerPunktGewonnen();
    }

    private static DomainEvent gegnerGameGewonnen() {
        return new GegnerGameGewonnen();
    }

    private static DomainEvent gegnerPunktGewonnen() {
        return new GegnerPunktGewonnen();
    }
}
