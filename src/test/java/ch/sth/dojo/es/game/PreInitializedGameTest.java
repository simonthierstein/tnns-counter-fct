package ch.sth.dojo.es.game;

import static ch.sth.dojo.es.Unit.Unit;
import static ch.sth.dojo.es.game.trans.Unit2Pre.createEmpty;
import static ch.sth.dojo.es.game.trans.Pre2Laufend.erzeugeGame;
import static org.assertj.core.api.Assertions.assertThat;

import ch.sth.dojo.es.events.GameErzeugt;
import io.vavr.control.Option;
import org.junit.jupiter.api.Test;

class PreInitializedGameTest {


    @Test
    void name() {

        final GameErzeugt gameErzeugt = Option.some(Unit())
                .map(createEmpty())
                .map(erzeugeGame())
                .get();

        assertThat(gameErzeugt).isNotNull();
    }
}