package ch.sth.dojo.beh.cgame.evt;

import static ch.sth.dojo.beh.DomainProblem.InvalidEvent;
import static io.vavr.control.Either.left;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cgame.domain.CGame;
import ch.sth.dojo.beh.cgame.domain.LaufendesCGame;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.GegnerDomainEvent;
import ch.sth.dojo.beh.evt.SpielerDomainEvent;
import io.vavr.control.Either;

public interface EventHandler {

    static Either<DomainProblem, CGame> handleEvent(LaufendesCGame state, DomainEvent event) {
        return switch (event) {
            case SpielerDomainEvent evt -> SpielerEventHandler.handleSpielerEvent(state, evt);
            case GegnerDomainEvent evt -> GegnerEventHandler.handleGegnerEvent(state, evt);
            default -> left(InvalidEvent);
        };
    }

}

