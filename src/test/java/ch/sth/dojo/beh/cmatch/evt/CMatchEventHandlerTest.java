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
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class CMatchEventHandlerTest {

    @ParameterizedTest
    @ValueSource(strings = {
        "SpielerGameGewonnen",
        "SpielerPunktGewonnen",
        //        "GegnerGameGewonnen",
        //        "GegnerPunktGewonnen"
    })
    void handleSpielerEvent_PunktOrGameGewonnen(String inputEventString) {
        final CMatch inputState = CMatch.zero();
        final CMatch expectedState = CMatch.zero();

        Either.<DomainProblem, String>right(inputEventString)
            .map(stringToEvent())
            .flatMap(evt -> CMatchEventHandler.handleEvent(inputState, evt))
            .fold(err -> fail(err.toString()), succ -> assertThat(succ).isEqualTo(expectedState));
    }

    @ParameterizedTest
    @CsvSource({
        "SpielerSatzGewonnen,0,0,1,0",
        "SpielerSatzGewonnen,0,1,1,1",
    })
    void handleSpielerEvent_SatzGewonnen(String inputEventString, Integer inputSpielerSatzScore, Integer inputGegnerSatzScoreInteger, Integer expectedSpielerSatzScore,
        Integer expectedGegnerSatzScore) {
        final CMatch inputState = CMatch.of(inputSpielerSatzScore, inputGegnerSatzScoreInteger).get();
        final CMatch expectedState = CMatch.of(expectedSpielerSatzScore, expectedGegnerSatzScore).get();

        Either.<DomainProblem, String>right(inputEventString)
            .map(stringToEvent())
            .flatMap(evt -> CMatchEventHandler.handleEvent(inputState, evt))
            .fold(err -> fail(err.toString()), succ -> assertThat(succ).isEqualTo(expectedState));
    }

    @ParameterizedTest
    @CsvSource({
        "SpielerMatchGewonnen,1,0,2,0",
        "SpielerMatchGewonnen,1,1,2,1",
    })
    void handleSpielerEvent_MatchGewonnen(String inputEventString, Integer inputSpielerSatzScore, Integer inputGegnerSatzScoreInteger, Integer expectedSpielerSatzScore,
        Integer expectedGegnerSatzScore) {
        final CMatch inputState = CMatch.of(inputSpielerSatzScore, inputGegnerSatzScoreInteger).get();
        final CMatch expectedState = CMatch.of(expectedSpielerSatzScore, expectedGegnerSatzScore).get();

        Either.<DomainProblem, String>right(inputEventString)
            .map(stringToEvent())
            .flatMap(evt -> CMatchEventHandler.handleEvent(inputState, evt))
            .fold(err -> fail(err.toString()), succ -> assertThat(succ).isEqualTo(expectedState));
    }

    @ParameterizedTest
    @ValueSource(
        strings = {"SpielerMatchGewonnen", "GegnerMatchGewonnen"}
    )
    void handleSpielerEvent_MatchGewonnen_invalidevent(String event) {
        final CMatch inputState = CMatch.zero();
        final DomainEvent inputEvent = parseEvent(event);
        final DomainProblem expectedError = DomainProblem.eventNotValid;

        CMatchEventHandler.handleEvent(inputState, inputEvent)
            .fold(
                err -> assertThat(err).isEqualTo(expectedError),
                succ -> fail("Expected error but was %s", succ)
            );
    }

    private static DomainEvent parseEvent(final String eventString) {
        return switch (eventString) {
            case "GegnerMatchGewonnen" -> new GegnerMatchGewonnen();
            case "SpielerMatchGewonnen" -> new SpielerMatchGewonnen();
            default -> throw new RuntimeException();
        };
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