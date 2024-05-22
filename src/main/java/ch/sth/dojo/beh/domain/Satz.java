package ch.sth.dojo.beh.domain;

import static ch.sth.dojo.beh.domain.SatzPunkt.isNotAbgeschlossen;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

import ch.sth.dojo.beh.Condition;
import ch.sth.dojo.beh.DomainProblem;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Either;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Satz {

    Predicate<Tuple2<InputSpielerPunkte, InputGegnerPunkte>> passIfNotAbgeschlossen = t2 ->
        isNotAbgeschlossen(t2._1.value(), t2._2.value());

    static Satz zero() {
        return new LaufenderSatz(SpielerSatzPunktestand.zero(), GegnerSatzPunktestand.zero());
    }

    static Either<DomainProblem, Satz> of(InputSpielerPunkte spielerPunkte, InputGegnerPunkte gegnerPunkte) {
        return of(spielerPunkte.value(), gegnerPunkte.value());
    }

    private static Either<DomainProblem, Satz> of(final SatzPunkt spielerPunkte, final SatzPunkt gegnerPunkte) {
        return Condition.condition(Tuple.of(spielerPunkte, gegnerPunkte),
            t2 -> isNotAbgeschlossen(t2._1, t2._2),
            t2 ->
                Either.right(t2.map(SpielerSatzPunktestand::new, GegnerSatzPunktestand::new)
                    .apply(LaufenderSatz::new)),
            x -> Either.right(new AbgeschlossenerSatz()));
    }

    public static <T> T apply(Satz target,
        Function<LaufenderSatz, T> laufenderSatzTFunction,
        Function<AbgeschlossenerSatz, T> abgeschlossenerSatzTFunction
    ) {
        return Match(target).of(
            Case($(instanceOf(LaufenderSatz.class)), laufenderSatzTFunction),
            Case($(instanceOf(AbgeschlossenerSatz.class)), abgeschlossenerSatzTFunction)
        );
    }

}
