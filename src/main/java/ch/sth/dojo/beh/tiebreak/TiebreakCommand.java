/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.tiebreak;

import static ch.sth.dojo.beh.Condition.condition;
import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.GegnerGameGewonnen;
import ch.sth.dojo.beh.evt.GegnerPunktGewonnen;
import ch.sth.dojo.beh.evt.SpielerGameGewonnen;
import ch.sth.dojo.beh.evt.SpielerPunktGewonnen;
import ch.sth.dojo.beh.tiebreak.domain.Tiebreak;
import io.vavr.control.Either;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;
import io.vavr.control.Option;

public class TiebreakCommand {

    public static Either<DomainProblem, DomainEvent> gegnerGewinntPunkt(final Tiebreak state) {
        return condition(state, Tiebreak.passIfTiebreakNotOver,
            tiebreak -> right(Option.some(state)
                .filter(Tiebreak.passIfGegnerOnePunktBisSatz)
                .map(x -> gegnerGameGewonnen())
                .getOrElse(gegnerPunktGewonnen())),
            tiebreak -> left(DomainProblem.valueNotValid));
    }

    public static Either<DomainProblem, DomainEvent> spielerGewinntPunkt(final Tiebreak state) {
        return right(Option.some(state)
            .filter(Tiebreak.passIfSpielerOnePunktBisSatz)
            .map(x -> spielerGameGewonnen())
            .getOrElse(spielerPunktGewonnen()));
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
