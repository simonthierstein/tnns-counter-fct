/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.csatz;

import static ch.sth.dojo.beh.Condition.condition;
import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.csatz.domain.CSatz;
import ch.sth.dojo.beh.csatz.domain.LaufenderCSatz;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.SpielerSatzGewonnen;
import io.vavr.control.Either;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

public final class CSatzCommand {

    public static Either<DomainProblem, DomainEvent> spielerGewinntGame(CSatz state, final DomainEvent evt) {
        return CSatz.apply(state,
            laufenderCSatz -> right(spielerGewinntGameLaufenderSatz(laufenderCSatz, evt)),
            x -> left(DomainProblem.eventNotValid));
    }

    private static DomainEvent spielerGewinntGameLaufenderSatz(final LaufenderCSatz state, final DomainEvent evt) {
        return condition(state, LaufenderCSatz.passIfSpielerOneGameBisSatz,
            x -> new SpielerSatzGewonnen(),
            x -> evt);
    }
}
