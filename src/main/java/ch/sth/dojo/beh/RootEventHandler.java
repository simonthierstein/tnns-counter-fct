package ch.sth.dojo.beh;

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
            case SpielerGameGewonnen spielerGameGewonnen -> spielerGameGewonnen(prev, spielerGameGewonnen);
            case SpielerSatzGewonnen spielerSatzGewonnen -> spielerSatzGewonnen(prev, spielerSatzGewonnen);

        };
    }

    Function2<Tuple2<CSatz, CGame>, CSatz, Tuple2<CSatz, CGame>> replaceNextSatz = (prev, nextSatz) -> prev.map1(x -> nextSatz);
    Function2<Tuple2<CSatz, CGame>, CGame, Tuple2<CSatz, CGame>> replaceNextGame = (prev, nextGame) -> prev.map2(x -> nextGame);

    static Either<DomainProblem, Tuple2<CSatz, CGame>> spielerSatzGewonnen(Tuple2<CSatz, CGame> prev, SpielerSatzGewonnen event) {
        return eithers2Tuple(CGameEventHandler.handleCGameEvent(prev._2, event), CSatzEventHandler.handleCSatzEvent(prev._1, event));
    }

    static Either<DomainProblem, Tuple2<CSatz, CGame>> spielerPunktGewonnenEvt(Tuple2<CSatz, CGame> prev, SpielerPunktGewonnen event) {
        return CGameEventHandler.handleCGameEvent(prev._2, event).map(replaceNextGame.apply(prev));
    }

    static Either<DomainProblem, Tuple2<CSatz, CGame>> spielerGameGewonnen(Tuple2<CSatz, CGame> prev, SpielerGameGewonnen event) {
        return eithers2Tuple(CGameEventHandler.handleCGameEvent(prev._2, event), CSatzEventHandler.handleCSatzEvent(prev._1, event));
    }

    private static Either<DomainProblem, Tuple2<CSatz, CGame>> gegnerSatzGewonnen(final Tuple2<CSatz, CGame> prev, final GegnerSatzGewonnen event) {
        return eithers2Tuple(CGameEventHandler.handleCGameEvent(prev._2, event), CSatzEventHandler.handleCSatzEvent(prev._1, event));
    }

    private static Either<DomainProblem, Tuple2<CSatz, CGame>> gegnerPunktGewonnen(final Tuple2<CSatz, CGame> prev, final GegnerPunktGewonnen event) {
        return CGameEventHandler.handleCGameEvent(prev._2, event).map(replaceNextGame.apply(prev));
    }

    private static Either<DomainProblem, Tuple2<CSatz, CGame>> gegnerGameGewonnen(final Tuple2<CSatz, CGame> prev, final GegnerGameGewonnen event) {
        return eithers2Tuple(CGameEventHandler.handleCGameEvent(prev._2, event), CSatzEventHandler.handleCSatzEvent(prev._1, event));
    }

    private static Either<DomainProblem, Tuple2<CSatz, CGame>> eithers2Tuple(final Either<DomainProblem, CGame> game, final Either<DomainProblem, CSatz> satz) {
        return game.flatMap(
            nextGame -> satz.map(
                nextSatz -> Tuple.of(nextSatz, nextGame)));
    }

}

