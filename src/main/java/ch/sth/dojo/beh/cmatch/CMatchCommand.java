/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cmatch;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cmatch.domain.MatchScore;
import ch.sth.dojo.beh.cmatch.domain.state.MatchState;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.GegnerSatzGewonnen;
import io.vavr.control.Either;

public class CMatchCommand {

    public static Either<DomainProblem, DomainEvent> spielerGewinntSatz(final MatchState state, DomainEvent event) {
        return MatchScore.spielerGewinntSatz(state);
    }

    public static Either<DomainProblem, DomainEvent> gegnerGewinntSatz(final MatchState state, final GegnerSatzGewonnen event) {
        return MatchScore.gegnerGewinntSatz(state);
    }
}
