package ch.sth.dojo.es.game;

import static ch.sth.dojo.es.Unit.Unit;
import static ch.sth.dojo.es.game.PreInitializedGame.PreInitializedGame;
import static ch.sth.dojo.es.game.PreInitializedGame.erzeugeGame;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import ch.sth.dojo.es.events.GameErzeugt;
import io.vavr.control.Option;
import java.util.function.Function;
import org.junit.jupiter.api.Test;

class PreInitializedGameTest {


    @Test
    void name() {

        final GameErzeugt gameErzeugt = Option.some(Unit())
                .map(PreInitializedGame())
                .map(erzeugeGame())
                .get();

        assertThat(gameErzeugt).isNotNull();
    }
}