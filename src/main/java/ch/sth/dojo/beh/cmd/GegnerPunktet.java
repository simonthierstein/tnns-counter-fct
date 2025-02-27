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
import ch.sth.dojo.beh.csatz.domain.CSatz;
import ch.sth.dojo.beh.csatz.domain.LaufenderCSatz;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.GegnerGameGewonnen;
import ch.sth.dojo.beh.evt.GegnerPunktGewonnen;
import ch.sth.dojo.beh.evt.GegnerSatzGewonnen;
import io.vavr.Tuple2;
import io.vavr.control.Either;

public record GegnerPunktet() implements DomainCommand {

    static Either<DomainProblem, DomainEvent> applyC(Tuple2<CSatz, CGame> state, final GegnerPunktet cmd) {
        return state.apply(GegnerPunktet::apply);
    }

    private static Either<DomainProblem, DomainEvent> apply(CSatz cSatz, CGame cGame) {
        return cGame.apply(
            laufendesCGame -> applyToLaufendesCGame(laufendesCGame, cSatz),
            tiebreak -> right(applyToTiebreak(cSatz, tiebreak)),
            abgeschlossenesCGame -> applyToAbgeschlossenesCGame(cSatz, abgeschlossenesCGame));
    }

    private static DomainEvent applyToTiebreak(final CSatz cSatz, final Tiebreak tiebreak) {
        return condition(tiebreak, Tiebreak.passIfGegnerOnePunktBisSatz,
            x -> new GegnerSatzGewonnen(),
            x -> new GegnerPunktGewonnen()
        );
    }

    private static Either<DomainProblem, DomainEvent> applyToLaufendesCGame(LaufendesCGame laufendesCGame, CSatz satz) {
        return condition(laufendesCGame, LaufendesCGame.passIfGegnerOnePunktBisCGame,
            game -> applyToCSatz(satz),
            x -> right(new GegnerPunktGewonnen()));
    }

    private static Either<DomainProblem, DomainEvent> applyToCSatz(CSatz satz) {
        return CSatz.apply(satz,
            laufenderCSatz -> right(applyToLaufenderSatz(laufenderCSatz)),
            x -> left(DomainProblem.valueNotValid));
    }

    private static DomainEvent applyToLaufenderSatz(final LaufenderCSatz laufenderCSatz) {
        return condition(laufenderCSatz, LaufenderCSatz.passIfGegnerOneGameBisSatz,
            x -> new GegnerSatzGewonnen(),
            x -> new GegnerGameGewonnen());
    }

    private static Either<DomainProblem, DomainEvent> applyToAbgeschlossenesCGame(final CSatz prev, AbgeschlossenesCGame abgeschlossenesCGame) {
        return CSatz.apply(prev,
            laufend -> right(new GegnerPunktGewonnen()),
            abgeschlossenerCSatz -> left(DomainProblem.valueNotValid)); // TODO sth/21.02.2025 : where is new game instantiated?
    }

}
