/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cgame;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cgame.domain.CGame;
import ch.sth.dojo.beh.cgame.domain.LaufendesCGame;
import ch.sth.dojo.beh.cgame.domain.Tiebreak;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.GegnerGameGewonnen;
import ch.sth.dojo.beh.evt.GegnerPunktGewonnen;
import ch.sth.dojo.beh.evt.SpielerGameGewonnen;
import ch.sth.dojo.beh.evt.SpielerPunktGewonnen;
import ch.sth.dojo.beh.evt.SpielerSatzGewonnen;
import io.vavr.control.Either;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;
import io.vavr.control.Option;

public class CGameCommand {

    public static Either<DomainProblem, DomainEvent> spielerGewinntPunkt(CGame state) {
        return state.apply(
            laufendesCGame -> right(spielerGewinntPunkt(laufendesCGame)),
            tiebreak -> right(spielerGewinntPunkt(tiebreak)),
            abgeschlossenesCGame -> left(DomainProblem.valueNotValid)
        );
    }

    private static DomainEvent spielerGewinntPunkt(final Tiebreak state) {
        return Option.some(state)
            .filter(Tiebreak.passIfSpielerOnePunktBisSatz)
            .map(x -> spielerPunktGewonnen())
            .getOrElse(spielerSatzGewonnen());
    }

    private static DomainEvent spielerGewinntPunkt(final LaufendesCGame state) {
        return Option.some(state)
            .filter(LaufendesCGame.passIfSpielerOnePunktBisCGame)
            .map(x -> spielerGameGewonnen())
            .getOrElse(spielerPunktGewonnen());
    }

    private static DomainEvent spielerSatzGewonnen() {
        return new SpielerSatzGewonnen();
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
