package ch.sth.dojo.es.cmd;

import static ch.sth.dojo.es.Routing.selective2SplitEither;
import static ch.sth.dojo.es.cmd.GameCommandHandler.invalidCommandForState;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.events.GameErzeugt;
import ch.sth.dojo.es.evt.GameEventHandler;
import ch.sth.dojo.es.game.Game;
import ch.sth.dojo.es.game.LaufendesGame;
import ch.sth.dojo.es.game.PreInitializedGame;
import io.vavr.control.Either;
import java.util.function.Function;

public interface GameCommandHandler {
    static Function<Game, Either<DomainError, DomainEvent>> handleSpielerPunktet() {
        return prev -> GameEventHandler.apply(prev,
                laufendesGameHandleSpielerPunktet(),
                invalidCommandForState("handleSpielerPunktet"),
                invalidCommandForState("handleSpielerPunktet")
        );
    }

    static Function<Game, Either<DomainError, DomainEvent>> handleGegnerPunktet() {
        return game -> GameEventHandler.apply(game,
                laufendesGameHandleGegnerPunktet(),
                invalidCommandForState("handleGegnerPunktet"),
                invalidCommandForState("handleGegnerPunktet")
        );
    }

    static Function<LaufendesGame, Either<DomainError, DomainEvent>> laufendesGameHandleGegnerPunktet() {
        return in -> selective2SplitEither(in,
                Game.passIfGegnerSize4(),
                Laufend2AbgeschlossenCommandHandler.gegnerGewinneGame(),
                Laufend2LaufendCommandHandler.gegnerGewinnePunkt()
        );
    }

    static Function<LaufendesGame, Either<DomainError, DomainEvent>> laufendesGameHandleSpielerPunktet() {
        return in -> selective2SplitEither(in,
                Game.passIfSpielerSize4(),
                Laufend2AbgeschlossenCommandHandler.spielerGewinneGame(),
                Laufend2LaufendCommandHandler.spielerGewinnePunkt());
    }

    static <G extends Game> Function<G, Either<DomainError, DomainEvent>> invalidCommandForState(final String commandAsString) {
        return prev -> invalidCommandForState(prev, commandAsString);
    }

    static Either<DomainError, DomainEvent> invalidCommandForState(final Game prev, final String commandAsString) {
        return Either.left(new DomainError.InvalidCommandForGame(prev, commandAsString));
    }

    static Function<PreInitializedGame, GameErzeugt> erzeugeGame() {
        return Pre2LaufendCommandHandler.erzeugeGame();
    }
}
