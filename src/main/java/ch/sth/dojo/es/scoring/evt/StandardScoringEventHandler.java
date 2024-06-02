package ch.sth.dojo.es.scoring.evt;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.evt.game.GameEventHandler;
import ch.sth.dojo.es.evt.satz.SatzEventHandler;
import ch.sth.dojo.es.game.Game;
import ch.sth.dojo.es.match.StandardMatch;
import ch.sth.dojo.es.match.evt.MatchEventHandler;
import ch.sth.dojo.es.satz.Satz;
import ch.sth.dojo.es.scoring.CurrentGame;
import ch.sth.dojo.es.scoring.CurrentSatz;
import ch.sth.dojo.es.scoring.StandardScoring;
import io.vavr.control.Either;

public interface StandardScoringEventHandler {

    static Either<DomainError, StandardScoring> handleEvent(StandardScoring state, DomainEvent event) {
        return GameEventHandler.handleEvent()
                .apply(state.currentGame().current(), event)
                .flatMap(game -> Game.apply(game,
                        laufendesGame -> Either.right(new StandardScoring(new CurrentGame(laufendesGame), state.currentSatz(), state.match())),
                        abgeschlossenesGame -> SatzEventHandler.handleEvent(state.currentSatz().current(), event)
                                .flatMap(nextSatz -> Satz.apply(nextSatz,
                                        laufenderSatz -> Either.right(new StandardScoring(new CurrentGame(Game.zero()), new CurrentSatz(laufenderSatz),
                                                state.match())),
                                        abgeschlossenerSatz -> MatchEventHandler.handleEvent(state.match(), event)
                                                .flatMap(nextStandardMatch -> StandardMatch.apply(nextStandardMatch,
                                                        laufendesMatch -> Either.right(new StandardScoring(new CurrentGame(Game.zero()),
                                                                new CurrentSatz(Satz.zero()), laufendesMatch)),
                                                        abgeschlossenesMatch -> Either.right(new StandardScoring(new CurrentGame(abgeschlossenesGame),
                                                                new CurrentSatz(abgeschlossenerSatz), abgeschlossenesMatch)))))),
                        preInitializedGame -> Either.right(new StandardScoring(new CurrentGame(Game.zero()), new CurrentSatz(Satz.zero()),
                                StandardMatch.zero()))
                ));
    }

}


