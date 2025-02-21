package ch.sth.dojo.beh.csatz.domain;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

import ch.sth.dojo.beh.shared.domain.GewinnerVerlierer;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import java.util.function.Function;
import java.util.function.Predicate;

public record LaufenderCSatz(SpielerPunkteSatz spielerPunkteSatz, GegnerPunkteSatz gegnerPunkteSatz) implements CSatz {

    private static final Function<Tuple2<SpielerPunkteSatz, GegnerPunkteSatz>, PunkteBisSatz> spielerPunkteBisSatzTransition = x -> new PunkteBisSatz((6 - x._1.value()));
    private static final Function<Tuple2<SpielerPunkteSatz, GegnerPunkteSatz>, PunkteBisSatz> gegnerPunkteBisSatzTransition = x -> new PunkteBisSatz((6 - x._2.value()));
    public static final Predicate<LaufenderCSatz> passIfSpielerOneGameBisSatz = in ->
        in.punkteBisSatz(spielerPunkteBisSatzTransition).isOne();
    public static final Predicate<LaufenderCSatz> passIfGegnerOneGameBisSatz = in ->
        in.punkteBisSatz(gegnerPunkteBisSatzTransition).isOne();
    private static final Predicate<Tuple2<SpielerPunkteSatz, GegnerPunkteSatz>> standardCondition =
        x -> x._1.value() <= 4 && x._2.value() <= 4
            || x._1.value() == 5 && x._2.value() <= 4
            || x._1.value() <= 4 && x._2.value() == 5
            || x._1.value() == 6 && x._2.value() <= 5
            || x._1.value() <= 5 && x._2.value() == 6;
    private static final Predicate<Tuple2<SpielerPunkteSatz, GegnerPunkteSatz>> sixAll =
        x -> x._1.value() == 6 && x._2.value() == 6;



    public static LaufenderCSatz zero() {
        return new LaufenderCSatz(SpielerPunkteSatz.zero(), GegnerPunkteSatz.zero());
    }

    public CSatz gameGewonnen(final GewinnerVerlierer gewinnerVerlierer) {

        return null;
    }

    private PunkteBisSatz punkteBisSatz(final Function<Tuple2<SpielerPunkteSatz, GegnerPunkteSatz>, PunkteBisSatz> punkteBisSatzTransition) {
        return Match(Tuple.of(spielerPunkteSatz, gegnerPunkteSatz)).of(
            Case($(standardCondition), punkteBisSatzTransition),
            Case($(sixAll), x -> new PunkteBisSatz(1))
        );
    }
}

