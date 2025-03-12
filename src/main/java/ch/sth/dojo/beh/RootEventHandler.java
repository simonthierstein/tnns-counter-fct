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
import ch.sth.dojo.beh.matchstate.MatchState;
import io.vavr.control.Either;

public interface RootEventHandler {

    static Either<DomainProblem, MatchState> handleEvent(MatchState prev, DomainEvent event) {
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

    private static Either<DomainProblem, MatchState> spielerMatchGewonnen(MatchState prev, SpielerMatchGewonnen event) {
        return delegateEventHandling(prev, event);
    }

    private static Either<DomainProblem, MatchState> spielerSatzGewonnen(MatchState prev, SpielerSatzGewonnen event) {
        return delegateEventHandling(prev, event);
    }

    private static Either<DomainProblem, MatchState> spielerPunktGewonnenEvt(MatchState prev, SpielerPunktGewonnen event) {
        return delegateEventHandling(prev, event);
    }

    private static Either<DomainProblem, MatchState> spielerGameGewonnen(MatchState prev, SpielerGameGewonnen event) {
        return delegateEventHandling(prev, event)
            .map(next -> next.apply(
                gameMatchState -> gameGewonnenTiebreakSwitch(gameMatchState.nextMatch(), gameMatchState.nextSatz(), gameMatchState.nextGame())
            ));
    }

    private static Either<DomainProblem, MatchState> gegnerMatchGewonnen(MatchState prev, GegnerMatchGewonnen event) {
        return delegateEventHandling(prev, event);
    }

    private static Either<DomainProblem, MatchState> delegateEventHandling(final MatchState prev, final DomainEvent event) {
        return Either.narrow(prev.apply(
            prevGameMatchState -> prevGameMatchState.apply(
                prevMatch -> CMatchEventHandler.handleEvent(prevMatch, event),
                prevSatz -> CSatzEventHandler.handleEvent(prevSatz, event),
                prevGame -> CGameEventHandler.handleEvent(prevGame, event))
        ));
    }

    private static Either<DomainProblem, MatchState> gegnerSatzGewonnen(final MatchState prev, final GegnerSatzGewonnen event) {
        return delegateEventHandling(prev, event);
    }

    private static Either<DomainProblem, MatchState> gegnerPunktGewonnen(final MatchState prev, final GegnerPunktGewonnen event) {
        return delegateEventHandling(prev, event);
    }

    private static Either<DomainProblem, MatchState> gegnerGameGewonnen(final MatchState prev, final GegnerGameGewonnen event) {
        return delegateEventHandling(prev, event)
            .map(next -> next.apply(
                gameMatchState -> gameGewonnenTiebreakSwitch(gameMatchState.nextMatch(), gameMatchState.nextSatz(), gameMatchState.nextGame())
            ));
    }

    private static MatchState gameGewonnenTiebreakSwitch(final CMatch nextMatch, final CSatz nextSatz, final CGame nextGame) {
        return Condition.condition(nextSatz, CSatz::isSixAll,
            satz -> MatchState.gameMatchState(nextMatch, satz, Tiebreak.zero()),
            satz -> MatchState.gameMatchState(nextMatch, nextSatz, nextGame));
    }

}

