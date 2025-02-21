package ch.sth.dojo.beh.cgame.evt;

import static org.assertj.core.api.Assertions.assertThat;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cgame.domain.AbgeschlossenesCGame;
import ch.sth.dojo.beh.cgame.domain.CGame;
import ch.sth.dojo.beh.cgame.domain.GegnerPunkteBisGame;
import ch.sth.dojo.beh.cgame.domain.LaufendesCGame;
import ch.sth.dojo.beh.cgame.domain.SpielerPunkteBisGame;
import ch.sth.dojo.beh.evt.GegnerGameGewonnen;
import ch.sth.dojo.beh.evt.GegnerPunktGewonnen;
import ch.sth.dojo.beh.evt.SpielerPunktGewonnen;
import io.vavr.control.Either;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CGameEventHandlerGegnerTest {

    @DisplayName("Scoring 😎")
    @ParameterizedTest(name = "gegner punktet {0} - {1}")
    @CsvSource({"4,4", "4,3", "4,2", "5,1",
        "3,4", "3,3", "3,2", "4,1",
        "2,4", "2,3"
    })
    void handleEvent(Integer gegnerValue, Integer spielerValue) {
        final Either<DomainProblem, CGame> cGames = GegnerEventHandler.handleGegnerEvent(createStandardState(spielerValue, gegnerValue), new GegnerPunktGewonnen());

        assertThat(cGames.isRight()).isTrue();
        assertThat(cGames.map(LaufendesCGame.class::cast))
            .allMatch(x -> x.spielerPunkteBisGame().value().equals(spielerValue))
            .allMatch(x -> x.gegnerPunkteBisGame().value().equals(gegnerValue - 1));
    }

    @DisplayName("CloseGamewin Scoring 😎")
    @ParameterizedTest(name = "spieler punktet {0} - {1}")
    @CsvSource({
        "2,2,1,3", "3,1,2,2"
    })
    void punktGewonnenInfluenced(Integer gegnerValue, Integer spielerValue, Integer expGegnerValue, Integer expSpielerValue) {
        final Either<DomainProblem, CGame> cGames = GegnerEventHandler.handleGegnerEvent(createStandardState(spielerValue, gegnerValue), new GegnerPunktGewonnen());

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
    void gameGewonnen(Integer gegnerValue, Integer spielerValue) {
        final Either<DomainProblem, CGame> cGames = GegnerEventHandler.handleGegnerEvent(createStandardState(spielerValue, gegnerValue), new GegnerGameGewonnen());

        assertThat(cGames.isRight()).isTrue();
        assertThat(cGames.get()).isInstanceOf(AbgeschlossenesCGame.class);
    }

    @DisplayName("Wrong Event Punkt Gewonnen on Game Gewonnen 😎")
    @ParameterizedTest(name = "spieler gewinnt game {0} - {1}")
    @CsvSource({
        "1,5",
        "1,4",
        "1,3"
    })
    void gameGewonnenWrongEvent(Integer gegnerValue, Integer spielerValue) {
        final Either<DomainProblem, CGame> cGames = GegnerEventHandler.handleGegnerEvent(createStandardState(spielerValue, gegnerValue), new GegnerPunktGewonnen());

        assertThat(cGames.isRight()).isFalse();
    }

    @DisplayName("Handle not allowed Event on finished game 😎")
    @ParameterizedTest(name = "finished game {0} - {1}")
    @CsvSource({
        "0,5",
        "0,4",
        "0,3",
        "5,0",
        "4,0",
        "3,0",
    })
    void gameGewonnenNotAllowedEvent(Integer gegnerValue, Integer spielerValue) {
        final Either<DomainProblem, CGame> cGames = GegnerEventHandler.handleGegnerEvent(createStandardState(spielerValue, gegnerValue), new GegnerPunktGewonnen());

        assertThat(cGames.isRight()).isFalse();
        assertThat(cGames.getLeft()).isEqualTo(DomainProblem.eventNotValid);
    }

    @DisplayName("Handle not allowed Event on finished game 😎")
    @ParameterizedTest(name = "finished game {0} - {1}")
    @CsvSource({
        "0,5",
        "0,4",
        "0,3",
        "5,0",
        "4,0",
        "3,0",
    })
    void gameGewonnenNotAllowedEvent2(Integer gegnerValue, Integer spielerValue) {
        final Either<DomainProblem, CGame> cGames = CGameEventHandler.handleEvent(createStandardState(spielerValue, gegnerValue), new SpielerPunktGewonnen());

        assertThat(cGames.isRight()).isFalse();
        assertThat(cGames.getLeft()).isEqualTo(DomainProblem.eventNotValid);
    }

    private static LaufendesCGame createStandardState(final int spielerValue, final int gegnerValue) {
        return new LaufendesCGame(new SpielerPunkteBisGame(spielerValue), new GegnerPunkteBisGame(gegnerValue));
    }
}