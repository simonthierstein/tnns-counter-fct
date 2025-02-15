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

class CSatzEventHandlerTest {

    @DisplayName("Gamewin Scoring 😎")
    @ParameterizedTest
    @ArgumentsSource(SatzScoreProvider.class)
    void handleGegnerGameGewonnenEvent(Integer left, Integer right) {
        var prev = new LaufenderCSatz(new SpielerPunkteSatz(left), new GegnerPunkteSatz(right), LaufendesCGame.zero());
        var res = CSatzEventHandler.handleEvent(prev, new GegnerGameGewonnen());

        assertThat(res.isRight()).isTrue();

    }

    @DisplayName("Gamewin Scoring 😎")
    @ParameterizedTest
    @ArgumentsSource(SatzScoreProvider.class)
    void handleSpielerGameGewonnenEvent(Integer left, Integer right) {
        var prev = new LaufenderCSatz(new SpielerPunkteSatz(left), new GegnerPunkteSatz(right), LaufendesCGame.zero());
        var res = CSatzEventHandler.handleEvent(prev, new SpielerGameGewonnen());

        assertThat(res.isRight()).isTrue();

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