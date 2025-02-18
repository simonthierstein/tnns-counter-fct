package ch.sth.dojo.beh;

import static org.assertj.core.api.Assertions.assertThat;

import ch.sth.dojo.beh.cgame.domain.CGame;
import ch.sth.dojo.beh.cgame.domain.LaufendesCGame;
import ch.sth.dojo.beh.csatz.domain.CSatz;
import ch.sth.dojo.beh.csatz.domain.GegnerPunkteSatz;
import ch.sth.dojo.beh.csatz.domain.LaufenderCSatz;
import ch.sth.dojo.beh.csatz.domain.SpielerPunkteSatz;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.SpielerPunktGewonnen;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.junit.jupiter.api.Test;

class RootEventHandlerTest {

    @Test
    void name() {
        var res = RootEventHandler.handleEvent(inputState(), inputEvent());

        assertThat(res.isRight())
            .withFailMessage("invalid result %s", res)
            .isTrue();

    }

    private static DomainEvent inputEvent() {
        return new SpielerPunktGewonnen();
    }

    private static Tuple2<CSatz, CGame> inputState() {
        return Tuple.of(new LaufenderCSatz(new SpielerPunkteSatz(2), new GegnerPunkteSatz(2)),
            LaufendesCGame.zero());
    }
}