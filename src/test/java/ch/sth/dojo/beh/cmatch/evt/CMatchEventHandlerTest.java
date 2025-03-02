package ch.sth.dojo.beh.cmatch.evt;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cmatch.domain.CMatch;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.GegnerGameGewonnen;
import ch.sth.dojo.beh.evt.GegnerMatchGewonnen;
import ch.sth.dojo.beh.evt.GegnerPunktGewonnen;
import ch.sth.dojo.beh.evt.GegnerSatzGewonnen;
import ch.sth.dojo.beh.evt.SpielerGameGewonnen;
import ch.sth.dojo.beh.evt.SpielerMatchGewonnen;
import ch.sth.dojo.beh.evt.SpielerPunktGewonnen;
import ch.sth.dojo.beh.evt.SpielerSatzGewonnen;
import io.vavr.control.Either;
import java.util.function.Function;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CMatchEventHandlerTest {

    @ParameterizedTest
    @ValueSource(strings = {
        "SpielerGameGewonnen",
        "SpielerPunktGewonnen",
        //        "GegnerGameGewonnen",
        //        "GegnerPunktGewonnen"
    })
    void handleSpielerEvent_gameGewonnen(String inputEventString) {
        final CMatch inputState = CMatch.zero();
        final CMatch expectedState = CMatch.zero();

        Either.<DomainProblem, String>right(inputEventString)
            .map(stringToEvent())
            .flatMap(evt -> CMatchEventHandler.handleEvent(inputState, evt))
            .fold(err -> fail(err.toString()), succ -> assertThat(succ).isEqualTo(expectedState));
    }

    private static Function<String, DomainEvent> stringToEvent() {
        return str -> Match(str).of(
            Case($("SpielerPunktGewonnen"), new SpielerPunktGewonnen()),
            Case($("SpielerGameGewonnen"), new SpielerGameGewonnen()),
            Case($("SpielerSatzGewonnen"), new SpielerSatzGewonnen()),
            Case($("SpielerMatchGewonnen"), new SpielerMatchGewonnen()),
            Case($("GegnerPunktGewonnen"), new GegnerPunktGewonnen()),
            Case($("GegnerGameGewonnen"), new GegnerGameGewonnen()),
            Case($("GegnerSatzGewonnen"), new GegnerSatzGewonnen()),
            Case($("GegnerMatchGewonnen"), new GegnerMatchGewonnen())
        );
    }
}