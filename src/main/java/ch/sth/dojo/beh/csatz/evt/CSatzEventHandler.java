package ch.sth.dojo.beh.csatz.evt;

import static ch.sth.dojo.beh.DomainProblem.eventNotValid;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;
import static io.vavr.control.Either.left;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.csatz.domain.AbgeschlossenerCSatz;
import ch.sth.dojo.beh.csatz.domain.CSatz;
import ch.sth.dojo.beh.csatz.domain.LaufenderCSatz;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.GameGestartet;
import ch.sth.dojo.beh.evt.GegnerDomainEvent;
import ch.sth.dojo.beh.evt.GegnerGameGewonnen;
import ch.sth.dojo.beh.evt.GegnerSatzGewonnen;
import ch.sth.dojo.beh.evt.SpielerDomainEvent;
import ch.sth.dojo.beh.evt.SpielerGameGewonnen;
import ch.sth.dojo.beh.evt.SpielerSatzGewonnen;
import io.vavr.Function2;
import io.vavr.control.Either;
import java.util.function.Function;

public interface CSatzEventHandler {

    private static <T> T apply(final CSatz prev, final DomainEvent event, final Function<LaufenderCSatz, T> laufendFct, final Function<AbgeschlossenerCSatz, T> abgeschlossenFct) {
        switch (event) {
            case GameGestartet gameGestartet -> {
            }
            case GegnerDomainEvent gegnerDomainEvent -> {
            }
            case SpielerDomainEvent spielerDomainEvent -> {
            }
        }

        return Match(event).of(
            Case($(instanceOf(SpielerGameGewonnen.class)), CSatz.apply(prev, laufendFct, abgeschlossenFct)),
            Case($(instanceOf(GegnerGameGewonnen.class)), CSatz.apply(prev, laufendFct, abgeschlossenFct)),
            Case($(instanceOf(SpielerSatzGewonnen.class)), CSatz.apply(prev, laufendFct, abgeschlossenFct)),
            Case($(instanceOf(GegnerSatzGewonnen.class)), CSatz.apply(prev, laufendFct, abgeschlossenFct))
        );
    }

    static Either<DomainProblem, CSatz> handleCSatzEvent(CSatz state, DomainEvent event) {
        return apply(state, event, laufenderCSatz -> CSatzEventHandler.handleEvent(laufenderCSatz, event),
            abgeschlossenerCSatz -> Either.<DomainProblem, CSatz>left(eventNotValid));
    }

    public static Function2<CSatz, DomainEvent, Either<DomainProblem, CSatz>> routeToSatz() {
        return (prev, event) -> CSatz.apply(
            prev,
            laufenderCSatz -> CSatzEventHandler.handleEvent(laufenderCSatz, event),
            abgeschlossenerCSatz -> Either.<DomainProblem, CSatz>left(eventNotValid));
    }

    private static Either<DomainProblem, CSatz> handleEvent(LaufenderCSatz state, DomainEvent event) {
        return switch (event) {
            case SpielerDomainEvent evt -> SpielerEventHandler.handleSpielerEvent(state, evt);
            case GegnerDomainEvent evt -> GegnerEventHandler.handleGegnerEvent(state, evt);
            default -> left(eventNotValid);
        };
    }

}

