/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.noadgame;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.GegnerGameGewonnen;
import ch.sth.dojo.beh.evt.GegnerPunktGewonnen;
import ch.sth.dojo.beh.evt.SpielerGameGewonnen;
import ch.sth.dojo.beh.evt.SpielerPunktGewonnen;
import ch.sth.dojo.beh.noadgame.domain.LaufendesNoAdGame;
import ch.sth.dojo.beh.noadgame.domain.NoAdGame;
import io.vavr.control.Either;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;
import io.vavr.control.Option;

public class NoAdGameCommand {

    public static Either<DomainProblem, DomainEvent> gegnerGewinntPunkt(final NoAdGame state) {
        return state.apply(
            laufendesCGame -> right(gegnerGewinntPunkt(laufendesCGame)),
            abgeschlossenesCGame -> left(DomainProblem.valueNotValid)
        );
    }

    private static DomainEvent gegnerGewinntPunkt(final LaufendesNoAdGame state) {
        return Option.some(state)
            .filter(LaufendesNoAdGame.passIfGegnerOnePunktBisCGame)
            .map(x -> gegnerGameGewonnen())
            .getOrElse(gegnerPunktGewonnen());
    }

    public static Either<DomainProblem, DomainEvent> spielerGewinntPunkt(NoAdGame state) {
        return state.apply(
            laufendesCGame -> right(spielerGewinntPunkt(laufendesCGame)),
            abgeschlossenesCGame -> left(DomainProblem.valueNotValid)
        );
    }

    private static DomainEvent spielerGewinntPunkt(final LaufendesNoAdGame state) {
        return Option.some(state)
            .filter(LaufendesNoAdGame.passIfSpielerOnePunktBisCGame)
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
