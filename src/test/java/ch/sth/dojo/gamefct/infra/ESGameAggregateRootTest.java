package ch.sth.dojo.gamefct.infra;

import ch.sth.dojo.es.GameAggregateRoot;
import ch.sth.dojo.es.events.DomainEvent;
import org.junit.jupiter.api.Test;

class ESGameAggregateRootTest {


    @Test
    void fdsafdsa() {
        var game = GameAggregateRoot.initial();

        System.out.println(GameAggregateRoot.spielerPunktet(game));
        System.out.println(GameAggregateRoot.spielerPunktet(game));
        System.out.println(GameAggregateRoot.spielerPunktet(game));
        System.out.println(GameAggregateRoot.spielerPunktet(game));
        System.out.println(GameAggregateRoot.spielerPunktet(game));

    }
}