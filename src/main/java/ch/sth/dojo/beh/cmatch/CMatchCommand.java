/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cmatch;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cmatch.domain.CMatch;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.GegnerSatzGewonnen;
import io.vavr.control.Either;

public class CMatchCommand {

    public static Either<DomainProblem, DomainEvent> spielerGewinntSatz(final CMatch state, DomainEvent event) {
        return CMatch.spielerGewinntSatz(state);
    }

    public static Either<DomainProblem, DomainEvent> gegnerGewinntSatz(final CMatch state, final GegnerSatzGewonnen event) {
        return CMatch.gegnerGewinntSatz(state);
    }
}
