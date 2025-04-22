/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cmatch.domain;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cmatch.domain.state.AbgeschlossenesMatchState;
import ch.sth.dojo.beh.cmatch.domain.state.LaufendesMatchState;
import ch.sth.dojo.beh.cmatch.domain.state.MatchState;
import ch.sth.dojo.beh.evt.DomainEvent;
import io.vavr.control.Either;
import java.util.function.Function;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MatchScoreCommand {

    public static final Function<AbgeschlossenesMatchState, Either<DomainProblem, MatchState>> abgeschlossenesMatchToDomainProblem = abgeschlossenesMatch -> left(DomainProblem.eventNotValid);
    public static final Function<LaufendesMatchState, Either<DomainProblem, MatchState>> laufendesMatchSpielerPunktet = laufendesMatch -> right(LaufendesMatch.spielerPunktet(laufendesMatch));
    public static final Function<LaufendesMatchState, Either<DomainProblem, MatchState>> laufendesMatchGegnerPunktet = laufendesMatch -> right(LaufendesMatch.gegnerPunktet(laufendesMatch));
    public static final Function<AbgeschlossenesMatchState, Either<DomainProblem, DomainEvent>> abgeschlossenesMatchToDomainProblemEvt = abgeschlossenesMatch -> left(DomainProblem.eventNotValid);


    public static Either<DomainProblem, DomainEvent> spielerGewinntSatz(MatchState state) {
        return MatchScoreEvent.apply(state,
            laufendesMatch -> right(LaufendesMatch.spielerGewinntSatz(laufendesMatch)),
            abgeschlossenesMatchToDomainProblemEvt);
    }

    public static Either<DomainProblem, DomainEvent> gegnerGewinntSatz(MatchState state) {
        return MatchScoreEvent.apply(state,
            laufendesMatch -> right(LaufendesMatch.gegnerGewinntSatz(laufendesMatch)),
            abgeschlossenesMatchToDomainProblemEvt);
    }
}
