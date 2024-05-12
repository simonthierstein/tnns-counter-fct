package ch.sth.dojo.es.game;

import static ch.sth.dojo.es.game.Punkt.punkt;
import static ch.sth.dojo.es.game.trans.Routing.selective2Split;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.events.GameErzeugt;
import ch.sth.dojo.es.game.trans.ErrorHandling;
import ch.sth.dojo.es.game.trans.Laufend2Abgeschlossen;
import ch.sth.dojo.es.game.trans.Laufend2Laufend;
import ch.sth.dojo.es.game.trans.Pre2Laufend;
import io.vavr.Function2;
import io.vavr.collection.List;
import io.vavr.control.Either;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Game {

    static Function<PreInitializedGame, GameErzeugt> erzeugeGame() {
        return Pre2Laufend.erzeugeGame();
    }

    static Function2<Game, DomainEvent, Either<DomainError, Game>> handleEvent() {
        return Game::handleEvent;
    }

    static Either<DomainError, Game> handleEvent(Game state, DomainEvent event) {
        return Game.apply(state,
                laufendesGame -> handleLaufendesGame(laufendesGame, event),
                abgeschlossenesGame -> handleAbgeschlossenesGame(abgeschlossenesGame, event),
                preInitializedGame -> handlePreInitializedGame(preInitializedGame, event));
    }

    static Function<Game, Either<DomainError, DomainEvent>> handleSpielerPunktet() {
        return Game::handleSpielerPunktet;
    }

    private static Either<DomainError, DomainEvent> handleSpielerPunktet(Game prev) {
        return apply(prev,
//                Laufend2X.handleSpielerPunktet(),
                laufendesGameHandleSpielerPunktet(),
                ErrorHandling.handleSpielerPunktet(),
                ErrorHandling.handleSpielerPunktet()
        );
    }

    private static Function<LaufendesGame, Either<DomainError, DomainEvent>> laufendesGameHandleSpielerPunktet() {
        return in -> selective2Split(in,
                passIfSize4(),
                Laufend2Abgeschlossen.spielerGewinneGame(),
                Laufend2Laufend.spielerGewinnePunkt());
    }

    //    static Function<Game, Either<DomainError, DomainEvent>> handleGegnerPunktet() {
//        return game -> apply(game,
//
//
//
//                )
//
//    }

    private static <T> T apply(Game game, Function<LaufendesGame, T> laufendesGameTFunction, Function<AbgeschlossenesGame, T> abgeschlossenesGameTFunction,
                               Function<PreInitializedGame, T> preInitializedGameTFunction) {
        return Match(game).of(
                Case($(instanceOf(LaufendesGame.class)), laufendesGameTFunction),
                Case($(instanceOf(AbgeschlossenesGame.class)), abgeschlossenesGameTFunction),
                Case($(instanceOf(PreInitializedGame.class)), preInitializedGameTFunction)
        );
    }

    private static Either<DomainError, Game> handlePreInitializedGame(PreInitializedGame preInitializedGame, DomainEvent event) {
        return DomainEvent.handleEvent(event,
                left(eventToError(preInitializedGame)),
                left(eventToError(preInitializedGame)),
                left(eventToError(preInitializedGame)),
                left(eventToError(preInitializedGame)),
                right(Pre2Laufend.gameErzeugt())
        );
    }

    private static Either<DomainError, Game> handleAbgeschlossenesGame(AbgeschlossenesGame state, DomainEvent event) {
        return Either.left(new DomainError.InvalidEventForState(state, event));
    }

    private static Either<DomainError, Game> handleLaufendesGame(final LaufendesGame laufendesGame, final DomainEvent event) {
        return DomainEvent.handleEvent(event,
                right(Laufend2Laufend.shpg(laufendesGame)),
                right(Laufend2Laufend.ghpg(laufendesGame)),
                right(Laufend2Abgeschlossen.shgg(laufendesGame)),
                right(Laufend2Abgeschlossen.ghgg(laufendesGame)),
                left(eventToError(laufendesGame)));
    }

    private static <I extends DomainEvent, L extends DomainError, R extends Game> Function<I, Either<L, Game>> right(Function<I, R> inputFunction) {
        return i -> Either.<L, I>right(i).map(inputFunction);
    }

    private static <I extends DomainEvent, L extends DomainError, R extends Game> Function<I, Either<DomainError, R>> left(Function<I, L> inputFunction) {
        return i -> Either.<I, R>left(i).mapLeft(inputFunction);
    }

    private static <E extends DomainEvent> Function<E, DomainError> eventToError(Game state) {
        return event -> new DomainError.InvalidEventForState(state, event);
    }

    static Predicate<LaufendesGame> passIfSize4() {
        return prev -> isSize4(prev.punkteSpieler.append(punkt()));
    }

    static boolean isSize4(final List<Punkt> incremented) {
        return incremented.size() == 4;
    }

    static DomainEvent doGegnerPunktet(LaufendesGame prev) {
        final List<Punkt> incremented = prev.punkteGegner.append(punkt());
        return incremented.size() == 4
                ? Laufend2Abgeschlossen.GegnerHatGameGewonnen(prev.punkteSpieler, incremented)
                : Laufend2Laufend.GegnerHatPunktGewonnen(prev.punkteSpieler, incremented);
    }
}
