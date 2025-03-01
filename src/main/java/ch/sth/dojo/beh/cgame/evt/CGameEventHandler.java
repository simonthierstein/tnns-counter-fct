package ch.sth.dojo.beh.cgame.evt;

import static ch.sth.dojo.beh.DomainProblem.eventNotValid;
import static io.vavr.control.Either.left;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cgame.domain.AbgeschlossenesCGame;
import ch.sth.dojo.beh.cgame.domain.CGame;
import ch.sth.dojo.beh.cgame.domain.Tiebreak;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.GegnerDomainEvent;
import ch.sth.dojo.beh.evt.SpielerDomainEvent;
import io.vavr.control.Either;
import java.util.function.Function;

public interface CGameEventHandler {

    Function<AbgeschlossenesCGame, Either<DomainProblem, CGame>> abgeschlossenToLeft = abgeschlossenesCGame -> left(eventNotValid);
    Function<Tiebreak, Either<DomainProblem, CGame>> tiebreakToLeft = abgeschlossenesCGame -> left(eventNotValid);

    static Either<DomainProblem, CGame> handleEvent(CGame state, DomainEvent event) {
        return switch (event) {
            case GegnerDomainEvent gegnerDomainEvent -> GegnerEventHandler.handleGegnerEvent(state, gegnerDomainEvent);
            case SpielerDomainEvent spielerDomainEvent -> SpielerEventHandler.handleSpielerEvent(state, spielerDomainEvent);
        };
    }
}

