package ch.sth.dojo.es.game;

import ch.sth.dojo.es.game.satztiebreak.AbgeschlossenesSatzTiebreak;
import org.junit.jupiter.api.Test;

class AbgeschlossenesSatzTiebreakTest {


    @Test
    void fdsa() {
        assertThat(AbgeschlossenesSatzTiebreak.abgeschlossenesSatzTiebreak(0, 0).isEmpty()).isTrue();
        assertThat(AbgeschlossenesSatzTiebreak.abgeschlossenesSatzTiebreak(-1, 0).isEmpty()).isTrue();
        assertThat(AbgeschlossenesSatzTiebreak.abgeschlossenesSatzTiebreak(6, 7).isEmpty()).isTrue();
        assertThat(AbgeschlossenesSatzTiebreak.abgeschlossenesSatzTiebreak(7, 6).isEmpty()).isTrue();
        assertThat(AbgeschlossenesSatzTiebreak.abgeschlossenesSatzTiebreak(8, 6).isDefined()).isTrue();
        assertThat(AbgeschlossenesSatzTiebreak.abgeschlossenesSatzTiebreak(6, 8).isDefined()).isTrue();
        assertThat(AbgeschlossenesSatzTiebreak.abgeschlossenesSatzTiebreak(9, 8).isEmpty()).isTrue();
        assertThat(AbgeschlossenesSatzTiebreak.abgeschlossenesSatzTiebreak(10, 8).isDefined()).isTrue();
        assertThat(AbgeschlossenesSatzTiebreak.abgeschlossenesSatzTiebreak(11, 8).isEmpty()).isTrue();
        assertThat(AbgeschlossenesSatzTiebreak.abgeschlossenesSatzTiebreak(7, 0).isDefined()).isTrue();
    }
}