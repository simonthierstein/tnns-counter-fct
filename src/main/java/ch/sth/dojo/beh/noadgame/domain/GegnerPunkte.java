package ch.sth.dojo.beh.noadgame.domain;

import ch.sth.dojo.beh.DomainProblem;
import static ch.sth.dojo.beh.PredicateUtils.compose;
import static ch.sth.dojo.beh.PredicateUtils.eq;
import static ch.sth.dojo.beh.noadgame.domain.Punkte.Punkte;
import io.vavr.control.Either;
import java.util.function.Predicate;

record GegnerPunkte(Integer value) {

    static final Predicate<GegnerPunkte> passIfOnePunktBisGame = compose(eq(3), GegnerPunkte::value);

    static GegnerPunkte zero() {
        return new GegnerPunkte(4);
    }

    static Either<DomainProblem, GegnerPunkte> GegnerPunkte(Integer value) {
        return Punkte(value, GegnerPunkte::new);
    }

}
