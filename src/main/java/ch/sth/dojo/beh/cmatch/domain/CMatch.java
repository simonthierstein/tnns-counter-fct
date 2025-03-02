package ch.sth.dojo.beh.cmatch.domain;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

import ch.sth.dojo.beh.DomainProblem;
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.util.function.Function;

public sealed interface CMatch permits LaufendesMatch, AbgeschlossenesMatch {

    Function<AbgeschlossenesMatch, Either<DomainProblem, CMatch>> abgeschlossenesMatchToDomainProblem = abgeschlossenesMatch -> left(DomainProblem.eventNotValid);
    Function<LaufendesMatch, Either<DomainProblem, CMatch>> laufendesMatchSpielerPunktet = laufendesMatch -> right(laufendesMatch.spielerPunktet());
    Function<LaufendesMatch, Either<DomainProblem, CMatch>> laufendesMatchGegnerPunktet = laufendesMatch -> right(laufendesMatch.gegnerPunktet());

    static <T> T apply(CMatch target,
        Function<LaufendesMatch, T> f1,
        Function<AbgeschlossenesMatch, T> f2
    ) {
        return switch (target) {
            case LaufendesMatch laufendesMatch -> f1.apply(laufendesMatch);
            case AbgeschlossenesMatch abgeschlossenesMatch -> f2.apply(abgeschlossenesMatch);
        };
    }

    static CMatch zero() {
        return new LaufendesMatch(SpielerPunkteMatch.zero(), GegnerPunkteMatch.zero());
    }

    static Either<DomainProblem, CMatch> of(Integer spielerScore, Integer gegnerScore) {
        var ssc = PunkteMatch.of(spielerScore).map(SpielerPunkteMatch::new);
        var gsc = PunkteMatch.of(gegnerScore).map(GegnerPunkteMatch::new);

        return ssc.flatMap(sscx ->
            gsc.map(gscx ->
                createMatchInstance(sscx, gscx)));
    }

    static CMatch createMatchInstance(SpielerPunkteMatch spielerPunkteMatch, GegnerPunkteMatch gegnerPunkteMatch) {
        final Option<SpielerPunkteMatch> existIfWon = Option.some(spielerPunkteMatch).filter(SpielerPunkteMatch.hasWon);
        return existIfWon.map(x -> new AbgeschlossenesMatch())
            .map(CMatch::narrow)
            .getOrElse(() -> new LaufendesMatch(spielerPunkteMatch, gegnerPunkteMatch));

    }

    static Either<DomainProblem, CMatch> spielerSatzGewonnen(CMatch state) {
        return apply(state,
            laufendesMatchSpielerPunktet,
            abgeschlossenesMatchToDomainProblem
        );
    }

    static Either<DomainProblem, CMatch> spielerMatchGewonnen(CMatch state) {
        return apply(state,
            laufendesMatchSpielerPunktet,
            abgeschlossenesMatchToDomainProblem
        )
            .filterOrElse(AbgeschlossenesMatch.class::isInstance, x -> DomainProblem.eventNotValid);
    }

    static Either<DomainProblem, CMatch> gegnerMatchGewonnen(CMatch state) {
        return apply(state,
            laufendesMatchGegnerPunktet,
            abgeschlossenesMatchToDomainProblem
        )
            .filterOrElse(AbgeschlossenesMatch.class::isInstance, x -> DomainProblem.eventNotValid);
    }

    static <T extends CMatch> CMatch narrow(T cMatch) {
        return cMatch;
    }
}

