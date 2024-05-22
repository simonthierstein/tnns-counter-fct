/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.evt;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.domain.Game;
import ch.sth.dojo.beh.state.StateHandler;
import io.vavr.control.Either;

public sealed interface GegnerDomainEvent extends DomainEvent permits GegnerPunktGewonnen, GegnerGameGewonnen {

    static Either<DomainProblem, Game> handleEvent(GegnerDomainEvent evt, Game prev) {
        return Match(evt).of(
            Case($(instanceOf(GegnerPunktGewonnen.class)), x -> StateHandler.gegnerPunktet(prev)),
            Case($(instanceOf(GegnerGameGewonnen.class)), x -> StateHandler.gegnerGameGewonnen(prev))
        );
    }
}
