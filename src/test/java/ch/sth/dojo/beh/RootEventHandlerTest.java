package ch.sth.dojo.beh;

import static org.assertj.core.api.Assertions.assertThat;

import ch.sth.dojo.beh.cgame.domain.AbgeschlossenesCGame;
import ch.sth.dojo.beh.cgame.domain.CGame;
import ch.sth.dojo.beh.cgame.domain.LaufendesCGame;
import ch.sth.dojo.beh.csatz.domain.AbgeschlossenerCSatz;
import ch.sth.dojo.beh.csatz.domain.CSatz;
import ch.sth.dojo.beh.csatz.domain.GegnerPunkteSatz;
import ch.sth.dojo.beh.csatz.domain.LaufenderCSatz;
import ch.sth.dojo.beh.csatz.domain.SpielerPunkteSatz;
import ch.sth.dojo.beh.evt.GegnerSatzGewonnen;
import ch.sth.dojo.beh.evt.SpielerPunktGewonnen;
import ch.sth.dojo.beh.evt.SpielerSatzGewonnen;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.junit.jupiter.api.Test;

class RootEventHandlerTest {

    @Test
    void spielerPunktGewonnen_success() {
        var res = RootEventHandler.handleEvent(inputState(), new SpielerPunktGewonnen());

        assertThat(res.isRight())
            .withFailMessage("invalid result %s", res)
            .isTrue();

    }

    @Test
    void gegnerSatzGewonnen_success() {
        var res = RootEventHandler.handleEvent(inputStateSatz2_5(), new GegnerSatzGewonnen());

        assertThat(res.isRight())
            .withFailMessage("invalid result %s", res)
            .isTrue();
        assertThat(res.get()._1).isInstanceOf(AbgeschlossenerCSatz.class);
        assertThat(res.get()._2).isInstanceOf(AbgeschlossenesCGame.class);

    }

    @Test
    void spielerSatzGewonnen_success() {
        var res = RootEventHandler.handleEvent(inputStateSatz5_2(), new SpielerSatzGewonnen());

        assertThat(res.isRight())
            .withFailMessage("invalid result %s", res)
            .isTrue();
        assertThat(res.get()._1).isInstanceOf(AbgeschlossenerCSatz.class);
        assertThat(res.get()._2).isInstanceOf(AbgeschlossenesCGame.class);

    }

    private Tuple2<CSatz, CGame> inputStateSatz2_5() {
        return Tuple.of(new LaufenderCSatz(new SpielerPunkteSatz(2), new GegnerPunkteSatz(5)),
            LaufendesCGame.zero());

    }

    private Tuple2<CSatz, CGame> inputStateSatz5_2() {
        return Tuple.of(new LaufenderCSatz(new SpielerPunkteSatz(5), new GegnerPunkteSatz(2)),
            LaufendesCGame.zero());

    }

    private static Tuple2<CSatz, CGame> inputState() {
        return Tuple.of(new LaufenderCSatz(new SpielerPunkteSatz(2), new GegnerPunkteSatz(2)),
            LaufendesCGame.zero());
    }
}