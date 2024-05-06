/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.gamefct.infra;

import static org.assertj.core.api.Assertions.assertThat;

import ch.sth.dojo.es.AbgeschlossenesGame;
import ch.sth.dojo.es.Game;
import ch.sth.dojo.es.GameAggregateRoot;
import ch.sth.dojo.es.LaufendesGame;
import ch.sth.dojo.es.PreInitializedGame;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.events.GameErzeugt;
import ch.sth.dojo.es.events.SpielerHatPunktGewonnen;
import io.vavr.Tuple;
import io.vavr.collection.List;
import io.vavr.control.Option;
import java.util.function.Function;
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
        Function<AbgeschlossenesGame, DomainEvent> errAbg = x -> {
            throw new RuntimeException(String.format("invalid command for state=%1", x));
        };
        Function<PreInitializedGame, DomainEvent> errPre = x -> {
            throw new RuntimeException(String.format("invalid command for state=%1", x));
        };


        final DomainEvent orElseThrow = Option.some(GameAggregateRoot.empty())
                .map(state -> Tuple.of(state, erzeuge.apply(state)))
                .map(t2 -> t2.map2(domainEvent -> GameAggregateRoot.handleEvent(t2._1, domainEvent))._2)
                .map(state -> Game.apply(state,
                        spieler,
                        errAbg,
                        errPre))
                .getOrElseThrow(() -> new RuntimeException());


        assertThat(orElseThrow).isInstanceOf(SpielerHatPunktGewonnen.class);

    }
}

record PlayerScore() {
}

record OpponentScore() {
}