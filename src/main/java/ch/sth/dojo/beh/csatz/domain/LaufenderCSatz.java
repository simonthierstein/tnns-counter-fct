package ch.sth.dojo.beh.csatz.domain;

import ch.sth.dojo.beh.shared.domain.Gewinner;
import ch.sth.dojo.beh.shared.domain.GewinnerVerlierer;
import ch.sth.dojo.beh.shared.domain.Verlierer;
import io.vavr.Predicates;
import java.util.function.Predicate;

public record LaufenderCSatz(SpielerPunkteSatz spielerPunkteSatz, GegnerPunkteSatz gegnerPunkteSatz) implements CSatz {

    private static final Predicate<GewinnerVerlierer> passIfGewinnerOneGameBisSatz =
        Predicates.anyOf(
            GewinnerVerlierer.compose(Gewinner.eq5, Verlierer.lte4),
            GewinnerVerlierer.compose(Gewinner.eq6, Verlierer.eq5),
            GewinnerVerlierer.compose(Gewinner.eq6, Verlierer.eq6)
        );


    public static final Predicate<LaufenderCSatz> passIfSpielerOneGameBisSatz = in ->
        passIfGewinnerOneGameBisSatz.test(GewinnerVerlierer.of(new Gewinner(in.spielerPunkteSatz.value()), new Verlierer(in.gegnerPunkteSatz.value())));
    public static final Predicate<LaufenderCSatz> passIfGegnerOneGameBisSatz = in ->
        passIfGewinnerOneGameBisSatz.test(GewinnerVerlierer.of(new Gewinner(in.gegnerPunkteSatz.value()), new Verlierer(in.spielerPunkteSatz.value())));


    public static LaufenderCSatz zero() {
        return new LaufenderCSatz(SpielerPunkteSatz.zero(), GegnerPunkteSatz.zero());
    }


}

