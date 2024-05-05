package ch.sth.dojo.es;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

import ch.sth.dojo.es.events.DomainEvent;
import io.vavr.collection.List;
import java.util.function.Function;

public interface Game {

    static <T> T apply(Game game, Function<LaufendesGame, T> laufendesGameTFunction, Function<AbgeschlossenesGame, T> abgeschlossenesGameTFunction) {
        return Match(game).of(
                Case($(instanceOf(LaufendesGame.class)), laufendesGameTFunction),
                Case($(instanceOf(AbgeschlossenesGame.class)), abgeschlossenesGameTFunction)
        );
    }
    
    
    static Game eventHandler(List<DomainEvent> domainEvents) {
        final Game initial = LaufendesGame.initial();

        Game state = domainEvents.foldLeft(initial, (acc, elem) -> acc.handleEvent(elem));

        return state;

    }

    Game handleEvent(DomainEvent elem);
}
