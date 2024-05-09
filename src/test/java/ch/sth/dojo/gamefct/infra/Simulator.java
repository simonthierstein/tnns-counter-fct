/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.gamefct.infra;

import static org.assertj.core.api.Assertions.assertThat;

import ch.sth.dojo.es.Game;
import ch.sth.dojo.es.GameAggregateRoot;
import ch.sth.dojo.es.LaufendesGame;
import ch.sth.dojo.es.commands.GegnerPunktet;
import ch.sth.dojo.es.commands.SpielerPunktet;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.events.SpielerHatGameGewonnen;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.util.function.Function;
import org.junit.jupiter.api.Test;

class Simulator {

    @Test
    void name() {
        var orElseThrow = Option.some(Game.empty())
                .map(state -> Tuple.of(state, state).map2(GameAggregateRoot.erzeugeSpiel()))
                .map(unwrap(doSpielerPunktet()))
                .map(unwrap(doSpielerPunktet()))
                .map(unwrap(doGegnerPunktet()))
                .map(unwrap(doSpielerPunktet()))
                .map(unwrap(doSpielerPunktet()))
                .map(unwrap(doSpielerPunktet()))
                .getOrElseThrow(RuntimeException::new);


        assertThat(orElseThrow._2).isInstanceOf(SpielerHatGameGewonnen.class);

    }

    private static Function<Tuple2<? extends Game, DomainEvent>, Tuple2<Game, DomainEvent>> unwrap(final Function<Tuple2<? extends Game, DomainEvent>, Tuple2<Game, Either<DomainError, DomainEvent>>> commandFct) {
        return commandFct.andThen(gameEitherTuple2 -> gameEitherTuple2.map2(eith->eith.getOrElseThrow(err->new RuntimeException(err.toString()))));
    }

    private static Function<Tuple2<? extends Game, DomainEvent>, Tuple2<Game, Either<DomainError, DomainEvent>>> doSpielerPunktet() {
        return stateEvent2State().andThen(command2StateEvent(GameAggregateRoot.command(new SpielerPunktet())));
    }

    private static Function<Tuple2<? extends Game, DomainEvent>, Tuple2<Game, Either<DomainError, DomainEvent>>> doGegnerPunktet() {
        return stateEvent2State().andThen(command2StateEvent(GameAggregateRoot.command(new GegnerPunktet())));
    }

    private static Function<Game, Tuple2<Game, Either<DomainError, DomainEvent>>> command2StateEvent(final Function<LaufendesGame, DomainEvent> laufendesGameTFunction) {
        return state -> Tuple.of(state, Game.apply(state,
                laufendesGameTFunction.andThen(Either::right),
                err(),
                err()));
    }

    private static Function<Tuple2<? extends Game, DomainEvent>, Game> stateEvent2State() {
        return t2 -> t2.map2(domainEvent -> GameAggregateRoot.handleEvent(t2._1, domainEvent))._2;
    }


    static <I extends Game> Function<I, Either<DomainError, DomainEvent>> err() {
        return i -> Either.left(new InvalidCommandForState(null, i));
    }
}

record PlayerScore() {
}

record OpponentScore() {
}