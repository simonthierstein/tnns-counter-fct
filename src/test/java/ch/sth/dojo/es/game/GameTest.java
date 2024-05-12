package ch.sth.dojo.es.game;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.Unit;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.events.GameErzeugt;
import ch.sth.dojo.es.events.SpielerHatPunktGewonnen;
import ch.sth.dojo.es.game.trans.Unit2Pre;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Either;
import java.util.function.Function;
import org.junit.jupiter.api.Test;

class GameTest {

    @Test
    void name() {
        final Either<DomainError, Game> next = Game.handleEvent()
                .apply(Unit2Pre.createEmpty().apply(Unit.Unit()))
                .apply(new GameErzeugt());

        final Either<DomainError, Game> games = next.flatMap(game -> Game.handleEvent().apply(game, new SpielerHatPunktGewonnen()))
                .flatMap(game -> Game.handleEvent().apply(game, new SpielerHatPunktGewonnen()))
                .flatMap(game -> Game.handleEvent().apply(game, new SpielerHatPunktGewonnen()))
                .flatMap(game -> Game.handleEvent().apply(game, new SpielerHatPunktGewonnen()))
                .flatMap(game -> Game.handleEvent().apply(game, new SpielerHatPunktGewonnen()))
                .flatMap(game -> Game.handleEvent().apply(game, new SpielerHatPunktGewonnen()));


        System.out.println(games);
    }

    @Test
    void fdsafas() {

        var games = Either.<DomainError, Unit>right(Unit.Unit())
                .map(Unit2Pre.createEmpty())
                .map(state2StateTuple(Game.erzeugeGame()))
                .flatMap(applyEvent())
                .flatMap(applyCommand(Game.handleSpielerPunktet()))
                .flatMap(applyEvent())
                .flatMap(applyCommand(Game.handleSpielerPunktet()))
                .flatMap(applyEvent())
                .flatMap(applyCommand(Game.handleSpielerPunktet()))
                .flatMap(applyEvent())
                .flatMap(applyCommand(Game.handleSpielerPunktet()))
                .flatMap(applyEvent())
                .flatMap(applyCommand(Game.handleSpielerPunktet()));


        System.out.println(games);


    }
    @Test
    void rewqrewq() {

        var games = Either.<DomainError, Unit>right(Unit.Unit())
                .map(Unit2Pre.createEmpty())
                .map(state2StateTuple(Game.erzeugeGame()))
//                .flatMap(applyEvent())
//                .flatMap(applyCommand(Game.handleGegnerPunktet()))
                .flatMap(applyEvent());


        System.out.println(games);


    }

    private static Function<Game, Either<DomainError, ? extends Tuple2<Game, DomainEvent>>> applyCommand(final Function<Game, Either<DomainError, DomainEvent>> command) {
        return game -> command.apply(game).map(event -> Tuple.of(game, event));
    }

    private static <I extends Game, E extends DomainEvent> Function<Tuple2<I, E>, Either<DomainError, Game>> applyEvent() {
        return stateTuple -> stateTuple.apply(Game.handleEvent());
    }

    private static <I, E> Function<I, Tuple2<I, E>> state2StateTuple(final Function<I, E> fct) {
        return i -> Tuple.of(i, fct.apply(i));
    }

}