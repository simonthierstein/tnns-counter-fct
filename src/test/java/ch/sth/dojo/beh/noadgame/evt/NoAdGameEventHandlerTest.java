package ch.sth.dojo.beh.noadgame.evt;

import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.GegnerPunktGewonnen;
import ch.sth.dojo.beh.evt.SpielerPunktGewonnen;
import ch.sth.dojo.beh.noadgame.domain.NoAdGame;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import io.vavr.Tuple;
import io.vavr.collection.List;
import java.util.Arrays;
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
            .map(parseTennisToCommandDomain(), parseEvent(), Function.identity(), Function.identity());

        var res = NoAdGameEventHandler.handleEvent(fixture._1, fixture._2);

        assertThat(res).isEqualTo(NoAdGame.of(expectedSpieler, expectedGegner));

    }

    private Function<String, DomainEvent> parseEvent() {
        return str -> Match(str).of(
            Case($("SpielerPunktGewonnen"), new SpielerPunktGewonnen()),
            Case($("GegnerPunktGewonnen"), new GegnerPunktGewonnen())
        );
    }

    private DomainEvent event() {
        return null;
    }

    private NoAdGame state() {
        return null;
    }

    private static Function<String, NoAdGame> parseTennisToCommandDomain() {
        return tennisStr -> List.ofAll(Arrays.stream(tennisStr.split("-")))
            .map(parseTennisToNumber())
            .foldLeft(Tuple.of(0, 0), (acc, it) -> acc.update1(it).swap()).apply((sp, ge) ->
                NoAdGame.of(sp, ge).get());
    }

    private static Function<String, Integer> parseTennisToNumber() {
        return str -> Match(str).of(
            Case($("00"), 0),
            Case($("15"), 1),
            Case($("30"), 2),
            Case($("40"), 3),
            Case($("GAME"), 4)
        );
    }
}