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
import ch.sth.dojo.beh.cmatch.domain.LaufendesMatch;
import ch.sth.dojo.beh.csatz.domain.CSatz;
import ch.sth.dojo.beh.csatz.domain.LaufenderCSatz;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.GegnerGameGewonnen;
import ch.sth.dojo.beh.evt.GegnerMatchGewonnen;
import ch.sth.dojo.beh.evt.GegnerPunktGewonnen;
import ch.sth.dojo.beh.evt.GegnerSatzGewonnen;
import io.vavr.Tuple3;
import io.vavr.control.Either;

public record GegnerPunktet() implements DomainCommand {

    static Either<DomainProblem, DomainEvent> applyC(Tuple3<CMatch, CSatz, CGame> state, final GegnerPunktet cmd) {
        return state.apply(GegnerPunktet::apply);
    }

    private static Either<DomainProblem, DomainEvent> apply(CMatch match, CSatz cSatz, CGame cGame) {
        return cGame.apply(
            laufendesCGame -> applyToLaufendesCGame(laufendesCGame, cSatz, match),
            tiebreak -> right(applyToTiebreak(cSatz, tiebreak)),
            abgeschlossenesCGame -> applyToAbgeschlossenesCGame(cSatz, abgeschlossenesCGame));
    }

    private static DomainEvent applyToTiebreak(final CSatz cSatz, final Tiebreak tiebreak) {
        return condition(tiebreak, Tiebreak.passIfGegnerOnePunktBisSatz,
            x -> new GegnerSatzGewonnen(),
            x -> new GegnerPunktGewonnen()
        );
    }

    private static Either<DomainProblem, DomainEvent> applyToLaufendesCGame(LaufendesCGame laufendesCGame, CSatz satz, final CMatch cMatch) {
        return condition(laufendesCGame, LaufendesCGame.passIfGegnerOnePunktBisCGame,
            game -> applyToCSatz(satz, cMatch),
            x -> right(new GegnerPunktGewonnen()));
    }

    private static Either<DomainProblem, DomainEvent> applyToCSatz(CSatz satz, final CMatch cMatch) {
        return CSatz.apply(satz,
            laufenderCSatz -> applyToLaufenderSatz(laufenderCSatz, cMatch),
            x -> left(DomainProblem.valueNotValid));
    }

    private static Either<DomainProblem, DomainEvent> applyToLaufenderSatz(final LaufenderCSatz laufenderCSatz, final CMatch cMatch) {
        return condition(laufenderCSatz, LaufenderCSatz.passIfGegnerOneGameBisSatz,
            x -> applyToMatch(cMatch),
            x -> right(new GegnerGameGewonnen()));
    }

    private static Either<DomainProblem, DomainEvent> applyToMatch(CMatch match) {
        return CMatch.apply(match,
            laufendesMatch -> right(applyToLaufendesMatch(laufendesMatch)),
            abgeschlossenesMatch -> left(DomainProblem.valueNotValid)
        );
    }

    private static DomainEvent applyToLaufendesMatch(final LaufendesMatch laufendesMatch) {
        return condition(laufendesMatch, LaufendesMatch.passIfGegnerOneSatzBisMatch,
            x -> new GegnerMatchGewonnen(),
            x -> new GegnerSatzGewonnen()
        );
    }

    private static Either<DomainProblem, DomainEvent> applyToAbgeschlossenesCGame(final CSatz prev, AbgeschlossenesCGame abgeschlossenesCGame) {
        return CSatz.apply(prev,
            laufend -> right(new GegnerPunktGewonnen()),
            abgeschlossenerCSatz -> left(DomainProblem.valueNotValid));
    }

}
