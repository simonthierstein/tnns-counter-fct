package ch.sth.dojo.beh.cgame.evt;

import static ch.sth.dojo.beh.DomainProblem.eventNotValid;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;
import static io.vavr.control.Either.left;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cgame.domain.AbgeschlossenesCGame;
import ch.sth.dojo.beh.cgame.domain.CGame;
import ch.sth.dojo.beh.cgame.domain.LaufendesCGame;
import ch.sth.dojo.beh.cgame.domain.Tiebreak;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.GegnerDomainEvent;
import ch.sth.dojo.beh.evt.GegnerGameGewonnen;
import ch.sth.dojo.beh.evt.GegnerPunktGewonnen;
import ch.sth.dojo.beh.evt.GegnerSatzGewonnen;
import ch.sth.dojo.beh.evt.SpielerDomainEvent;
import ch.sth.dojo.beh.evt.SpielerGameGewonnen;
import ch.sth.dojo.beh.evt.SpielerPunktGewonnen;
import ch.sth.dojo.beh.evt.SpielerSatzGewonnen;
import io.vavr.control.Either;
import java.util.function.Function;

public interface CGameEventHandler {

    private static <T> T apply(CGame prev, DomainEvent event,
        Function<LaufendesCGame, T> laufendesCGameTFunction,
        Function<Tiebreak, T> tiebreakTFunction,
        Function<AbgeschlossenesCGame, T> abgeschlossenesCGameTFunction
    ) {
        return Match(event).of(
            Case($(instanceOf(SpielerPunktGewonnen.class)), prev.apply(laufendesCGameTFunction, tiebreakTFunction, abgeschlossenesCGameTFunction)),
            Case($(instanceOf(SpielerGameGewonnen.class)), prev.apply(laufendesCGameTFunction, tiebreakTFunction, abgeschlossenesCGameTFunction)),
            Case($(instanceOf(SpielerSatzGewonnen.class)), prev.apply(laufendesCGameTFunction, tiebreakTFunction, abgeschlossenesCGameTFunction)),
            Case($(instanceOf(GegnerPunktGewonnen.class)), prev.apply(laufendesCGameTFunction, tiebreakTFunction, abgeschlossenesCGameTFunction)),
            Case($(instanceOf(GegnerGameGewonnen.class)), prev.apply(laufendesCGameTFunction, tiebreakTFunction, abgeschlossenesCGameTFunction)),
            Case($(instanceOf(GegnerSatzGewonnen.class)), prev.apply(laufendesCGameTFunction, tiebreakTFunction, abgeschlossenesCGameTFunction))
        );
    }

    static Either<DomainProblem, CGame> handleCGameEvent(CGame state, DomainEvent event) {
        return apply(state,
            event,
            laufendesCGame -> CGameEventHandler.handleEvent(laufendesCGame, event),
            tiebreak -> CGameEventHandler.handleEvent(tiebreak, event),
            abgeschlossenesCGame -> Either.left(eventNotValid));
    }

    static Either<DomainProblem, CGame> handleEvent(Tiebreak state, DomainEvent event) {
        return switch (event) {
            case SpielerDomainEvent evt -> SpielerEventHandler.handleSpielerTBEvent(state, evt);
            case GegnerDomainEvent evt -> GegnerEventHandler.handleGegnerTBEvent(state, evt);
            default -> left(eventNotValid);
        };
    }

    static Either<DomainProblem, CGame> handleEvent(LaufendesCGame state, DomainEvent event) {
        return switch (event) {
            case SpielerDomainEvent evt -> SpielerEventHandler.handleSpielerEvent(state, evt);
            case GegnerDomainEvent evt -> GegnerEventHandler.handleGegnerEvent(state, evt);
            default -> left(eventNotValid);
        };
    }

}

