package ch.sth.dojo.beh.csatz.domain;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

import ch.sth.dojo.beh.DomainProblem;
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.util.function.Function;

public interface CSatz {

    static <T> T apply(CSatz target,
        Function<LaufenderCSatz, T> laufenderCSatzTFunction,
        Function<AbgeschlossenerCSatz, T> abgeschlossenerCSatzTFunction) {
        return Match(target).of(
            Case($(instanceOf(LaufenderCSatz.class)), laufenderCSatzTFunction),
            Case($(instanceOf(AbgeschlossenerCSatz.class)), abgeschlossenerCSatzTFunction)
        );
    }

    static CSatz zero() {
        return LaufenderCSatz.zero();
    }

    static Either<DomainProblem, CSatz> of(Integer spieler, Integer gegner) {
        var spielerEith = Option.of(spieler).toEither(DomainProblem.nullValueNotValid).flatMap(SpielerPunkteSatz::SpielerPunkteSatz);
        var gegnerEith = Option.of(gegner).toEither(DomainProblem.nullValueNotValid).flatMap(GegnerPunkteSatz::GegnerPunkteSatz);

        return spielerEith.flatMap(spielerx ->
            gegnerEith.map(gegnerx -> new LaufenderCSatz(spielerx, gegnerx)));
    }
}
