package ch.sth.dojo.beh;

import ch.sth.dojo.beh.cgame.domain.CGame;
import ch.sth.dojo.beh.cgame.domain.Tiebreak;
import ch.sth.dojo.beh.cgame.evt.CGameEventHandler;
import ch.sth.dojo.beh.cmatch.domain.CMatch;
import ch.sth.dojo.beh.cmatch.evt.CMatchEventHandler;
import ch.sth.dojo.beh.csatz.domain.CSatz;
import ch.sth.dojo.beh.csatz.evt.CSatzEventHandler;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.GegnerGameGewonnen;
import ch.sth.dojo.beh.evt.GegnerMatchGewonnen;
import ch.sth.dojo.beh.evt.GegnerPunktGewonnen;
import ch.sth.dojo.beh.evt.GegnerSatzGewonnen;
import ch.sth.dojo.beh.evt.SpielerGameGewonnen;
import ch.sth.dojo.beh.evt.SpielerMatchGewonnen;
import ch.sth.dojo.beh.evt.SpielerPunktGewonnen;
import ch.sth.dojo.beh.evt.SpielerSatzGewonnen;
import io.vavr.Function3;
import io.vavr.Tuple;
import io.vavr.Tuple3;
import io.vavr.control.Either;

public interface RootEventHandler {

    Function3<Either<DomainProblem, CMatch>, Either<DomainProblem, CSatz>, Either<DomainProblem, CGame>, Either<DomainProblem, Tuple3<CMatch, CSatz, CGame>>> tuple3EithersToEitherTuple3 =
        (eith1, eith2, eith3) ->
            eith1.flatMap(match -> eith2.flatMap(satz -> eith3.map(game -> Tuple.of(match, satz, game))));

    static Either<DomainProblem, Tuple3<CMatch, CSatz, CGame>> handleEvent(Tuple3<CMatch, CSatz, CGame> prev, DomainEvent event) {
        return switch (event) {
            case GegnerPunktGewonnen gegnerPunktGewonnen -> gegnerPunktGewonnen(prev, gegnerPunktGewonnen);
            case GegnerGameGewonnen gegnerGameGewonnen -> gegnerGameGewonnen(prev, gegnerGameGewonnen);
            case GegnerSatzGewonnen gegnerSatzGewonnen -> gegnerSatzGewonnen(prev, gegnerSatzGewonnen);
            case GegnerMatchGewonnen gegnerMatchGewonnen -> gegnerMatchGewonnen(prev, gegnerMatchGewonnen);
            case SpielerPunktGewonnen spielerPunktGewonnen -> spielerPunktGewonnenEvt(prev, spielerPunktGewonnen);
            case SpielerGameGewonnen spielerGameGewonnen -> spielerGameGewonnen(prev, spielerGameGewonnen);
            case SpielerSatzGewonnen spielerSatzGewonnen -> spielerSatzGewonnen(prev, spielerSatzGewonnen);
            case SpielerMatchGewonnen spielerMatchGewonnen -> spielerMatchGewonnen(prev, spielerMatchGewonnen);
        };
    }

    private static Either<DomainProblem, Tuple3<CMatch, CSatz, CGame>> spielerMatchGewonnen(Tuple3<CMatch, CSatz, CGame> prev, SpielerMatchGewonnen event) {
        return delegateEventHandling(prev, event);
    }

    private static Either<DomainProblem, Tuple3<CMatch, CSatz, CGame>> spielerSatzGewonnen(Tuple3<CMatch, CSatz, CGame> prev, SpielerSatzGewonnen event) {
        return delegateEventHandling(prev, event);
    }

    private static Either<DomainProblem, Tuple3<CMatch, CSatz, CGame>> spielerPunktGewonnenEvt(Tuple3<CMatch, CSatz, CGame> prev, SpielerPunktGewonnen event) {
        return delegateEventHandling(prev, event);
    }

    private static Either<DomainProblem, Tuple3<CMatch, CSatz, CGame>> spielerGameGewonnen(Tuple3<CMatch, CSatz, CGame> prev, SpielerGameGewonnen event) {
        return delegateEventHandling(prev, event)
            .map(t3 -> t3.apply(RootEventHandler::gameGewonnenTiebreakSwitch));
    }

    private static Either<DomainProblem, Tuple3<CMatch, CSatz, CGame>> gegnerMatchGewonnen(Tuple3<CMatch, CSatz, CGame> prev, GegnerMatchGewonnen event) {
        return delegateEventHandling(prev, event);
    }

    private static Either<DomainProblem, Tuple3<CMatch, CSatz, CGame>> delegateEventHandling(final Tuple3<CMatch, CSatz, CGame> prev, final DomainEvent event) {
        return prev.map1(prevMatch -> CMatchEventHandler.handleEvent(prevMatch, event))
            .map2(prevSatz -> CSatzEventHandler.handleEvent(prevSatz, event))
            .map3(prevGame -> CGameEventHandler.handleEvent(prevGame, event))
            .apply(tuple3EithersToEitherTuple3);
    }

    private static Either<DomainProblem, Tuple3<CMatch, CSatz, CGame>> gegnerSatzGewonnen(final Tuple3<CMatch, CSatz, CGame> prev, final GegnerSatzGewonnen event) {
        return delegateEventHandling(prev, event);
    }

    private static Either<DomainProblem, Tuple3<CMatch, CSatz, CGame>> gegnerPunktGewonnen(final Tuple3<CMatch, CSatz, CGame> prev, final GegnerPunktGewonnen event) {
        return delegateEventHandling(prev, event);
    }

    private static Either<DomainProblem, Tuple3<CMatch, CSatz, CGame>> gegnerGameGewonnen(final Tuple3<CMatch, CSatz, CGame> prev, final GegnerGameGewonnen event) {
        return delegateEventHandling(prev, event)
            .map(next -> next.apply(RootEventHandler::gameGewonnenTiebreakSwitch));
    }

    private static Tuple3<CMatch, CSatz, CGame> gameGewonnenTiebreakSwitch(final CMatch nextMatch, final CSatz nextSatz, final CGame nextGame) {
        return Condition.condition(nextSatz, CSatz::isSixAll,
            satz -> Tuple.of(nextMatch, satz, Tiebreak.zero()),
            satz -> Tuple.of(nextMatch, nextSatz, nextGame));
    }

}

