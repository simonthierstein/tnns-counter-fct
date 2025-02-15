package ch.sth.dojo.beh.csatz.evt;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CSatzEventHandlerTest {

    @DisplayName("Gamewin Scoring 😎")
    @ParameterizedTest(name = "Satz handling spieler gewinnt game {0} - {1}")
    @CsvSource({
        "1,5",
        "1,4",
        "1,3"
    })
    void handleEvent(Integer left, Integer right) {
        System.out.printf("%d, %d%n", left, right);

        fail();

    }
}