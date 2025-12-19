/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.csatz;

import static ch.sth.dojo.beh.Condition.condition;
import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.csatz.domain.Satz;
import ch.sth.dojo.beh.csatz.domain.LaufenderSatz;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.GegnerGameGewonnen;
import ch.sth.dojo.beh.evt.GegnerSatzGewonnen;
import ch.sth.dojo.beh.evt.SpielerSatzGewonnen;
import io.vavr.control.Either;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

public final class SatzCommand {

    public static Either<DomainProblem, DomainEvent> gegnerGewinntGame(final Satz state, final GegnerGameGewonnen evt) {
        return Satz.apply(state,
            laufenderCSatz -> right(gegnerGewinntGameLaufenderSatz(laufenderCSatz, evt)),
            x -> left(DomainProblem.eventNotValid));
    }

    private static DomainEvent gegnerGewinntGameLaufenderSatz(final LaufenderSatz state, final GegnerGameGewonnen evt) {
        return condition(state, LaufenderSatz.passIfGegnerOneGameBisSatz,
            x -> new GegnerSatzGewonnen(),
            x -> evt);
    }

    public static Either<DomainProblem, DomainEvent> spielerGewinntGame(Satz state, final DomainEvent evt) {
        return Satz.apply(state,
            laufenderCSatz -> right(spielerGewinntGameLaufenderSatz(laufenderCSatz, evt)),
            x -> left(DomainProblem.eventNotValid));
    }

    private static DomainEvent spielerGewinntGameLaufenderSatz(final LaufenderSatz state, final DomainEvent evt) {
        return condition(state, LaufenderSatz.passIfSpielerOneGameBisSatz,
            x -> new SpielerSatzGewonnen(),
            x -> evt);
    }
}
