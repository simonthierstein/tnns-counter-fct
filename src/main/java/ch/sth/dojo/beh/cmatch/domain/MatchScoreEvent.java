/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cmatch.domain;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cmatch.domain.state.AbgeschlossenesMatchState;
import ch.sth.dojo.beh.cmatch.domain.state.GegnerPunkteMatchState;
import ch.sth.dojo.beh.cmatch.domain.state.LaufendesMatchState;
import ch.sth.dojo.beh.cmatch.domain.state.MatchState;
import ch.sth.dojo.beh.cmatch.domain.state.SpielerPunkteMatchState;
import ch.sth.dojo.beh.evt.DomainEvent;
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.util.function.Function;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MatchScoreEvent {

    public static final Function<AbgeschlossenesMatchState, Either<DomainProblem, MatchState>> abgeschlossenesMatchToDomainProblem = abgeschlossenesMatch -> left(DomainProblem.eventNotValid);
    public static final Function<LaufendesMatchState, Either<DomainProblem, MatchState>> laufendesMatchSpielerPunktet = laufendesMatch -> right(LaufendesMatch.spielerPunktet(laufendesMatch));
    public static final Function<LaufendesMatchState, Either<DomainProblem, MatchState>> laufendesMatchGegnerPunktet = laufendesMatch -> right(LaufendesMatch.gegnerPunktet(laufendesMatch));
    public static final Function<AbgeschlossenesMatchState, Either<DomainProblem, DomainEvent>> abgeschlossenesMatchToDomainProblemEvt = abgeschlossenesMatch -> left(DomainProblem.eventNotValid);

    public static <T> T apply(MatchState target,
        Function<LaufendesMatchState, T> f1,
        Function<AbgeschlossenesMatchState, T> f2
    ) {
        return switch (target) {
            case LaufendesMatchState laufendesMatch -> f1.apply(laufendesMatch);
            case AbgeschlossenesMatchState abgeschlossenesMatch -> f2.apply(abgeschlossenesMatch);
        };
    }

    public static MatchState zero() {
        return new LaufendesMatchState(SpielerPunkteMatch.zero(), GegnerPunkteMatch.zero());
    }

    public static Either<DomainProblem, MatchState> of(Integer spielerScore, Integer gegnerScore) {
        var ssc = PunkteMatch.of(spielerScore).map(SpielerPunkteMatchState::new);
        var gsc = PunkteMatch.of(gegnerScore).map(GegnerPunkteMatchState::new);

        return ssc.flatMap(sscx ->
            gsc.map(gscx ->
                createMatchInstance(sscx, gscx)));
    }

    public static MatchState createMatchInstance(SpielerPunkteMatchState spielerPunkteMatch, GegnerPunkteMatchState gegnerPunkteMatch) {
        return Option.when(SpielerPunkteMatch.hasWon.test(spielerPunkteMatch) || GegnerPunkteMatch.hasWon.test(gegnerPunkteMatch), new AbgeschlossenesMatchState())
            .map(MatchScoreEvent::narrow)
            .getOrElse(() -> new LaufendesMatchState(spielerPunkteMatch, gegnerPunkteMatch));
    }

    public static Either<DomainProblem, MatchState> spielerSatzGewonnen(MatchState state) {
        return apply(state,
            laufendesMatchSpielerPunktet,
            abgeschlossenesMatchToDomainProblem
        );
    }

    public static Either<DomainProblem, MatchState> spielerMatchGewonnen(MatchState state) {
        return apply(state,
            laufendesMatchSpielerPunktet,
            abgeschlossenesMatchToDomainProblem
        )
            .filterOrElse(AbgeschlossenesMatchState.class::isInstance, x -> DomainProblem.eventNotValid);
    }

    public static Either<DomainProblem, MatchState> gegnerMatchGewonnen(MatchState state) {
        return apply(state,
            laufendesMatchGegnerPunktet,
            abgeschlossenesMatchToDomainProblem
        )
            .filterOrElse(AbgeschlossenesMatchState.class::isInstance, x -> DomainProblem.eventNotValid);
    }

    public static Either<DomainProblem, MatchState> gegnerSatzGewonnen(MatchState state) {
        return apply(state,
            laufendesMatchGegnerPunktet,
            abgeschlossenesMatchToDomainProblem
        );
    }

    public static <T extends MatchState> MatchState narrow(T cMatch) {
        return cMatch;
    }

    public static AbgeschlossenesMatchState abgeschlossenesMatch() {
        return new AbgeschlossenesMatchState();
    }

}
