package ch.sth.dojo.beh.csatz.domain;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

import ch.sth.dojo.beh.shared.domain.GewinnerVerlierer;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import java.util.function.Predicate;

public record LaufenderCSatz(SpielerPunkteSatz spielerPunkteSatz, GegnerPunkteSatz gegnerPunkteSatz) implements CSatz {

    private static final Predicate<Tuple2<SpielerPunkteSatz, GegnerPunkteSatz>> standardCondition =
        x -> x._1.value() <= 4 && x._2.value() <= 4;

    public static LaufenderCSatz zero() {
        return new LaufenderCSatz(SpielerPunkteSatz.zero(), GegnerPunkteSatz.zero());
    }

    public CSatz gameGewonnen(final GewinnerVerlierer gewinnerVerlierer) {

        return null;
    }

    public SpielerPunkteBisSatz spielerPunkteBisSatz() {
        return Match(Tuple.of(spielerPunkteSatz, gegnerPunkteSatz)).of(
            Case($(standardCondition), x -> new SpielerPunkteBisSatz((6 - x._1.value()))),
            Case($(standardCondition), x -> new SpielerPunkteBisSatz((6 - x._1.value())))
        );

    }

    public static final Predicate<LaufenderCSatz> passIfSpielerOneGameBisSatz = in ->
        in.spielerPunkteSatz().isOne();
}

