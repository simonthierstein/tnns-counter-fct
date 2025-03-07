/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cmatch;

import static ch.sth.dojo.beh.Condition.condition;
import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cmatch.domain.CMatch;
import ch.sth.dojo.beh.cmatch.domain.LaufendesMatch;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.GegnerMatchGewonnen;
import ch.sth.dojo.beh.evt.GegnerSatzGewonnen;
import ch.sth.dojo.beh.evt.SpielerMatchGewonnen;
import io.vavr.control.Either;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

public class CMatchCommand {

    public static Either<DomainProblem, DomainEvent> spielerGewinntSatz(final CMatch state, DomainEvent event) {
        return CMatch.apply(state,
            laufendesMatch -> right(spielerGewinntSatzLaufendesMatch(laufendesMatch, event)),
            abgeschlossenesMatch -> left(DomainProblem.eventNotValid));
    }

    private static DomainEvent spielerGewinntSatzLaufendesMatch(final LaufendesMatch state, final DomainEvent event) {
        return condition(state, LaufendesMatch.passIfSpielerOneSatzBisMatch,
            x -> new SpielerMatchGewonnen(),
            x -> event);
    }

    public static Either<DomainProblem, DomainEvent> gegnerGewinntSatz(final CMatch state, final GegnerSatzGewonnen event) {
        return CMatch.apply(state,
            laufendesMatch -> right(gegnerGewinntSatzLaufendesMatch(laufendesMatch, event)),
            abgeschlossenesMatch -> left(DomainProblem.eventNotValid));

    }

    private static DomainEvent gegnerGewinntSatzLaufendesMatch(final LaufendesMatch state, final DomainEvent event) {
        return condition(state, LaufendesMatch.passIfGegnerOneSatzBisMatch,
            x -> new GegnerMatchGewonnen(),
            x -> event);
    }
}
