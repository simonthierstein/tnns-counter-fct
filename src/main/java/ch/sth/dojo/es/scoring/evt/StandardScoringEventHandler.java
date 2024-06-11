package ch.sth.dojo.es.scoring.evt;

import static ch.sth.dojo.es.scoring.CurrentGame.currentGame;
import static ch.sth.dojo.es.scoring.CurrentSatz.currentSatz;
import static ch.sth.dojo.es.scoring.StandardScoring.standardScoring;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.events.GameErzeugt;
import ch.sth.dojo.es.evt.game.GameEventHandler;
import ch.sth.dojo.es.evt.satz.SatzEventHandler;
import ch.sth.dojo.es.game.Game;
import ch.sth.dojo.es.match.StandardMatch;
import ch.sth.dojo.es.match.evt.MatchEventHandler;
import ch.sth.dojo.es.satz.Satz;
import ch.sth.dojo.es.scoring.StandardScoring;
import io.vavr.control.Either;

public interface StandardScoringEventHandler {

    static Either<DomainError, StandardScoring> handleEvent2(StandardScoring state, DomainEvent event) {
        var res = DomainEvent.handleEventF2(event, state,
                StandardScoringEventHandler::handlePunktGewonnen,
                StandardScoringEventHandler::handlePunktGewonnen,
                StandardScoringEventHandler::handleAnyoneHatGameGewonnen,
                StandardScoringEventHandler::handleAnyoneHatGameGewonnen,
                StandardScoringEventHandler::handleGameErzeugt,
                StandardScoringEventHandler::handleAnyoneHatSatzGewonnen,
                StandardScoringEventHandler::handleAnyoneHatSatzGewonnen,
                StandardScoringEventHandler::handleAnyoneHatMatchGewonnen,
                StandardScoringEventHandler::handleAnyoneHatMatchGewonnen
        );


        return res;
    }

    static Either<DomainError, StandardScoring> handleAnyoneHatMatchGewonnen(StandardScoring statex, DomainEvent eventx) {
        final Either<DomainError, StandardMatch> standardMatches = MatchEventHandler.handleEvent(statex.match(), eventx);
        final Either<DomainError, Satz> satzs = SatzEventHandler.handleEvent(statex.currentSatz().current(), eventx);
        final Either<DomainError, Game> games = GameEventHandler.handleEvent().apply(statex.currentGame().current(), eventx);


        return null;
    }

    static Either<DomainError, StandardScoring> handleAnyoneHatSatzGewonnen(StandardScoring statex, DomainEvent eventx) {
        final Either<DomainError, StandardMatch> standardMatches = MatchEventHandler.handleEvent(statex.match(), eventx);
        final Either<DomainError, Satz> satzs = SatzEventHandler.handleEvent(statex.currentSatz().current(), eventx);
        final Either<DomainError, Game> games = GameEventHandler.handleEvent().apply(statex.currentGame().current(), eventx);

        return standardMatches.flatMap(standardMatch ->
                satzs.flatMap(satz ->
                        games.map(game ->
                                StandardScoring.standardScoring(currentGame(game), currentSatz(satz), standardMatch))));
    }

    private static Either<DomainError, StandardScoring> handleGameErzeugt(final StandardScoring statex, GameErzeugt event) {
        return Either.right(statex);
    }

    private static Either<DomainError, StandardScoring> handleAnyoneHatGameGewonnen(final StandardScoring statex, final DomainEvent eventx) {
        final Either<DomainError, Satz> satzs = SatzEventHandler.handleEvent(statex.currentSatz().current(), eventx);
        final Either<DomainError, Game> abgeschlossenesGamex = GameEventHandler.handleEvent().apply(statex.currentGame().current(), eventx);

        return satzs.flatMap(satz ->
                abgeschlossenesGamex.map(abgeschlossenesGame ->
                        standardScoring(currentGame(abgeschlossenesGame), currentSatz(satz), statex.match())));
    }

    private static Either<DomainError, StandardScoring> handlePunktGewonnen(final StandardScoring statex, final DomainEvent eventx) {
        return GameEventHandler.handleEvent().apply(statex.currentGame().current(), eventx)
                .map(game -> standardScoring(currentGame(game), currentSatz(statex.currentSatz().current()),
                        statex.match()));
    }

}


