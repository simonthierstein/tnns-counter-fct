/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.tiebreak;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.GegnerMatchGewonnen;
import ch.sth.dojo.beh.evt.GegnerPunktGewonnen;
import ch.sth.dojo.beh.evt.GegnerSatzGewonnen;
import ch.sth.dojo.beh.evt.SpielerMatchGewonnen;
import ch.sth.dojo.beh.evt.SpielerPunktGewonnen;
import ch.sth.dojo.beh.evt.SpielerSatzGewonnen;
import ch.sth.dojo.beh.tiebreak.domain.Tiebreak;
import io.vavr.control.Either;
import static io.vavr.control.Either.right;

public class TiebreakEventHandler {

    public static Either<DomainProblem, Tiebreak> handleEvent(final Tiebreak state, final DomainEvent event) {
        return DomainEvent.apply(
            event,
            evt -> Either.left(DomainProblem.eventNotValid),
            x -> handleEvent(state, x),
            x -> handleEvent(state, x),
            x -> handleEvent(state, x),
            evt1 -> Either.left(DomainProblem.eventNotValid),
            x -> handleEvent(state, x),
            x -> handleEvent(state, x),
            x -> handleEvent(state, x)
        );
    }

    private static Either<DomainProblem, Tiebreak> handleEvent(final Tiebreak state, final SpielerMatchGewonnen evt) {
        return right(state.spielerPunktGewonnen());
    }

    private static Either<DomainProblem, Tiebreak> handleEvent(final Tiebreak state, final SpielerPunktGewonnen evt) {
        return right(state.spielerPunktGewonnen());
    }

    private static Either<DomainProblem, Tiebreak> handleEvent(final Tiebreak state, final SpielerSatzGewonnen evt) {
        return right(state.spielerPunktGewonnen());
    }

    private static Either<DomainProblem, Tiebreak> handleEvent(final Tiebreak state, final GegnerMatchGewonnen evt) {
        return right(state.gegnerPunktGewonnen());
    }

    private static Either<DomainProblem, Tiebreak> handleEvent(final Tiebreak state, final GegnerPunktGewonnen evt) {
        return right(state.gegnerPunktGewonnen());
    }

    private static Either<DomainProblem, Tiebreak> handleEvent(final Tiebreak state, final GegnerSatzGewonnen evt) {
        return right(state.gegnerPunktGewonnen());
    }

}

