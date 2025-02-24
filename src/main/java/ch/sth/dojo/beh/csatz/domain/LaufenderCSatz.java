package ch.sth.dojo.beh.csatz.domain;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

import ch.sth.dojo.beh.FunctionUtils;
import ch.sth.dojo.beh.shared.domain.Gewinner;
import ch.sth.dojo.beh.shared.domain.GewinnerVerlierer;
import ch.sth.dojo.beh.shared.domain.StateTransition;
import ch.sth.dojo.beh.shared.domain.Verlierer;
import io.vavr.Predicates;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import java.util.function.Function;
import java.util.function.Predicate;

public record LaufenderCSatz(SpielerPunkteSatz spielerPunkteSatz, GegnerPunkteSatz gegnerPunkteSatz) implements CSatz {

    private static final Function<Tuple2<SpielerPunkteSatz, GegnerPunkteSatz>, PunkteBisSatz> spielerPunkteBisSatzTransition = x -> new PunkteBisSatz((6 - x._1.value()));
    private static final Function<Tuple2<SpielerPunkteSatz, GegnerPunkteSatz>, PunkteBisSatz> gegnerPunkteBisSatzTransition = x -> new PunkteBisSatz((6 - x._2.value()));
    public static final Predicate<LaufenderCSatz> passIfSpielerOneGameBisSatz = in ->
        in.punkteBisSatz(spielerPunkteBisSatzTransition).isOne();
    public static final Predicate<LaufenderCSatz> passIfGegnerOneGameBisSatz = in ->
        in.punkteBisSatz(gegnerPunkteBisSatzTransition).isOne();

    private static final Predicate<GewinnerVerlierer> standardConditionGV = gv ->
        gv.gewinner()
    private static final Predicate<Tuple2<SpielerPunkteSatz, GegnerPunkteSatz>> standardCondition =
        FunctionUtils.untuple(SpielerPunkteSatz.lte4, GegnerPunkteSatz.lte4);
    private static final Predicate<Tuple2<SpielerPunkteSatz, GegnerPunkteSatz>> spieler5Games =
        Predicates.anyOf(
            FunctionUtils.untuple(SpielerPunkteSatz.eq5, GegnerPunkteSatz.lte4),
            FunctionUtils.untuple(SpielerPunkteSatz.eq6, GegnerPunkteSatz.lte5)
        );
    private static final Predicate<Tuple2<SpielerPunkteSatz, GegnerPunkteSatz>> gegner5Games =
        Predicates.anyOf(
            FunctionUtils.untuple(SpielerPunkteSatz.lte4, GegnerPunkteSatz.eq5),
            FunctionUtils.untuple(SpielerPunkteSatz.lte5, GegnerPunkteSatz.eq6)
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

    private static final Function<GewinnerVerlierer, GewinnerVerlierer> spieler5GamesTransition = gewinnerVerlierer ->
        GewinnerVerlierer.of(new Gewinner(1), new Verlierer(7 - gewinnerVerlierer.verlierer().value()));
    static List<StateTransition> stateTransitions = List.of(
        new StateTransition(spieler5Games, spieler5GamesTransition),
        new StateTransition(gegner5Games, gegner5GamesTransition),
        new StateTransition(sixAll, sixAllTransition),
        new StateTransition(standardCondition, standardTransition)
    );

}

