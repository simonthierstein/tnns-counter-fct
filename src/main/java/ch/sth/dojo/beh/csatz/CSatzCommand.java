/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.csatz;

import static ch.sth.dojo.beh.Condition.condition;
import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.csatz.domain.CSatz;
import ch.sth.dojo.beh.csatz.domain.LaufenderCSatz;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.GegnerGameGewonnen;
import ch.sth.dojo.beh.evt.GegnerSatzGewonnen;
import ch.sth.dojo.beh.evt.SpielerSatzGewonnen;
import io.vavr.control.Either;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

public final class CSatzCommand {

    public static Either<DomainProblem, DomainEvent> gegnerGewinntGame(final CSatz state, final GegnerGameGewonnen evt) {
        return CSatz.apply(state,
            laufenderCSatz -> right(gegnerGewinntGameLaufenderSatz(laufenderCSatz, evt)),
            x -> left(DomainProblem.eventNotValid));
    }

    private static DomainEvent gegnerGewinntGameLaufenderSatz(final LaufenderCSatz state, final GegnerGameGewonnen evt) {
        return condition(state, LaufenderCSatz.passIfGegnerOneGameBisSatz,
            x -> new GegnerSatzGewonnen(),
            x -> evt);
    }

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
