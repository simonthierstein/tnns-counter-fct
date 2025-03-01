/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cmd;

import static ch.sth.dojo.beh.Condition.condition;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cgame.domain.AbgeschlossenesCGame;
import ch.sth.dojo.beh.cgame.domain.CGame;
import ch.sth.dojo.beh.cgame.domain.LaufendesCGame;
import ch.sth.dojo.beh.cgame.domain.Tiebreak;
import ch.sth.dojo.beh.cmatch.domain.CMatch;
import ch.sth.dojo.beh.csatz.domain.CSatz;
import ch.sth.dojo.beh.csatz.domain.LaufenderCSatz;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.SpielerGameGewonnen;
import ch.sth.dojo.beh.evt.SpielerPunktGewonnen;
import ch.sth.dojo.beh.evt.SpielerSatzGewonnen;
import io.vavr.Tuple3;
import io.vavr.control.Either;

public record SpielerPunktet() implements DomainCommand {

    public static Either<DomainProblem, DomainEvent> applyC(Tuple3<CMatch, CSatz, CGame> state, SpielerPunktet cmd) {
        return state.apply(SpielerPunktet::apply);
    }

    private static Either<DomainProblem, DomainEvent> apply(CMatch cMatch, CSatz cSatz, CGame cGame) {
        return cGame.apply(
            laufendesCGame -> applyToLaufendesCGame(laufendesCGame, cSatz),
            tiebreak -> applyToTiebreak(tiebreak),
            abgeschlossenesCGame -> applyToAbgeschlossenesCGame(cSatz, abgeschlossenesCGame));
    }

    private static Either<DomainProblem, DomainEvent> applyToTiebreak(final Tiebreak tiebreak) {
        return right(condition(tiebreak, Tiebreak.passIfSpielerOnePunktBisSatz,
            x -> new SpielerSatzGewonnen(),
            x -> new SpielerPunktGewonnen()
        ));
    }

    private static Either<DomainProblem, DomainEvent> applyToLaufendesCGame(LaufendesCGame laufendesCGame, CSatz satz) {
        return condition(laufendesCGame, LaufendesCGame.passIfSpielerOnePunktBisCGame,
            game -> applyToCSatz(satz),
            x -> right(new SpielerPunktGewonnen()));
    }

    private static Either<DomainProblem, DomainEvent> applyToCSatz(CSatz satz) {
        return CSatz.apply(satz,
            laufenderCSatz -> right(applyToLaufenderSatz(laufenderCSatz)),
            x -> left(DomainProblem.valueNotValid));
    }

    private static DomainEvent applyToLaufenderSatz(final LaufenderCSatz laufenderCSatz) {
        return condition(laufenderCSatz, LaufenderCSatz.passIfSpielerOneGameBisSatz,
            x -> new SpielerSatzGewonnen(),
            x -> new SpielerGameGewonnen());
    }

    private static Either<DomainProblem, DomainEvent> applyToAbgeschlossenesCGame(final CSatz prev, AbgeschlossenesCGame abgeschlossenesCGame) {
        return CSatz.apply(prev,
            laufend -> right(new SpielerPunktGewonnen()),
            abgeschlossenerCSatz -> left(DomainProblem.valueNotValid));
    }

}
