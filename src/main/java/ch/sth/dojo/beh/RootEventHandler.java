package ch.sth.dojo.beh;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

import ch.sth.dojo.beh.cgame.domain.CGame;
import ch.sth.dojo.beh.cgame.evt.CGameEventHandler;
import ch.sth.dojo.beh.csatz.domain.CSatz;
import ch.sth.dojo.beh.csatz.evt.CSatzEventHandler;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.GameGestartet;
import ch.sth.dojo.beh.evt.GegnerGameGewonnen;
import ch.sth.dojo.beh.evt.GegnerPunktGewonnen;
import ch.sth.dojo.beh.evt.GegnerSatzGewonnen;
import ch.sth.dojo.beh.evt.SpielerGameGewonnen;
import ch.sth.dojo.beh.evt.SpielerPunktGewonnen;
import ch.sth.dojo.beh.evt.SpielerSatzGewonnen;
import io.vavr.Function2;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Either;

public interface RootEventHandler {

    static Either<DomainProblem, Tuple2<CSatz, CGame>> handleEvent(Tuple2<CSatz, CGame> prev, DomainEvent event) {
        return switch (event) {
            case GameGestartet gameGestartet -> Either.right(prev);
            case GegnerPunktGewonnen gegnerPunktGewonnen -> gegnerPunktGewonnen(prev, gegnerPunktGewonnen);
            case GegnerGameGewonnen gegnerGameGewonnen -> gegnerGameGewonnen(prev, gegnerGameGewonnen);
            case GegnerSatzGewonnen gegnerSatzGewonnen -> gegnerSatzGewonnen(prev, gegnerSatzGewonnen);
            case SpielerPunktGewonnen spielerPunktGewonnen -> spielerPunktGewonnenEvt(prev, spielerPunktGewonnen);
            case SpielerGameGewonnen spielerGameGewonnen -> spielerGameGewonnenf(prev, spielerGameGewonnen);
            case SpielerSatzGewonnen spielerSatzGewonnen -> spielerSatzGewonnenEvt(prev, spielerSatzGewonnen);

        };
    }

    Function2<Tuple2<CSatz, CGame>, CSatz, Tuple2<CSatz, CGame>> replaceNextSatz = (prev, nextSatz) -> prev.map1(x -> nextSatz);
    Function2<Tuple2<CSatz, CGame>, CGame, Tuple2<CSatz, CGame>> replaceNextGame = (prev, nextGame) -> prev.map2(x -> nextGame);

    static Either<DomainProblem, Tuple2<CSatz, CGame>> spielerSatzGewonnenEvt(Tuple2<CSatz, CGame> prev, SpielerSatzGewonnen event) {
        return handleSatzEvents(prev._1, event).map(replaceNextSatz.apply(prev));
    }

    static Either<DomainProblem, Tuple2<CSatz, CGame>> spielerPunktGewonnenEvt(Tuple2<CSatz, CGame> prev, SpielerPunktGewonnen event) {
        return handleGameEvents(prev._2, event).map(replaceNextGame.apply(prev));
    }

    static Either<DomainProblem, Tuple2<CSatz, CGame>> spielerGameGewonnenf(Tuple2<CSatz, CGame> prev, SpielerGameGewonnen event) {
        return eithers2Tuple(handleGameEvents(prev._2, event), handleSatzEvents(prev._1, event));
    }

    private static Either<DomainProblem, Tuple2<CSatz, CGame>> gegnerSatzGewonnen(final Tuple2<CSatz, CGame> prev, final GegnerSatzGewonnen event) {
        return handleSatzEvents(prev._1, event).map(replaceNextSatz.apply(prev));
    }

    private static Either<DomainProblem, Tuple2<CSatz, CGame>> gegnerPunktGewonnen(final Tuple2<CSatz, CGame> prev, final GegnerPunktGewonnen event) {
        return handleGameEvents(prev._2, event).map(replaceNextGame.apply(prev));
    }

    private static Either<DomainProblem, Tuple2<CSatz, CGame>> gegnerGameGewonnen(final Tuple2<CSatz, CGame> prev, final GegnerGameGewonnen event) {
        return eithers2Tuple(handleGameEvents(prev._2, event), handleSatzEvents(prev._1, event));
    }

    private static Either<DomainProblem, Tuple2<CSatz, CGame>> eithers2Tuple(final Either<DomainProblem, CGame> game, final Either<DomainProblem, CSatz> satz) {
        return game.flatMap(
            nextGame -> satz.map(
                nextSatz -> Tuple.of(nextSatz, nextGame)));
    }

    private static Either<DomainProblem, CSatz> handleSatzEvents(final CSatz prev, final DomainEvent event) {
        return Match(event).of(
            Case($(instanceOf(SpielerGameGewonnen.class)), routeToSatz().apply(prev)),
            Case($(instanceOf(GegnerGameGewonnen.class)), routeToSatz().apply(prev)),
            Case($(instanceOf(SpielerSatzGewonnen.class)), routeToSatz().apply(prev)),
            Case($(instanceOf(GegnerSatzGewonnen.class)), routeToSatz().apply(prev))
        );
    }

    private static Either<DomainProblem, CGame> handleGameEvents(final CGame prev, final DomainEvent event) {
        return Match(event).of(
            Case($(instanceOf(SpielerPunktGewonnen.class)), routeToGame().apply(prev)),
            Case($(instanceOf(GegnerPunktGewonnen.class)), routeToGame().apply(prev)),
            Case($(instanceOf(GegnerGameGewonnen.class)), routeToGame().apply(prev)),
            Case($(instanceOf(SpielerGameGewonnen.class)), routeToGame().apply(prev))
        );
    }

    static Function2<CGame, DomainEvent, Either<DomainProblem, CGame>> routeToGame() {
        return (prev, event) -> prev.apply(
            laufendesCGame -> CGameEventHandler.handleEvent(laufendesCGame, event),
            abgeschlossenesCGame -> Either.<DomainProblem, CGame>left(DomainProblem.eventNotValid));
    }

    static Function2<CSatz, DomainEvent, Either<DomainProblem, CSatz>> routeToSatz() {
        return (prev, event) -> CSatz.apply(
            prev,
            laufenderCSatz -> CSatzEventHandler.handleEvent(laufenderCSatz, event),
            abgeschlossenerCSatz -> Either.<DomainProblem, CSatz>left(DomainProblem.eventNotValid));
    }
}

