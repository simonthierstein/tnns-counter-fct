package ch.sth.dojo.gamefct.infra;

import static ch.sth.dojo.es.Game.eventHandler;

import ch.sth.dojo.es.AbgeschlossenesGame;
import ch.sth.dojo.es.Game;
import ch.sth.dojo.es.GameAggregateRoot;
import ch.sth.dojo.es.LaufendesGame;
import ch.sth.dojo.es.events.DomainEvent;
import io.vavr.collection.List;
import java.util.function.Function;
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
                .map(x -> spielerPunktetFct())
                .foldLeft(List.empty(), (xs, x) -> x.apply(xs));

        System.out.println(xx);



        Function<List<DomainEvent>, List<DomainEvent>> fct=List.range(0, 4)
                .map(x -> spielerPunktetFct())
                .foldLeft(Function.identity(), (xs, x) -> x.compose(xs));

        System.out.println(fct.apply(List.empty()));

    }


    @Test
    void fdafdasfdasfs() {
        doSpielerPunktet_(GameAggregateRoot.initial(), laufendesGame -> GameAggregateRoot.spielerPunktet(laufendesGame));
    }

    static Function<List<DomainEvent>, List<DomainEvent>> spielerPunktetFct() {
        return ESGameAggregateRootTest::doSpielerPunktet;
    }


    private static DomainEvent doSpielerPunktet_(Game state, final Function<LaufendesGame, DomainEvent> command) {
        return Game.apply(state, command, err_());
    }


    private static List<DomainEvent> doSpielerPunktet(List<DomainEvent> events) {
        return events.append(Game.apply(eventHandler(events), GameAggregateRoot::spielerPunktet, err_()));
    }

    private static Function<AbgeschlossenesGame, DomainEvent> err_() {
        return x-> {
            System.err.println("Fehler!!" + x);
            return null;
        };
    }
}