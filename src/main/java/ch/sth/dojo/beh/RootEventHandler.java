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
import ch.sth.dojo.beh.evt.GegnerGameGewonnen;
import ch.sth.dojo.beh.evt.GegnerPunktGewonnen;
import ch.sth.dojo.beh.evt.GegnerSatzGewonnen;
import ch.sth.dojo.beh.evt.SpielerGameGewonnen;
import ch.sth.dojo.beh.evt.SpielerPunktGewonnen;
import ch.sth.dojo.beh.evt.SpielerSatzGewonnen;
import io.vavr.Function2;
import io.vavr.Tuple2;
import io.vavr.control.Either;

public interface RootEventHandler {

    static Tuple2<CSatz, CGame> handleEvent(Tuple2<CSatz, CGame> prev, DomainEvent event) {

        return prev.map2(x1 -> Match(event).of(
            Case($(instanceOf(SpielerPunktGewonnen.class)), routeToGame().apply(prev._2)),
            Case($(instanceOf(GegnerPunktGewonnen.class)), routeToGame().apply(prev._2)),
            Case($(instanceOf(GegnerGameGewonnen.class)), routeToGame().apply(prev._2)),
            Case($(instanceOf(SpielerGameGewonnen.class)), routeToGame().apply(prev._2))
        )).map1(x -> Match(event).of(
            Case($(instanceOf(SpielerGameGewonnen.class)), routeToSatz().apply(prev._1)),
            Case($(instanceOf(GegnerGameGewonnen.class)), routeToSatz().apply(prev._1)),
            Case($(instanceOf(SpielerSatzGewonnen.class)), routeToSatz().apply(prev._1)),
            Case($(instanceOf(GegnerSatzGewonnen.class)), routeToSatz().apply(prev._1))
        ));
    }

    static Function2<CGame, DomainEvent, CGame> routeToGame() {
        return (prev, event) -> prev.apply(
                laufendesCGame -> CGameEventHandler.handleEvent(laufendesCGame, event),
                abgeschlossenesCGame -> Either.<DomainProblem, CGame>left(DomainProblem.eventNotValid))
            .get(); // TODO sth/16.02.2025 : gut genug?
    }

    static Function2<CSatz, DomainEvent, CSatz> routeToSatz() {
        return (prev, event) -> CSatz.apply(
                prev,
                laufenderCSatz -> CSatzEventHandler.handleEvent(laufenderCSatz, event),
                abgeschlossenerCSatz -> Either.<DomainProblem, CSatz>left(DomainProblem.eventNotValid))
            .get(); // TODO sth/16.02.2025 : gut genug?
    }

}
