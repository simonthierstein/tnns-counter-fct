package ch.sth.dojo.es.cmd;

import static ch.sth.dojo.es.cmd.AbgeschlossenerSatzCommandHandler.handleAbgeschlossenerSatzCmd;
import static ch.sth.dojo.es.cmd.LaufenderSatzCommandHandler.handleLaufenderSatzCmd;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.satz.AbgeschlossenerSatz;
import ch.sth.dojo.es.satz.LaufenderSatz;
import ch.sth.dojo.es.satz.Satz;
import io.vavr.control.Either;
import java.util.function.Function;

public interface SatzCommandHandler {
    static Either<DomainError, DomainEvent> handleCommand(Satz prev, DomainEvent event) {
        return apply(prev,
                state -> handleLaufenderSatzCmd(state, event),
                state -> handleAbgeschlossenerSatzCmd(state, event)
        );
    }

    static <T> T apply(final Satz prev,
                       Function<LaufenderSatz, T> f1,
                       Function<AbgeschlossenerSatz, T> f2) {
        return Match(prev).of(
                Case($(instanceOf(LaufenderSatz.class)), f1),
                Case($(instanceOf(AbgeschlossenerSatz.class)), f2)
        );
    }
}
