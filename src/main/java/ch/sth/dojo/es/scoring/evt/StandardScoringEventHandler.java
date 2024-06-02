package ch.sth.dojo.es.scoring.evt;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.events.SpielerHatPunktGewonnen;
import ch.sth.dojo.es.evt.game.GameEventHandler;
import ch.sth.dojo.es.evt.satz.SatzEventHandler;
import ch.sth.dojo.es.game.Game;
import ch.sth.dojo.es.match.StandardMatch;
import ch.sth.dojo.es.match.evt.MatchEventHandler;
import ch.sth.dojo.es.satz.Satz;
import ch.sth.dojo.es.scoring.CurrentGame;
import ch.sth.dojo.es.scoring.CurrentSet;
import ch.sth.dojo.es.scoring.StandardScoring;
import io.vavr.control.Either;

public interface StandardScoringEventHandler {

    static StandardScoring handleEvent(StandardScoring state, DomainEvent event) {
        DomainEvent.handleEvent(event,
                evt -> handleSpielerHatPunktGewonnen(state, evt),
                evt -> evt,
                evt -> evt,
                evt -> evt,
                evt -> evt,
                evt -> evt,
                evt -> evt,
                evt -> evt,
                evt -> evt
        )
    }

    static Either<DomainError, StandardScoring> handleSpielerHatPunktGewonnen(StandardScoring state, SpielerHatPunktGewonnen evt) {
        return GameEventHandler.handleEvent().apply(state.currentGame().current(), evt).flatMap(game -> Game.apply(game,
                laufendesGame -> Either.right(new StandardScoring(new CurrentGame(laufendesGame), state.currentSet(), state.match())),
                abgeschlossenesGame -> {
                    final Either<DomainError, Satz> satzs = SatzEventHandler.handleEvent(state.currentSet().current(), evt);
                    final Either<DomainError, StandardMatch> map = satzs.flatMap(satz -> MatchEventHandler.handleEvent(state.match(), evt));
                    final Either<DomainError, StandardMatch> standardMatches = map.flatMap(standardMatch -> MatchEventHandler.handleEvent(standardMatch, evt));
                    return standardMatches.map(standardMatch -> StandardMatch.apply(standardMatch,
                            laufendesStandardMatch -> new StandardScoring(new CurrentGame(Game.zero()), new CurrentSet(satzs.get()), laufendesStandardMatch),
                            abgeschlossenesStandardMatch -> new StandardScoring(new CurrentGame(abgeschlossenesGame), new CurrentSet(satzs.get()),
                                    abgeschlossenesStandardMatch)
                    ));
                },
                preInitializedGame -> Either.right(new StandardScoring(new CurrentGame(Game.zero()), new CurrentSet(Satz.zero()), StandardMatch.zero()))
        ));
    }

}


