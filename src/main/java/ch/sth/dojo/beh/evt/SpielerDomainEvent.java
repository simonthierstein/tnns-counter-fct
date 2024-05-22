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
import java.util.function.Function;

public sealed interface SpielerDomainEvent extends DomainEvent permits SpielerPunktGewonnen, SpielerGameGewonnen {

    static <T> T apply(SpielerDomainEvent evt,
        Function<SpielerPunktGewonnen, T> spielerPunktGewonnenTFunction,
        Function<SpielerGameGewonnen, T> spielerGameGewonnenTFunction) {
        return Match(evt).of(
            Case($(instanceOf(SpielerPunktGewonnen.class)), spielerPunktGewonnenTFunction),
            Case($(instanceOf(SpielerGameGewonnen.class)), spielerGameGewonnenTFunction)
        );
    }

    static Either<DomainProblem, Game> handleEvent(SpielerDomainEvent evt, Game prev) {
        return Match(evt).of(
            Case($(instanceOf(SpielerGameGewonnen.class)), x -> StateHandler.spielerGameGewonnen(prev)),
            Case($(instanceOf(SpielerPunktGewonnen.class)), x -> StateHandler.spielerPunktet(prev))
        );
    }
}
