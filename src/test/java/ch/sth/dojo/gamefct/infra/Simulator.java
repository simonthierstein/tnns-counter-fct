/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.gamefct.infra;

import static org.assertj.core.api.Assertions.assertThat;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.Game;
import ch.sth.dojo.es.GameAggregateRoot;
import ch.sth.dojo.es.InvalidCommandForState;
import ch.sth.dojo.es.PreInitializedGame;
import ch.sth.dojo.es.commands.DomainCommand;
import ch.sth.dojo.es.commands.GegnerPunktet;
import ch.sth.dojo.es.commands.SpielerPunktet;
import ch.sth.dojo.es.events.DomainEvent;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Either;
import java.util.function.Function;
import java.util.function.Predicate;
import org.junit.jupiter.api.Test;

class SimulatorTest {

    @Test
    void name() {
        Either<DomainError, Tuple2<Game, DomainEvent>> orElseThrow = Either.<DomainError, PreInitializedGame>right(Game.empty())
                .map(state -> Tuple.of(state, state).map2(GameAggregateRoot.erzeugeSpiel()))
                .flatMap(doSpielerPunktet())
                .flatMap(doSpielerPunktet())
                .flatMap(doSpielerPunktet())
                .flatMap(doGegnerPunktet())
                .flatMap(doSpielerPunktet())
                .flatMap(doSpielerPunktet())
                .peekLeft(System.err::println);


        assertThat(passIfeitherLeftPred()).accepts(orElseThrow);
        assertThat(Predicate.not(passIfeitherRightPred())).accepts(orElseThrow);

    }

    private static Predicate<Either<?, ?>> passIfeitherLeftPred() {
        return Either::isLeft;
    }

    private static Predicate<Either<?, ?>> passIfeitherRightPred() {
        return Either::isRight;
    }

    private static Function<Tuple2<? extends Game, DomainEvent>, Either<DomainError, Tuple2<Game, DomainEvent>>> doSpielerPunktet() {
        return stateEvent2State()
                .andThen(command2StateEvent(new SpielerPunktet()));
    }

    private static Function<Tuple2<? extends Game, DomainEvent>, Either<DomainError, Tuple2<Game, DomainEvent>>> doGegnerPunktet() {
        return stateEvent2State()
                .andThen(command2StateEvent(new GegnerPunktet()));
    }

    private static Function<Game, Either<DomainError, Tuple2<Game, DomainEvent>>> command2StateEvent(final DomainCommand command) {
        return game -> Game.commandHandler(game, command, () -> new InvalidCommandForState(game, null))
                .map(domainEvent -> Tuple.of(game, domainEvent));
    }

    private static Function<Tuple2<? extends Game, DomainEvent>, Game> stateEvent2State() {
        return t2 -> t2.map2(domainEvent -> GameAggregateRoot.handleEvent(t2._1, domainEvent))._2;
    }

}

record PlayerScore() {
}

record OpponentScore() {
}