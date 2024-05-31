package ch.sth.dojo.es.match.cmd;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.events.GegnerHatMatchGewonnen;
import ch.sth.dojo.es.events.GegnerHatSatzGewonnen;
import ch.sth.dojo.es.events.SpielerHatMatchGewonnen;
import ch.sth.dojo.es.events.SpielerHatSatzGewonnen;
import ch.sth.dojo.es.match.LaufendesStandardMatch;
import ch.sth.dojo.es.match.StandardMatch;
import io.vavr.control.Either;

public interface MatchCommandHandler {

    static Either<DomainError, DomainEvent> spielerGewinneSatzCommand(LaufendesStandardMatch state) {
        return LaufendesStandardMatch.incrementSpieler(state)
                .map(next -> StandardMatch.apply(next,
                        laufendesStandardMatch -> new SpielerHatSatzGewonnen(),
                        abgeschlossenesStandardMatch -> new SpielerHatMatchGewonnen()));
    }

    static Either<DomainError, DomainEvent> gegnerGewinneSatzCommand(LaufendesStandardMatch state) {
        return LaufendesStandardMatch.incrementGegner(state)
                .map(next -> StandardMatch.apply(next,
                        laufendesStandardMatch -> new GegnerHatSatzGewonnen(),
                        abgeschlossenesStandardMatch -> new GegnerHatMatchGewonnen()));
    }

}
