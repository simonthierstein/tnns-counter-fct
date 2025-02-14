/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cgame.cmd;

import static ch.sth.dojo.beh.Condition.condition;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cgame.domain.CGame;
import ch.sth.dojo.beh.cgame.domain.LaufendesCGame;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.SpielerGameGewonnen;
import ch.sth.dojo.beh.evt.SpielerPunktGewonnen;
import io.vavr.control.Either;
import java.util.function.Function;

public record SpielerPunktet() implements DomainCommand {

    public static final Function<LaufendesCGame, DomainEvent> laufendesGame2EventFct = prev -> condition(prev, LaufendesCGame.passIfSpielerOnePunktBisCGame,
        x -> new SpielerGameGewonnen(),
        x -> new SpielerPunktGewonnen());

    public static Either<DomainProblem, DomainEvent> applyC(CGame state, SpielerPunktet cmd) {
        return state.apply(lg -> Either.<DomainProblem, LaufendesCGame>right(lg)
            .map(laufendesGame2EventFct), x -> Either.left(DomainProblem.invalidValue));
    }

}
