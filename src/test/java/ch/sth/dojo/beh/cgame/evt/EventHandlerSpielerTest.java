package ch.sth.dojo.beh.cgame.evt;

import static org.assertj.core.api.Assertions.assertThat;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cgame.domain.CGame;
import ch.sth.dojo.beh.cgame.domain.GegnerPunkteBisGame;
import ch.sth.dojo.beh.cgame.domain.LaufendesCGame;
import ch.sth.dojo.beh.cgame.domain.SpielerPunkteBisGame;
import ch.sth.dojo.beh.evt.SpielerGameGewonnen;
import ch.sth.dojo.beh.evt.SpielerPunktGewonnen;
import io.vavr.control.Either;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class EventHandlerSpielerTest {

    @DisplayName("Scoring 😎")
    @ParameterizedTest(name = "spieler punktet {0} - {1}")
    @CsvSource({"4,4", "4,3", "4,2", "5,1",
        "3,4", "3,3", "3,2", "4,1",
        "2,4", "2,3"
    })
    void handleEvent(Integer spielerValue, Integer gegnerValue) {
        final Either<DomainProblem, CGame> cGames = EventHandler.handleEvent(createStandardState(spielerValue, gegnerValue), new SpielerPunktGewonnen());

        assertThat(cGames.isRight()).isTrue();
        assertThat(cGames.map(LaufendesCGame.class::cast))
            .allMatch(x -> x.spielerPunkteBisGame().value().equals(spielerValue - 1))
            .allMatch(x -> x.gegnerPunkteBisGame().value().equals(gegnerValue));
    }

    @DisplayName("CloseGamewin Scoring 😎")
    @ParameterizedTest(name = "spieler punktet {0} - {1}")
    @CsvSource({
        "2,2,1,3", "3,1,2,2"
    })
    void punktGewonnenInfluenced(Integer spielerValue, Integer gegnerValue, Integer expSpielerValue, Integer expGegnerValue) {
        final Either<DomainProblem, CGame> cGames = EventHandler.handleEvent(createStandardState(spielerValue, gegnerValue), new SpielerPunktGewonnen());

        assertThat(cGames.isRight()).isTrue();
        assertThat(cGames.map(LaufendesCGame.class::cast))
            .allMatch(x -> x.spielerPunkteBisGame().value().equals(expSpielerValue))
            .allMatch(x -> x.gegnerPunkteBisGame().value().equals(expGegnerValue));
    }

    @DisplayName("Gamewin Scoring 😎")
    @ParameterizedTest(name = "spieler gewinnt game {0} - {1}")
    @CsvSource({
        "1,5",
        "1,4",
        "1,3"
    })
    void gameGewonnen(Integer spielerValue, Integer gegnerValue) {
        final Either<DomainProblem, CGame> cGames = EventHandler.handleEvent(createStandardState(spielerValue, gegnerValue), new SpielerGameGewonnen());

        assertThat(cGames.isRight()).isTrue();
        assertThat(cGames.map(LaufendesCGame.class::cast))
            .allMatch(x -> x.spielerPunkteBisGame().value().equals(0))
            .allMatch(x -> x.gegnerPunkteBisGame().value().equals(gegnerValue));
    }

    @DisplayName("Wrong Event Punkt Gewonnen on Game Gewonnen 😎")
    @ParameterizedTest(name = "spieler gewinnt game {0} - {1}")
    @CsvSource({
        "1,5",
        "1,4",
        "1,3"
    })
    void gameGewonnenWrongEvent(Integer spielerValue, Integer gegnerValue) {
        final Either<DomainProblem, CGame> cGames = EventHandler.handleEvent(createStandardState(spielerValue, gegnerValue), new SpielerPunktGewonnen());

        assertThat(cGames.isRight()).isFalse();
    }

    private static LaufendesCGame createStandardState(final int spielerValue, final int gegnerValue) {
        return new LaufendesCGame(new SpielerPunkteBisGame(spielerValue), new GegnerPunkteBisGame(gegnerValue));
    }
}