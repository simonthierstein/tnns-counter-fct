package ch.sth.dojo.beh.satz.domain;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

import ch.sth.dojo.beh.DomainProblem;
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.util.function.Function;

public interface Satz {

    static <T> T apply(Satz target,
                       Function<LaufenderSatz, T> laufenderCSatzTFunction,
                       Function<AbgeschlossenerSatz, T> abgeschlossenerCSatzTFunction) {
        return Match(target).of(
            Case($(instanceOf(LaufenderSatz.class)), laufenderCSatzTFunction),
            Case($(instanceOf(AbgeschlossenerSatz.class)), abgeschlossenerCSatzTFunction)
        );
    }

    static Satz zero() {
        return LaufenderSatz.zero();
    }

    static Either<DomainProblem, Satz> of(Integer spieler, Integer gegner) {
        var spielerEith = Option.of(spieler).toEither(DomainProblem.nullValueNotValid).flatMap(SpielerPunkteSatz::SpielerPunkteSatz);
        var gegnerEith = Option.of(gegner).toEither(DomainProblem.nullValueNotValid).flatMap(GegnerPunkteSatz::GegnerPunkteSatz);

        return spielerEith.flatMap(spielerx ->
            gegnerEith.map(gegnerx -> new LaufenderSatz(spielerx, gegnerx)));
    }

    static boolean isSixAll(Satz satz) {
        return apply(satz, laufenderCSatz -> laufenderCSatz.spielerPunkteSatz().value() == 6 && laufenderCSatz.gegnerPunkteSatz().value() == 6, x -> false);
    }
}
