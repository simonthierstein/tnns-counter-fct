/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.gamefct.infra;

import static org.assertj.core.api.Assertions.assertThat;

import ch.sth.dojo.es.Game;
import ch.sth.dojo.es.GameAggregateRoot;
import ch.sth.dojo.es.LaufendesGame;
import ch.sth.dojo.es.PreInitializedGame;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.events.SpielerHatGameGewonnen;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Option;
import java.util.function.Function;
import org.junit.jupiter.api.Test;

class Simulator {

    @Test
    void name() {
        var orElseThrow = Option.some(Game.empty())
                .map(state -> Tuple.of(state, state).map2(GameAggregateRoot.erzeugeSpiel()))
                .map(doSpielerPunktet())
                .map(doSpielerPunktet())
                .map(doGegnerPunktet())
                .map(doSpielerPunktet())
                .map(doSpielerPunktet())
                .getOrElseThrow(RuntimeException::new);


        assertThat(orElseThrow._2).isInstanceOf(SpielerHatGameGewonnen.class);

    }

    private static Function<Tuple2<? extends Game, DomainEvent>, Tuple2<Game, DomainEvent>> doSpielerPunktet() {
        return stateEvent2State().andThen(command2StateEvent(GameAggregateRoot.spielerPunktetFct()));
    }

    private static Function<Tuple2<? extends Game, DomainEvent>, Tuple2<Game, DomainEvent>> doGegnerPunktet() {
        return stateEvent2State().andThen(command2StateEvent(GameAggregateRoot.gegnerPunktetFct()));
    }

    private static Function<Game, Tuple2<Game, DomainEvent>> command2StateEvent(final Function<LaufendesGame, DomainEvent> laufendesGameTFunction) {
        return state -> Tuple.of(state, Game.apply(state,
                laufendesGameTFunction,
                err(),
                err()));
    }

    private static Function<Tuple2<? extends Game, DomainEvent>, Game> stateEvent2State() {
        return t2 -> t2.map2(domainEvent -> GameAggregateRoot.handleEvent(t2._1, domainEvent))._2;
    }


    static <I> Function<I, DomainEvent> err() {
        return i -> {
            throw new RuntimeException(String.format("invalid command for state=%s", i));
        };
    }
}

record PlayerScore() {
}

record OpponentScore() {
}