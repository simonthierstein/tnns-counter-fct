/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cgame.cmd;

import static ch.sth.dojo.beh.Condition.condition;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cgame.domain.game.CGame;
import ch.sth.dojo.beh.cgame.domain.game.LaufendesCGame;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.GegnerGameGewonnen;
import ch.sth.dojo.beh.evt.GegnerPunktGewonnen;
import io.vavr.control.Either;
import java.util.function.Function;

public record GegnerPunktet() implements DomainCommand {

    static Either<DomainProblem, DomainEvent> applyC(final CGame state, final GegnerPunktet cmd) {
        final Function<LaufendesCGame, DomainEvent> lgFct = prev -> condition(prev, LaufendesCGame.passIfGegnerOnePunktBisCGame,
            x -> new GegnerGameGewonnen(),
            x -> new GegnerPunktGewonnen());

        return Either.<DomainProblem, CGame>right(state)
            .flatMap(prev -> prev.apply(lgFct.andThen(Either::right), x -> Either.left(DomainProblem.invalidValue)));
    }
}
