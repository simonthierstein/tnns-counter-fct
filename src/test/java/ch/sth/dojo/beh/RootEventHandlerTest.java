package ch.sth.dojo.beh;

import static org.assertj.core.api.Assertions.assertThat;

import ch.sth.dojo.beh.cgame.domain.AbgeschlossenesCGame;
import ch.sth.dojo.beh.cgame.domain.CGame;
import ch.sth.dojo.beh.cgame.domain.LaufendesCGame;
import ch.sth.dojo.beh.cmatch.domain.CMatch;
import ch.sth.dojo.beh.cmatch.domain.CMatch.LaufendesMatch;
import ch.sth.dojo.beh.csatz.domain.AbgeschlossenerCSatz;
import ch.sth.dojo.beh.csatz.domain.CSatz;
import ch.sth.dojo.beh.csatz.domain.GegnerPunkteSatz;
import ch.sth.dojo.beh.csatz.domain.LaufenderCSatz;
import ch.sth.dojo.beh.csatz.domain.SpielerPunkteSatz;
import ch.sth.dojo.beh.evt.GegnerSatzGewonnen;
import ch.sth.dojo.beh.evt.SpielerPunktGewonnen;
import ch.sth.dojo.beh.evt.SpielerSatzGewonnen;
import io.vavr.Tuple;
import io.vavr.Tuple3;
import org.junit.jupiter.api.Test;

class RootEventHandlerTest {

    @Test
    void spielerPunktGewonnen_success() {
        var res = RootEventHandler.handleEvent(inputState(2, 2), new SpielerPunktGewonnen());

        assertThat(res.isRight())
            .withFailMessage("invalid result %s", res)
            .isTrue();

    }

    @Test
    void gegnerSatzGewonnen_success() {

        var res = RootEventHandler.handleEvent(inputState(2, 5), new GegnerSatzGewonnen());

        assertThat(res.isRight())
            .withFailMessage("invalid result %s", res)
            .isTrue();
        assertThat(res.get()._1).isInstanceOf(AbgeschlossenerCSatz.class);
        assertThat(res.get()._2).isInstanceOf(AbgeschlossenesCGame.class);

    }

    @Test
    void spielerSatzGewonnen_success() {

        var res = RootEventHandler.handleEvent(inputState(5, 2), new SpielerSatzGewonnen());

        assertThat(res.isRight())
            .withFailMessage("invalid result %s", res)
            .isTrue();
        assertThat(res.get()._1).isInstanceOf(AbgeschlossenerCSatz.class);
        assertThat(res.get()._2).isInstanceOf(AbgeschlossenesCGame.class);

    }

    private static Tuple3<CMatch, CSatz, CGame> inputState(final int spielerPunkteSatz, final int gegnerPunkteSatz) {
        return Tuple.of(new LaufendesMatch(), new LaufenderCSatz(new SpielerPunkteSatz(spielerPunkteSatz), new GegnerPunkteSatz(gegnerPunkteSatz)),
            LaufendesCGame.zero());
    }
}