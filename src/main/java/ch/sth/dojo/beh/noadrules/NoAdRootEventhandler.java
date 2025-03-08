/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.noadrules;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cmatch.domain.CMatch;
import ch.sth.dojo.beh.cmatch.evt.CMatchEventHandler;
import ch.sth.dojo.beh.csatz.domain.CSatz;
import ch.sth.dojo.beh.csatz.evt.CSatzEventHandler;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.GegnerDomainEvent;
import ch.sth.dojo.beh.evt.GegnerGameGewonnen;
import ch.sth.dojo.beh.evt.GegnerMatchGewonnen;
import ch.sth.dojo.beh.evt.GegnerPunktGewonnen;
import ch.sth.dojo.beh.evt.GegnerSatzGewonnen;
import ch.sth.dojo.beh.evt.SpielerDomainEvent;
import ch.sth.dojo.beh.evt.SpielerGameGewonnen;
import ch.sth.dojo.beh.evt.SpielerMatchGewonnen;
import ch.sth.dojo.beh.evt.SpielerPunktGewonnen;
import ch.sth.dojo.beh.evt.SpielerSatzGewonnen;
import ch.sth.dojo.beh.noadgame.domain.NoAdGame;
import ch.sth.dojo.beh.noadgame.evt.NoAdGameEventHandler;
import io.vavr.Tuple;
import io.vavr.Tuple3;
import io.vavr.control.Either;

public class NoAdRootEventhandler {

    public static Either<DomainProblem, NoAdMatchState> handleEvent(NoAdMatchState prevState, DomainEvent event) {
        return switch (event) {
            case SpielerGameGewonnen spielerGameGewonnen -> delegateEventHandling(prevState, event);
            case SpielerMatchGewonnen spielerMatchGewonnen -> null;
            case SpielerPunktGewonnen spielerPunktGewonnen -> null;
            case SpielerSatzGewonnen spielerSatzGewonnen -> null;
            case GegnerGameGewonnen gegnerGameGewonnen -> null;
            case GegnerMatchGewonnen gegnerMatchGewonnen -> null;
            case GegnerPunktGewonnen gegnerPunktGewonnen -> null;
            case GegnerSatzGewonnen gegnerSatzGewonnen -> null;
        };

    }

    private static Either<DomainProblem, Tuple3<CMatch, CSatz, NoAdGame>> tuple3EithersToEitherTuple3(Either<DomainProblem, CMatch> eith1, Either<DomainProblem, CSatz> eith2,
        Either<DomainProblem, NoAdGame> eith3) {
        return eith1.flatMap(match -> eith2.flatMap(satz -> eith3.map(game -> Tuple.of(match, satz, game))));
    }

    private static Either<DomainProblem, NoAdMatchState> delegateEventHandling(final NoAdMatchState prev, final DomainEvent event) {
        return prev.tupled().map1(prevMatch -> CMatchEventHandler.handleEvent(prevMatch, event))
            .map2(prevSatz -> CSatzEventHandler.handleEvent(prevSatz, event))
            .map3(prevGame -> NoAdGameEventHandler.handleEvent(prevGame, event))
            .apply(NoAdRootEventhandler::tuple3EithersToEitherTuple3)
            .map(NoAdMatchState::untuple);
    }

    private static Either<DomainProblem, NoAdMatchState> spielerHandleEvent(final NoAdMatchState prevState, final SpielerDomainEvent spielerDomainEvent) {
        var res = switch (spielerDomainEvent) {
            case SpielerGameGewonnen spielerGameGewonnen -> NoAdGameEventHandler.handleEvent(prevState.game(), spielerGameGewonnen);
            case SpielerMatchGewonnen spielerMatchGewonnen -> NoAdGameEventHandler.handleEvent(prevState.game(), spielerMatchGewonnen);
            case SpielerPunktGewonnen spielerPunktGewonnen -> NoAdGameEventHandler.handleEvent(prevState.game(), spielerPunktGewonnen);
            case SpielerSatzGewonnen spielerSatzGewonnen -> NoAdGameEventHandler.handleEvent(prevState.game(), spielerSatzGewonnen);
        };

        return null;
    }

    private static Either<DomainProblem, NoAdMatchState> gegnerHandleEvent(final NoAdMatchState prevState, final GegnerDomainEvent gegnerDomainEvent) {
        return null;
    }

}

record NoAdMatchState(CMatch match, CSatz satz, NoAdGame game) {

    static NoAdMatchState untuple(Tuple3<CMatch, CSatz, NoAdGame> tuple) {
        return tuple.apply(NoAdMatchState::new);
    }

    Tuple3<CMatch, CSatz, NoAdGame> tupled() {
        return Tuple.of(match, satz, game);
    }
}