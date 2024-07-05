package ch.sth.dojo.es.satztiebreak.evt;

import static ch.sth.dojo.es.events.SpielerHatPunktGewonnen.spielerHatPunktGewonnen;
import static ch.sth.dojo.es.satztiebreak.evt.LaufendesSatzTiebreakEventHandler.handle;
import static org.assertj.core.api.Assertions.assertThat;

import ch.sth.dojo.es.satztiebreak.LaufendesSatzTiebreak;
import ch.sth.dojo.es.satztiebreak.SatzTiebreak;
import io.vavr.control.Option;
import org.junit.jupiter.api.Test;

class LaufendesSatzTiebreakEventHandlerTest {

    @Test
    void spielerHatPunktGewonnen_ok() {

        final Option<SatzTiebreak> result = handle(LaufendesSatzTiebreak.zero(), spielerHatPunktGewonnen());
        assertThat(result.isDefined()).isTrue();

    }

    @Test
    void fdsa() {

        var result = SatzTiebreakEventHandler.handleEvent(LaufendesSatzTiebreak.zero(), spielerHatPunktGewonnen())
                .flatMap(state -> SatzTiebreakEventHandler.handleEvent(state, spielerHatPunktGewonnen()))
                .flatMap(state -> SatzTiebreakEventHandler.handleEvent(state, spielerHatPunktGewonnen()))
                .flatMap(state -> SatzTiebreakEventHandler.handleEvent(state, spielerHatPunktGewonnen()))
                .flatMap(state -> SatzTiebreakEventHandler.handleEvent(state, spielerHatPunktGewonnen()))
                .flatMap(state -> SatzTiebreakEventHandler.handleEvent(state, spielerHatPunktGewonnen()))
                .flatMap(state -> SatzTiebreakEventHandler.handleEvent(state, spielerHatPunktGewonnen()));


        assertThat(result.isDefined()).isTrue();
        assertThat(result.flatMap(state -> SatzTiebreakEventHandler.handleEvent(state, spielerHatPunktGewonnen())).isEmpty()).isTrue();

    }
}