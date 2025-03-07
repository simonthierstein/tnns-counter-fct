package ch.sth.dojo.beh.noadgame.evt;

import static ch.sth.dojo.beh.noadgame.TestParsingUtils.parseEvent;
import static ch.sth.dojo.beh.noadgame.TestParsingUtils.parseTennisToNoAdGame;
import ch.sth.dojo.beh.noadgame.domain.NoAdGame;
import io.vavr.Tuple;
import java.util.function.Function;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class NoAdGameEventHandlerTest {

    @ParameterizedTest
    @CsvSource(
        {
            "00-00,SpielerPunktGewonnen,1,0",
            "15-00,SpielerPunktGewonnen,2,0",
            "30-00,SpielerPunktGewonnen,3,0",
            "40-00,SpielerPunktGewonnen,4,0",
            "00-00,GegnerPunktGewonnen,0,1",
            "00-15,GegnerPunktGewonnen,0,2",
            "00-30,GegnerPunktGewonnen,0,3",
            "00-40,GegnerPunktGewonnen,0,4",
            "40-40,SpielerPunktGewonnen,4,3",
            "40-40,GegnerPunktGewonnen,3,4",
        }
    )
    void handleEvent(String score, String event, Integer expectedSpieler, Integer expectedGegner) {
        var fixture = Tuple.of(score, event, expectedSpieler, expectedGegner)
            .map(parseTennisToNoAdGame(), parseEvent(), Function.identity(), Function.identity());

        var res = NoAdGameEventHandler.handleEvent(fixture._1, fixture._2);

        assertThat(res).isEqualTo(NoAdGame.of(expectedSpieler, expectedGegner));

    }

}

