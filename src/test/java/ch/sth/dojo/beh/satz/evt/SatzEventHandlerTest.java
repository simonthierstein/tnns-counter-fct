package ch.sth.dojo.beh.satz.evt;

import static org.assertj.core.api.Assertions.assertThat;

import ch.sth.dojo.beh.satz.domain.Satz;
import ch.sth.dojo.beh.satz.domain.GegnerPunkteSatz;
import ch.sth.dojo.beh.satz.domain.LaufenderSatz;
import ch.sth.dojo.beh.satz.domain.SpielerPunkteSatz;
import ch.sth.dojo.beh.evt.GegnerGameGewonnen;
import ch.sth.dojo.beh.evt.GegnerSatzGewonnen;
import ch.sth.dojo.beh.evt.SpielerGameGewonnen;
import ch.sth.dojo.beh.evt.SpielerSatzGewonnen;
import io.vavr.collection.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;

class SatzEventHandlerTest {

    @DisplayName("Gamewin Scoring 😎")
    @ParameterizedTest
    @ArgumentsSource(SatzScoreProvider.class)
    void handleGegnerGameGewonnenEvent(Integer left, Integer right) {
        var prev = new LaufenderSatz(new SpielerPunkteSatz(left), new GegnerPunkteSatz(right));
        var res = SatzEventHandler.handleEvent(prev, new GegnerGameGewonnen());

        assertThat(res.isRight()).isTrue();

    }

    @DisplayName("Gamewin Scoring 😎")
    @ParameterizedTest
    @ArgumentsSource(SatzScoreProvider.class)
    void handleSpielerGameGewonnenEvent(Integer left, Integer right) {
        var prev = new LaufenderSatz(new SpielerPunkteSatz(left), new GegnerPunkteSatz(right));
        var res = SatzEventHandler.handleEvent(prev, new SpielerGameGewonnen());

        assertThat(res.isRight()).isTrue();

    }

    @Test
    void handleSpielerGameGewonnenEvent5_5() {
        var prev = new LaufenderSatz(new SpielerPunkteSatz(5), new GegnerPunkteSatz(5));
        var res = SatzEventHandler.handleEvent(prev, new SpielerGameGewonnen());

        assertThat(res.isRight()).isTrue();
        assertThat(res.get()).isInstanceOf(LaufenderSatz.class);
        assertThat(res
            .map(LaufenderSatz.class::cast)
            .get())
            .isEqualTo(new LaufenderSatz(new SpielerPunkteSatz(6), new GegnerPunkteSatz(5)));
    }

    @Test
    void handleGegnerGameGewonnenEvent5_5() {
        var prev = new LaufenderSatz(new SpielerPunkteSatz(5), new GegnerPunkteSatz(5));
        var res = SatzEventHandler.handleEvent(prev, new GegnerGameGewonnen());

        assertThat(res.isRight()).isTrue();
        assertThat(res.get()).isInstanceOf(LaufenderSatz.class);
        assertThat(res
            .map(LaufenderSatz.class::cast)
            .get())
            .isEqualTo(new LaufenderSatz(new SpielerPunkteSatz(5), new GegnerPunkteSatz(6)));
    }

    @DisplayName("Spieler Satz gewonnen: {0} - {1}")
    @ParameterizedTest
    @CsvSource({
        "5,0",
        "5,1",
        "5,2",
        "5,3",
        "5,4",
        "6,5",
        "6,6"})
    void handleSpielerSatzGewonnenEvent(Integer left, Integer right) {
        var prev = new LaufenderSatz(new SpielerPunkteSatz(left), new GegnerPunkteSatz(right));
        var res = SatzEventHandler.handleEvent(prev, new SpielerSatzGewonnen());

        assertThat(res.isRight()).isTrue();
        assertThat(res.get())
            .isInstanceOf(LaufenderSatz.class)
            .isEqualTo(Satz.zero());
    }

    @DisplayName("Gegner Satz gewonnen: {0} - {1}")
    @ParameterizedTest
    @CsvSource({
        "5,0",
        "5,1",
        "5,2",
        "5,3",
        "5,4",
        "6,5",
        "6,6"})
    void handleGegnerSatzGewonnenEvent(Integer left, Integer right) {
        var prev = new LaufenderSatz(new SpielerPunkteSatz(right), new GegnerPunkteSatz(left));
        var res = SatzEventHandler.handleEvent(prev, new GegnerSatzGewonnen());

        assertThat(res.isRight()).isTrue();
        assertThat(res.get())
            .isInstanceOf(LaufenderSatz.class)
            .isEqualTo(Satz.zero());
    }

}

class SatzScoreProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        final List<Integer> toFiveRange = List.range(0, 5);
        return toFiveRange
            .flatMap(i -> toFiveRange.map(j -> Arguments.of(i, j))).toJavaStream();
    }
}