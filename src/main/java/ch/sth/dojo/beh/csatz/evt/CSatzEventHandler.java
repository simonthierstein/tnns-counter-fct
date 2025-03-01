package ch.sth.dojo.beh.csatz.evt;

import static ch.sth.dojo.beh.DomainProblem.eventNotValid;
import static io.vavr.control.Either.left;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.csatz.domain.AbgeschlossenerCSatz;
import ch.sth.dojo.beh.csatz.domain.CSatz;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.GegnerDomainEvent;
import ch.sth.dojo.beh.evt.SpielerDomainEvent;
import io.vavr.control.Either;
import java.util.function.Function;

public interface CSatzEventHandler {

    Function<AbgeschlossenerCSatz, Either<DomainProblem, CSatz>> abgeschlossenerSatzToProblem = abgeschlossenerCSatz -> left(eventNotValid);

    static Either<DomainProblem, CSatz> handleEvent(CSatz state, DomainEvent event) {
        return switch (event) {
            case GegnerDomainEvent evt -> GegnerEventHandler.handleGegnerEvent(state, evt);
            case SpielerDomainEvent evt -> SpielerEventHandler.handleSpielerEvent(state, evt);
        };
    }

}

