package ch.sth.dojo.beh.csatz.domain;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

import ch.sth.dojo.beh.FunctionUtils;
import io.vavr.Predicates;
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
        Predicates.anyOf(FunctionUtils.untuple(SpielerPunkteSatz.lte4, GegnerPunkteSatz.lte4),
            FunctionUtils.untuple(SpielerPunkteSatz.eq5, GegnerPunkteSatz.lte4),
            FunctionUtils.untuple(SpielerPunkteSatz.lte4, GegnerPunkteSatz.eq5),
            FunctionUtils.untuple(SpielerPunkteSatz.eq6, GegnerPunkteSatz.lte5),// TODO sth/23.02.2025 : edge case
            FunctionUtils.untuple(SpielerPunkteSatz.lte5, GegnerPunkteSatz.eq6) // TODO sth/23.02.2025 : edge case
        );
    private static final Predicate<Tuple2<SpielerPunkteSatz, GegnerPunkteSatz>> sixAll =
        FunctionUtils.untuple(SpielerPunkteSatz.eq6, GegnerPunkteSatz.eq6);

    public static LaufenderCSatz zero() {
        return new LaufenderCSatz(SpielerPunkteSatz.zero(), GegnerPunkteSatz.zero());
    }

    private PunkteBisSatz punkteBisSatz(final Function<Tuple2<SpielerPunkteSatz, GegnerPunkteSatz>, PunkteBisSatz> punkteBisSatzTransition) {
        return Match(Tuple.of(spielerPunkteSatz, gegnerPunkteSatz)).of(
            Case($(standardCondition), punkteBisSatzTransition),
            Case($(sixAll), x -> new PunkteBisSatz(1))
        );
    }
}

