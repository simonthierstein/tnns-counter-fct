package ch.sth.dojo.beh.noadgame.evt;

import ch.sth.dojo.beh.DomainProblem;
import static ch.sth.dojo.beh.DomainProblem.eventNotValid;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.GegnerDomainEvent;
import ch.sth.dojo.beh.evt.SpielerDomainEvent;
import ch.sth.dojo.beh.noadgame.domain.AbgeschlossenesNoAdGame;
import ch.sth.dojo.beh.noadgame.domain.NoAdGame;
import io.vavr.control.Either;
import static io.vavr.control.Either.left;
import java.util.function.Function;

public interface NoAdGameEventHandler {

    Function<AbgeschlossenesNoAdGame, Either<DomainProblem, NoAdGame>> abgeschlossenToLeft = abgeschlossenesCGame -> left(eventNotValid);

    static Either<DomainProblem, NoAdGame> handleEvent(NoAdGame state, DomainEvent event) {
        return switch (event) {
            case GegnerDomainEvent gegnerDomainEvent -> GegnerEventHandler.handleGegnerEvent(state, gegnerDomainEvent);
            case SpielerDomainEvent spielerDomainEvent -> SpielerEventHandler.handleSpielerEvent(state, spielerDomainEvent);
        };
    }
}

