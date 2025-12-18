package ch.sth.dojo.beh.game.evt;

import ch.sth.dojo.beh.DomainProblem;
import static ch.sth.dojo.beh.DomainProblem.eventNotValid;
import ch.sth.dojo.beh.game.domain.AbgeschlossenesGame;
import ch.sth.dojo.beh.game.domain.CGame;
import ch.sth.dojo.beh.game.domain.Tiebreak;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.GegnerDomainEvent;
import ch.sth.dojo.beh.evt.SpielerDomainEvent;
import io.vavr.control.Either;
import static io.vavr.control.Either.left;
import java.util.function.Function;

public interface GameEventHandler {

    Function<AbgeschlossenesGame, Either<DomainProblem, CGame>> abgeschlossenToLeft = abgeschlossenesGame -> left(eventNotValid);
    Function<Tiebreak, Either<DomainProblem, CGame>> tiebreakToLeft = abgeschlossenesCGame -> left(eventNotValid);

    static Either<DomainProblem, CGame> handleEvent(CGame state, DomainEvent event) {
        return switch (event) {
            case GegnerDomainEvent gegnerDomainEvent -> GegnerEventHandler.handleGegnerEvent(state, gegnerDomainEvent);
            case SpielerDomainEvent spielerDomainEvent -> SpielerEventHandler.handleSpielerEvent(state, spielerDomainEvent);
        };
    }
}

