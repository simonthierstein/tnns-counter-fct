package ch.sth.dojo.beh.satz.domain;

import ch.sth.dojo.beh.shared.domain.Gewinner;
import ch.sth.dojo.beh.shared.domain.GewinnerVerlierer;
import ch.sth.dojo.beh.shared.domain.Verlierer;
import io.vavr.Predicates;
import java.util.function.Predicate;

public record LaufenderSatz(SpielerPunkteSatz spielerPunkteSatz, GegnerPunkteSatz gegnerPunkteSatz) implements Satz {

    private static final Predicate<GewinnerVerlierer> passIfGewinnerOneGameBisSatz =
        Predicates.anyOf(
            GewinnerVerlierer.compose(Gewinner.eq5, Verlierer.lte4),
            GewinnerVerlierer.compose(Gewinner.eq6, Verlierer.eq5),
            GewinnerVerlierer.compose(Gewinner.eq6, Verlierer.eq6)
        );


    public static final Predicate<LaufenderSatz> passIfSpielerOneGameBisSatz = in ->
        passIfGewinnerOneGameBisSatz.test(GewinnerVerlierer.of(new Gewinner(in.spielerPunkteSatz.value()), new Verlierer(in.gegnerPunkteSatz.value())));
    public static final Predicate<LaufenderSatz> passIfGegnerOneGameBisSatz = in ->
        passIfGewinnerOneGameBisSatz.test(GewinnerVerlierer.of(new Gewinner(in.gegnerPunkteSatz.value()), new Verlierer(in.spielerPunkteSatz.value())));


    public static LaufenderSatz zero() {
        return new LaufenderSatz(SpielerPunkteSatz.zero(), GegnerPunkteSatz.zero());
    }


}

