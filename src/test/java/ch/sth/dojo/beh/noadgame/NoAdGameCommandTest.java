package ch.sth.dojo.beh.noadgame;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.evt.DomainEvent;
import static ch.sth.dojo.beh.noadgame.TestParsingUtils.parseEvent;
import static ch.sth.dojo.beh.noadgame.TestParsingUtils.parseTennisToNoAdGame;
import io.vavr.Tuple;
import io.vavr.control.Either;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class NoAdGameCommandTest {

    @ParameterizedTest
    @CsvSource({
        "00-00,GegnerPunktGewonnen",
        "00-15,GegnerPunktGewonnen",
        "00-30,GegnerPunktGewonnen",
        "00-40,GegnerGameGewonnen",
        "40-40,GegnerGameGewonnen",
    })
    void gegnerGewinntPunkt(String score, String event) {
        var fixture = Tuple.of(score, event)
            .map(parseTennisToNoAdGame(), parseEvent());

        final Either<DomainProblem, DomainEvent> apply = fixture.apply((state, x) -> NoAdGameCommand.gegnerGewinntPunkt(state));

        assertThat(apply).isEqualTo(Either.right(fixture._2));
    }

    @ParameterizedTest
    @CsvSource({
        "00-00,SpielerPunktGewonnen",
        "15-00,SpielerPunktGewonnen",
        "30-00,SpielerPunktGewonnen",
        "40-00,SpielerGameGewonnen",
        "40-40,SpielerGameGewonnen",
    })
    void spielerGewinntPunkt(String score, String event) {
        var fixture = Tuple.of(score, event)
            .map(parseTennisToNoAdGame(), parseEvent());

        final Either<DomainProblem, DomainEvent> apply = fixture.apply((state, x) -> NoAdGameCommand.spielerGewinntPunkt(state));

        assertThat(apply).isEqualTo(Either.right(fixture._2));
    }
}