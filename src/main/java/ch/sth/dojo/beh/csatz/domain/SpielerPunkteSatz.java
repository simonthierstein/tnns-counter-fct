package ch.sth.dojo.beh.csatz.domain;

import static ch.sth.dojo.beh.csatz.domain.PunkteSatz.PunkteSatz;

import ch.sth.dojo.beh.DomainProblem;
import io.vavr.control.Either;
import java.util.function.Predicate;

public record SpielerPunkteSatz(Integer value) {

    public static final Predicate<SpielerPunkteSatz> lte4 = in -> in.value <= 4;
    public static final Predicate<SpielerPunkteSatz> lte5 = in -> in.value <= 5;
    public static final Predicate<SpielerPunkteSatz> eq5 = in -> in.value == 5;
    public static final Predicate<SpielerPunkteSatz> eq6 = in -> in.value == 6;

    static SpielerPunkteSatz zero() {
        return new SpielerPunkteSatz(0);
    }

    boolean isOne() {
        return value == 1;
    }

    static Either<DomainProblem, SpielerPunkteSatz> SpielerPunkteSatz(Integer punkte) {
        return PunkteSatz(punkte, SpielerPunkteSatz::new);
    }
}

