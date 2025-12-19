package ch.sth.dojo.beh.cmatch.domain;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

import ch.sth.dojo.beh.DomainProblem;
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.util.function.Function;

public sealed interface TennisMatch permits LaufendesMatch, AbgeschlossenesMatch {

    Function<AbgeschlossenesMatch, Either<DomainProblem, TennisMatch>> abgeschlossenesMatchToDomainProblem = abgeschlossenesMatch -> left(DomainProblem.eventNotValid);
    Function<LaufendesMatch, Either<DomainProblem, TennisMatch>> laufendesMatchSpielerPunktet = laufendesMatch -> right(laufendesMatch.spielerPunktet());
    Function<LaufendesMatch, Either<DomainProblem, TennisMatch>> laufendesMatchGegnerPunktet = laufendesMatch -> right(laufendesMatch.gegnerPunktet());

    static <T> T apply(TennisMatch target,
                       Function<LaufendesMatch, T> f1,
                       Function<AbgeschlossenesMatch, T> f2
    ) {
        return switch (target) {
            case LaufendesMatch laufendesMatch -> f1.apply(laufendesMatch);
            case AbgeschlossenesMatch abgeschlossenesMatch -> f2.apply(abgeschlossenesMatch);
        };
    }

    static TennisMatch zero() {
        return new LaufendesMatch(SpielerPunkteMatch.zero(), GegnerPunkteMatch.zero());
    }

    static Either<DomainProblem, TennisMatch> of(Integer spielerScore, Integer gegnerScore) {
        var ssc = PunkteMatch.of(spielerScore).map(SpielerPunkteMatch::new);
        var gsc = PunkteMatch.of(gegnerScore).map(GegnerPunkteMatch::new);

        return ssc.flatMap(sscx ->
            gsc.map(gscx ->
                createMatchInstance(sscx, gscx)));
    }

    static TennisMatch createMatchInstance(SpielerPunkteMatch spielerPunkteMatch, GegnerPunkteMatch gegnerPunkteMatch) {
        return Option.when(SpielerPunkteMatch.hasWon.test(spielerPunkteMatch) || GegnerPunkteMatch.hasWon.test(gegnerPunkteMatch), new AbgeschlossenesMatch())
            .map(TennisMatch::narrow)
            .getOrElse(() -> new LaufendesMatch(spielerPunkteMatch, gegnerPunkteMatch));
    }

    static Either<DomainProblem, TennisMatch> spielerSatzGewonnen(TennisMatch state) {
        return apply(state,
            laufendesMatchSpielerPunktet,
            abgeschlossenesMatchToDomainProblem
        );
    }

    static Either<DomainProblem, TennisMatch> spielerMatchGewonnen(TennisMatch state) {
        return apply(state,
            laufendesMatchSpielerPunktet,
            abgeschlossenesMatchToDomainProblem
        )
            .filterOrElse(AbgeschlossenesMatch.class::isInstance, x -> DomainProblem.eventNotValid);
    }

    static Either<DomainProblem, TennisMatch> gegnerMatchGewonnen(TennisMatch state) {
        return apply(state,
            laufendesMatchGegnerPunktet,
            abgeschlossenesMatchToDomainProblem
        )
            .filterOrElse(AbgeschlossenesMatch.class::isInstance, x -> DomainProblem.eventNotValid);
    }

    static Either<DomainProblem, TennisMatch> gegnerSatzGewonnen(TennisMatch state) {
        return apply(state,
            laufendesMatchGegnerPunktet,
            abgeschlossenesMatchToDomainProblem
        );
    }

    static <T extends TennisMatch> TennisMatch narrow(T cMatch) {
        return cMatch;
    }

    static AbgeschlossenesMatch abgeschlossenesMatch() {
        return new AbgeschlossenesMatch();
    }
}

