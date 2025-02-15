package ch.sth.dojo.beh.csatz.evt;

import static org.assertj.core.api.Assertions.assertThat;

import ch.sth.dojo.beh.cgame.domain.LaufendesCGame;
import ch.sth.dojo.beh.csatz.domain.GegnerPunkteSatz;
import ch.sth.dojo.beh.csatz.domain.LaufenderCSatz;
import ch.sth.dojo.beh.csatz.domain.SpielerPunkteSatz;
import ch.sth.dojo.beh.evt.GegnerGameGewonnen;
import ch.sth.dojo.beh.evt.SpielerGameGewonnen;
import io.vavr.collection.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;

class CSatzEventHandlerTest {

    @DisplayName("Gamewin Scoring 😎")
    @ParameterizedTest(name = "Satz handling spieler gewinnt game {0} - {1}")
    @CsvSource({
        "0,4", "0,3", "0,2", "0,1", "0,0",
        "1,4", "1,3", "1,2", "1,1", "1,0",
        "2,4", "2,3", "2,2", "2,1", "2,0",
        "3,4", "3,3", "3,2", "3,1", "3,0",
        "4,4", "4,3", "4,2", "4,1", "4,0"
    })
    void handleSpielerGameGewonnenEvent(Integer left, Integer right) {
        var prev = new LaufenderCSatz(new SpielerPunkteSatz(left), new GegnerPunkteSatz(right), LaufendesCGame.zero());
        var fdsa = CSatzEventHandler.handleEvent(prev, new SpielerGameGewonnen());

        assertThat(fdsa.isRight()).isTrue();
    }

    @DisplayName("Gamewin Scoring 😎")
    @ParameterizedTest(name = "Satz handling spieler gewinnt game {0} - {1}")
    @CsvSource({
        "0,4", "0,3", "0,2", "0,1", "0,0",
        "1,4", "1,3", "1,2", "1,1", "1,0",
        "2,4", "2,3", "2,2", "2,1", "2,0",
        "3,4", "3,3", "3,2", "3,1", "3,0",
        "4,4", "4,3", "4,2", "4,1", "4,0"
    })
    void handleGegnerGameGewonnenEvent(Integer left, Integer right) {
        var prev = new LaufenderCSatz(new SpielerPunkteSatz(right), new GegnerPunkteSatz(left), LaufendesCGame.zero());
        var fdsa = CSatzEventHandler.handleEvent(prev, new GegnerGameGewonnen());

        assertThat(fdsa.isRight()).isTrue();
    }

    @DisplayName("Gamewin Scoring 😎")
    @ParameterizedTest
    @ArgumentsSource(SatzScoreProvider.class)
    void handleEventfds(Integer left, Integer right) {
        var prev = new LaufenderCSatz(new SpielerPunkteSatz(left), new GegnerPunkteSatz(right), LaufendesCGame.zero());
        var fdsa = CSatzEventHandler.handleEvent(prev, new SpielerGameGewonnen());
    }

}

class SatzScoreProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        final List<Integer> range = List.range(0, 25);

        //        range.forEach(System.out::println);

        return Stream.of(
            Arguments.of(1, 1)
        );
    }
}