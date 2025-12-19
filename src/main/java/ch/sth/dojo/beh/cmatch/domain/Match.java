package ch.sth.dojo.beh.cmatch.domain;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

import ch.sth.dojo.beh.DomainProblem;
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.util.function.Function;

public sealed interface Match permits LaufendesMatch, AbgeschlossenesMatch {

    Function<AbgeschlossenesMatch, Either<DomainProblem, Match>> abgeschlossenesMatchToDomainProblem = abgeschlossenesMatch -> left(DomainProblem.eventNotValid);
    Function<LaufendesMatch, Either<DomainProblem, Match>> laufendesMatchSpielerPunktet = laufendesMatch -> right(laufendesMatch.spielerPunktet());
    Function<LaufendesMatch, Either<DomainProblem, Match>> laufendesMatchGegnerPunktet = laufendesMatch -> right(laufendesMatch.gegnerPunktet());

    static <T> T apply(Match target,
                       Function<LaufendesMatch, T> f1,
                       Function<AbgeschlossenesMatch, T> f2
    ) {
        return switch (target) {
            case LaufendesMatch laufendesMatch -> f1.apply(laufendesMatch);
            case AbgeschlossenesMatch abgeschlossenesMatch -> f2.apply(abgeschlossenesMatch);
        };
    }

    static Match zero() {
        return new LaufendesMatch(SpielerPunkteMatch.zero(), GegnerPunkteMatch.zero());
    }

    static Either<DomainProblem, Match> of(Integer spielerScore, Integer gegnerScore) {
        var ssc = PunkteMatch.of(spielerScore).map(SpielerPunkteMatch::new);
        var gsc = PunkteMatch.of(gegnerScore).map(GegnerPunkteMatch::new);

        return ssc.flatMap(sscx ->
            gsc.map(gscx ->
                createMatchInstance(sscx, gscx)));
    }

    static Match createMatchInstance(SpielerPunkteMatch spielerPunkteMatch, GegnerPunkteMatch gegnerPunkteMatch) {
        return Option.when(SpielerPunkteMatch.hasWon.test(spielerPunkteMatch) || GegnerPunkteMatch.hasWon.test(gegnerPunkteMatch), new AbgeschlossenesMatch())
            .map(Match::narrow)
            .getOrElse(() -> new LaufendesMatch(spielerPunkteMatch, gegnerPunkteMatch));
    }

    static Either<DomainProblem, Match> spielerSatzGewonnen(Match state) {
        return apply(state,
            laufendesMatchSpielerPunktet,
            abgeschlossenesMatchToDomainProblem
        );
    }

    static Either<DomainProblem, Match> spielerMatchGewonnen(Match state) {
        return apply(state,
            laufendesMatchSpielerPunktet,
            abgeschlossenesMatchToDomainProblem
        )
            .filterOrElse(AbgeschlossenesMatch.class::isInstance, x -> DomainProblem.eventNotValid);
    }

    static Either<DomainProblem, Match> gegnerMatchGewonnen(Match state) {
        return apply(state,
            laufendesMatchGegnerPunktet,
            abgeschlossenesMatchToDomainProblem
        )
            .filterOrElse(AbgeschlossenesMatch.class::isInstance, x -> DomainProblem.eventNotValid);
    }

    static Either<DomainProblem, Match> gegnerSatzGewonnen(Match state) {
        return apply(state,
            laufendesMatchGegnerPunktet,
            abgeschlossenesMatchToDomainProblem
        );
    }

    static <T extends Match> Match narrow(T cMatch) {
        return cMatch;
    }

    static AbgeschlossenesMatch abgeschlossenesMatch() {
        return new AbgeschlossenesMatch();
    }
}

