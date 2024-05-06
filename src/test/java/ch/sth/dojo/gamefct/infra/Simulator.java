/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.gamefct.infra;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.condition.MappedCondition.mappedCondition;

import ch.sth.dojo.es.Game;
import ch.sth.dojo.es.GameAggregateRoot;
import ch.sth.dojo.es.LaufendesGame;
import ch.sth.dojo.es.PreInitializedGame;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.events.GameErzeugt;
import ch.sth.dojo.es.events.SpielerHatPunktGewonnen;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.control.Option;
import java.util.function.Function;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.assertj.core.condition.MappedCondition;
import org.hamcrest.core.IsInstanceOf;
import org.junit.jupiter.api.Test;

public class Simulator {

    @Test
    void name() {

        List<DomainEvent> repo = List.empty();


        final GameErzeugt gameErzeugt = GameAggregateRoot.erzeugeGame();

        final List<DomainEvent> app1 = repo.append(gameErzeugt);


        Function<PreInitializedGame, DomainEvent> erzeuge = x -> GameAggregateRoot.erzeugeGame();
        Function<LaufendesGame, DomainEvent> spieler = x -> GameAggregateRoot.spielerPunktet(x);
        Function<LaufendesGame, DomainEvent> gegner = x -> GameAggregateRoot.gegnerPunktet(x);

        var orElseThrow = Option.some(GameAggregateRoot.empty())
                .map(state -> Tuple.of(state, erzeuge.apply(state)))
                .map(doSpielerPunktet(spieler))
                .map(doSpielerPunktet(spieler))
                .map(doSpielerPunktet(gegner))
                .map(doSpielerPunktet(spieler))
                .map(doSpielerPunktet(spieler))
                .getOrElseThrow(() -> new RuntimeException());


        assertThat(orElseThrow._2).isInstanceOf(SpielerHatPunktGewonnen.class);

    }

    private static Function<Tuple2<? extends Game, DomainEvent>, Tuple2<Game, DomainEvent>> doSpielerPunktet(final Function<LaufendesGame, DomainEvent> punktetCommandFunction) {
        return stateEvent2State().andThen(state2StateEvent(punktetCommandFunction));
    }

    private static Function<Game, Tuple2<Game, DomainEvent>> state2StateEvent(final Function<LaufendesGame, DomainEvent> spieler) {
        return state -> Tuple.of(state, Game.apply(state,
                spieler,
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