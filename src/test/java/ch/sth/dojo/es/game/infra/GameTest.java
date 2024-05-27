package ch.sth.dojo.es.game.infra;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.Unit;
import ch.sth.dojo.es.Unit2Pre;
import ch.sth.dojo.es.cmd.game.GameCommandHandler;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.events.GameErzeugt;
import ch.sth.dojo.es.events.SpielerHatPunktGewonnen;
import ch.sth.dojo.es.evt.game.GameEventHandler;
import ch.sth.dojo.es.game.Game;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Either;
import java.util.function.Function;
import org.junit.jupiter.api.Test;

class GameTest {

    @Test
    void name() {
        final Either<DomainError, Game> next = GameEventHandler.handleEvent()
                .apply(Unit2Pre.createEmpty().apply(Unit.Unit()))
                .apply(new GameErzeugt());

        final Either<DomainError, Game> games = next.flatMap(game -> GameEventHandler.handleEvent().apply(game, new SpielerHatPunktGewonnen()))
                .flatMap(game -> GameEventHandler.handleEvent().apply(game, new SpielerHatPunktGewonnen()))
                .flatMap(game -> GameEventHandler.handleEvent().apply(game, new SpielerHatPunktGewonnen()))
                .flatMap(game -> GameEventHandler.handleEvent().apply(game, new SpielerHatPunktGewonnen()))
                .flatMap(game -> GameEventHandler.handleEvent().apply(game, new SpielerHatPunktGewonnen()))
                .flatMap(game -> GameEventHandler.handleEvent().apply(game, new SpielerHatPunktGewonnen()));


        System.out.println(games);
    }

    @Test
    void spielerPunktet() {

        var games = Either.<DomainError, Unit>right(Unit.Unit())
                .map(Unit2Pre.createEmpty())
                .map(state2StateTuple(GameCommandHandler.erzeugeGame()))
                .flatMap(applyEvent())
                .flatMap(applyCommand(GameCommandHandler.handleSpielerPunktet()))
                .flatMap(applyEvent())
                .flatMap(applyCommand(GameCommandHandler.handleSpielerPunktet()))
                .flatMap(applyEvent())
                .flatMap(applyCommand(GameCommandHandler.handleSpielerPunktet()))
                .flatMap(applyEvent())
                .flatMap(applyCommand(GameCommandHandler.handleSpielerPunktet()))
                .flatMap(applyEvent())
                .flatMap(applyCommand(GameCommandHandler.handleSpielerPunktet()));


        System.out.println(games);


    }
    @Test
    void gegnerPunktet() {

        var games = Either.<DomainError, Unit>right(Unit.Unit())
                .map(Unit2Pre.createEmpty())
                .map(state2StateTuple(GameCommandHandler.erzeugeGame()))
                .flatMap(applyEvent())
                .flatMap(applyCommand(GameCommandHandler.handleGegnerPunktet()))
                .flatMap(applyEvent())
                .flatMap(applyCommand(GameCommandHandler.handleGegnerPunktet()))
                .flatMap(applyEvent());


        System.out.println(games);


    }

    private static Function<Game, Either<DomainError, ? extends Tuple2<Game, DomainEvent>>> applyCommand(final Function<Game, Either<DomainError, DomainEvent>> command) {
        return game -> command.apply(game).map(event -> Tuple.of(game, event));
    }

    private static <I extends Game, E extends DomainEvent> Function<Tuple2<I, E>, Either<DomainError, Game>> applyEvent() {
        return stateTuple -> stateTuple.apply(GameEventHandler.handleEvent());
    }

    private static <I, E> Function<I, Tuple2<I, E>> state2StateTuple(final Function<I, E> fct) {
        return i -> Tuple.of(i, fct.apply(i));
    }

}