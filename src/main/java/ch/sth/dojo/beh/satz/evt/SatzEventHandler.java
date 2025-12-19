package ch.sth.dojo.beh.satz.evt;

import static ch.sth.dojo.beh.DomainProblem.eventNotValid;
import static io.vavr.control.Either.left;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.satz.domain.AbgeschlossenerSatz;
import ch.sth.dojo.beh.satz.domain.Satz;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.GegnerDomainEvent;
import ch.sth.dojo.beh.evt.SpielerDomainEvent;
import io.vavr.control.Either;
import java.util.function.Function;

public interface SatzEventHandler {

    Function<AbgeschlossenerSatz, Either<DomainProblem, Satz>> abgeschlossenerSatzToProblem = abgeschlossenerCSatz -> left(eventNotValid);

    static Either<DomainProblem, Satz> handleEvent(Satz state, DomainEvent event) {
        return switch (event) {
            case GegnerDomainEvent evt -> GegnerEventHandler.handleGegnerEvent(state, evt);
            case SpielerDomainEvent evt -> SpielerEventHandler.handleSpielerEvent(state, evt);
        };
    }

}

