package ch.sth.dojo.beh;

import ch.sth.dojo.beh.cgame.domain.CGame;
import ch.sth.dojo.beh.cgame.domain.Tiebreak;
import ch.sth.dojo.beh.cgame.evt.CGameEventHandler;
import ch.sth.dojo.beh.cmatch.domain.state.MatchState;
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
import io.vavr.control.Either;

public interface RootEventHandler {

    static Either<DomainProblem, ch.sth.dojo.beh.matchstate.MatchState> handleEvent(ch.sth.dojo.beh.matchstate.MatchState prev, DomainEvent event) {
        return switch (event) {
            case GegnerPunktGewonnen gegnerPunktGewonnen -> delegateEventHandling(prev, gegnerPunktGewonnen);
            case GegnerGameGewonnen gegnerGameGewonnen -> gegnerGameGewonnen(prev, gegnerGameGewonnen);
            case GegnerSatzGewonnen gegnerSatzGewonnen -> delegateEventHandling(prev, gegnerSatzGewonnen);
            case GegnerMatchGewonnen gegnerMatchGewonnen -> delegateEventHandling(prev, gegnerMatchGewonnen);
            case SpielerPunktGewonnen spielerPunktGewonnen -> delegateEventHandling(prev, spielerPunktGewonnen);
            case SpielerGameGewonnen spielerGameGewonnen -> spielerGameGewonnen(prev, spielerGameGewonnen);
            case SpielerSatzGewonnen spielerSatzGewonnen -> delegateEventHandling(prev, spielerSatzGewonnen);
            case SpielerMatchGewonnen spielerMatchGewonnen -> delegateEventHandling(prev, spielerMatchGewonnen);
        };
    }

    private static Either<DomainProblem, ch.sth.dojo.beh.matchstate.MatchState> spielerGameGewonnen(ch.sth.dojo.beh.matchstate.MatchState prev, SpielerGameGewonnen event) {
        return delegateEventHandling(prev, event)
            .map(next -> next.apply(
                gameMatchState -> gameGewonnenTiebreakSwitch(gameMatchState.nextMatch(), gameMatchState.nextSatz(), gameMatchState.nextGame())
            ));
    }

    private static Either<DomainProblem, ch.sth.dojo.beh.matchstate.MatchState> gegnerGameGewonnen(final ch.sth.dojo.beh.matchstate.MatchState prev, final GegnerGameGewonnen event) {
        return delegateEventHandling(prev, event)
            .map(next -> next.apply(
                gameMatchState -> gameGewonnenTiebreakSwitch(gameMatchState.nextMatch(), gameMatchState.nextSatz(), gameMatchState.nextGame())
            ));
    }

    private static Either<DomainProblem, ch.sth.dojo.beh.matchstate.MatchState> delegateEventHandling(final ch.sth.dojo.beh.matchstate.MatchState prev, final DomainEvent event) {
        return Either.narrow(prev.apply(
            prevGameMatchState -> prevGameMatchState.apply(
                prevMatch -> CMatchEventHandler.handleEvent(prevMatch, event),
                prevSatz -> CSatzEventHandler.handleEvent(prevSatz, event),
                prevGame -> CGameEventHandler.handleEvent(prevGame, event))
        ));
    }

    private static ch.sth.dojo.beh.matchstate.MatchState gameGewonnenTiebreakSwitch(final MatchState nextMatch, final CSatz nextSatz, final CGame nextGame) {
        return Condition.condition(nextSatz, CSatz::isSixAll,
            satz -> ch.sth.dojo.beh.matchstate.MatchState.gameMatchState(nextMatch, satz, Tiebreak.zero()),
            satz -> ch.sth.dojo.beh.matchstate.MatchState.gameMatchState(nextMatch, nextSatz, nextGame));
    }

}

