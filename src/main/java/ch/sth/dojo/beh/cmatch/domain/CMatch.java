package ch.sth.dojo.beh.cmatch.domain;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

import ch.sth.dojo.beh.DomainProblem;
import io.vavr.control.Either;
import java.util.function.Function;

public sealed interface CMatch permits LaufendesMatch, AbgeschlossenesMatch {

    Function<AbgeschlossenesMatch, Either<DomainProblem, CMatch>> abgeschlossenToDomainProblem = x -> left(DomainProblem.eventNotValid);

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
                new LaufendesMatch(sscx, gscx)));
    }

    static Either<DomainProblem, CMatch> spielerSatzGewonnen(CMatch state) {
        return apply(state,
            laufendesMatch -> right(laufendesMatch.spielerPunktet()),
            abgeschlossenesMatch -> left(DomainProblem.eventNotValid)
        );
    }
}

