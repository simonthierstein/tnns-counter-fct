/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh;

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
import ch.sth.dojo.beh.noadgame.domain.NoAdGame;
import ch.sth.dojo.beh.noadgame.evt.NoAdGameEventHandler;
import io.vavr.Tuple;
import io.vavr.Tuple3;
import io.vavr.control.Either;

public class NoAdRootEventHandler {

    static Either<DomainProblem, Tuple3<CMatch, CSatz, NoAdGame>> handleEvent_(Tuple3<CMatch, CSatz, NoAdGame> prev, DomainEvent event) {
        return switch (event) {
            case GegnerPunktGewonnen evt -> delegateEventHandling(prev, evt);
            case GegnerGameGewonnen evt -> delegateEventHandling(prev, evt);
            case GegnerSatzGewonnen evt -> delegateEventHandling(prev, evt);
            case GegnerMatchGewonnen evt -> delegateEventHandling(prev, evt);
            case SpielerPunktGewonnen evt -> delegateEventHandling(prev, evt);
            case SpielerGameGewonnen evt -> delegateEventHandling(prev, evt);
            case SpielerSatzGewonnen evt -> delegateEventHandling(prev, evt);
            case SpielerMatchGewonnen evt -> delegateEventHandling(prev, evt);
        };
    }

    private static Either<DomainProblem, Tuple3<CMatch, CSatz, NoAdGame>> delegateEventHandling(final Tuple3<CMatch, CSatz, NoAdGame> prev, final DomainEvent event) {
        return prev.map1(prevMatch -> CMatchEventHandler.handleEvent(prevMatch, event))
            .map2(prevSatz -> CSatzEventHandler.handleEvent(prevSatz, event))
            .map3(prevGame -> NoAdGameEventHandler.handleEvent(prevGame, event))
            .apply((eith1, eith2, eith3) ->
                eith1.flatMap(match -> eith2.flatMap(satz -> eith3.map(game -> Tuple.of(match, satz, game)))));
    }
}
