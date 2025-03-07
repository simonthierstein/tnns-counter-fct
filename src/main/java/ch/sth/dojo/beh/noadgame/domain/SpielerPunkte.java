package ch.sth.dojo.beh.noadgame.domain;

import ch.sth.dojo.beh.DomainProblem;
import static ch.sth.dojo.beh.noadgame.domain.Punkte.Punkte;
import io.vavr.control.Either;
import java.util.function.Predicate;

record SpielerPunkte(Integer value) {

    static Predicate<SpielerPunkte> passIfOnePunktBisGame = spielerPunkte -> spielerPunkte.value == 1;

    static SpielerPunkte zero() {
        return new SpielerPunkte(4);
    }

    static Either<DomainProblem, SpielerPunkte> SpielerPunkte(Integer value) {
        return Punkte(value, SpielerPunkte::new);
    }

}

