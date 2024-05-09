package ch.sth.dojo.gamefct.infra;

import static ch.sth.dojo.es.Game.eventHandler;

import ch.sth.dojo.es.AbgeschlossenesGame;
import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.Game;
import ch.sth.dojo.es.GameAggregateRoot;
import ch.sth.dojo.es.InvalidCommandForState;
import ch.sth.dojo.es.PreInitializedGame;
import ch.sth.dojo.es.commands.SpielerPunktet;
import ch.sth.dojo.es.events.DomainEvent;
import io.vavr.collection.List;
import io.vavr.control.Either;
import java.util.function.Function;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;

class ESGameAggregateRootTest {


    @Test
    void fdsafdsa() {

        final List<DomainEvent> fdsaf = doSpielerPunktet(doSpielerPunktet(doSpielerPunktet(doSpielerPunktet(List.empty()))));
        System.out.println(fdsaf);

        final List<DomainEvent> d = List.range(0, 4)
                .foldLeft(List.empty(), (acc, elem) -> doSpielerPunktet(acc));

        System.out.println(d);

        List<DomainEvent> xx = List.range(0, 4)
                .map(x -> spielerCommand())
                .foldLeft(List.empty(), (xs, x) -> x.apply(xs));

        System.out.println(xx);



        Function<List<DomainEvent>, List<DomainEvent>> fct=List.range(0, 4)
                .map(x -> spielerCommand())
                .foldLeft(Function.identity(), (xs, x) -> x.compose(xs));

        System.out.println(fct.apply(List.empty()));

    }


    static Function<List<DomainEvent>, List<DomainEvent>> spielerCommand() {
        return ESGameAggregateRootTest::doSpielerPunktet;
    }


    private static List<DomainEvent> doSpielerPunktet(List<DomainEvent> events) {
        final Game target = eventHandler(events);
        final SpielerPunktet command = new SpielerPunktet();
        final Either<DomainError, DomainEvent> domainEvents = Game.commandHandler(target, command, errSup(target, command));
        return domainEvents.map(domainEvent -> events.append(domainEvent))
                .getOrElseThrow(err -> new RuntimeException(err.toString()));
    }

    private static Supplier<DomainError> errSup(final Game state, final SpielerPunktet command) {
        return () -> new InvalidCommandForState(state, command);
    }

    private static Function<AbgeschlossenesGame, DomainEvent> err_Ab() {
        return ESGameAggregateRootTest::doErr;
    }

    private static DomainEvent doErr(final Game x) {
        System.err.println("Fehler!!" + x);
        return null;
    }

    private static Function<PreInitializedGame, DomainEvent> err_Pre() {
        return ESGameAggregateRootTest::doErr;
    }
}