package ch.sth.dojo.es.match.cmd;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.events.GegnerHatMatchGewonnen;
import ch.sth.dojo.es.events.GegnerHatSatzGewonnen;
import ch.sth.dojo.es.events.SpielerHatMatchGewonnen;
import ch.sth.dojo.es.events.SpielerHatSatzGewonnen;
import ch.sth.dojo.es.match.AbgeschlossenesStandardMatch;
import ch.sth.dojo.es.match.LaufendesStandardMatch;
import ch.sth.dojo.es.match.StandardMatch;
import io.vavr.control.Either;
import java.util.function.Function;

public final class MatchCommandHandler {

    static Either<DomainError, DomainEvent> spielerGewinneSatzCommand(StandardMatch state) {
        return StandardMatch.apply(state,
                spielerGewinneSatz(),
                invalidStateForMatch()
        );
    }

    static Either<DomainError, DomainEvent> gegnerGewinneSatzCommand(StandardMatch state) {
        return StandardMatch.apply(state,
                gegnerGewinneSatz(),
                invalidStateForMatch()
        );
    }

    private static Function<AbgeschlossenesStandardMatch, Either<DomainError, DomainEvent>> invalidStateForMatch() {
        return asm -> Either.left(new DomainError.InvalidStateForMatch());
    }

    private static Function<LaufendesStandardMatch, Either<DomainError, DomainEvent>> spielerGewinneSatz() {
        return lsm -> LaufendesStandardMatch.incrementSpieler(lsm)
                .map(next -> StandardMatch.apply(next,
                        laufendesMatch2SpielerSatzGewonnen,
                        laufendesMatch2SpielerMatchGewonnen));
    }

    private static Function<LaufendesStandardMatch, Either<DomainError, DomainEvent>> gegnerGewinneSatz() {
        return lsm -> LaufendesStandardMatch.incrementGegner(lsm)
                .map(next -> StandardMatch.apply(next,
                        laufendesStandardMatch2GegnerSatzGewonnen,
                        abgeschlossenesStandardMatch2GegnerMatchGewonnen));
    }

    private static final Function<AbgeschlossenesStandardMatch, DomainEvent> abgeschlossenesStandardMatch2GegnerMatchGewonnen =
            abgeschlossenesStandardMatch -> new GegnerHatMatchGewonnen();

    private static final Function<LaufendesStandardMatch, DomainEvent> laufendesStandardMatch2GegnerSatzGewonnen =
            laufendesStandardMatch -> new GegnerHatSatzGewonnen();

    private static final Function<LaufendesStandardMatch, DomainEvent> laufendesMatch2SpielerSatzGewonnen =
            laufendesStandardMatch -> new SpielerHatSatzGewonnen();

    private static final Function<AbgeschlossenesStandardMatch, DomainEvent> laufendesMatch2SpielerMatchGewonnen =
            abgeschlossenesStandardMatch -> new SpielerHatMatchGewonnen();

}
