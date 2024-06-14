package ch.sth.dojo.es.game;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class LaufendesSatzTiebreakTest {

    @Test
    void fdsa() {
        assertThat(LaufendesSatzTiebreak.laufendesSatzTiebreak(0, 0).isDefined()).isTrue();
        assertThat(LaufendesSatzTiebreak.laufendesSatzTiebreak(-1, 0).isEmpty()).isTrue();
        assertThat(LaufendesSatzTiebreak.laufendesSatzTiebreak(6, 7).isDefined()).isTrue();
        assertThat(LaufendesSatzTiebreak.laufendesSatzTiebreak(7, 6).isDefined()).isTrue();
        assertThat(LaufendesSatzTiebreak.laufendesSatzTiebreak(8, 6).isEmpty()).isTrue();
        assertThat(LaufendesSatzTiebreak.laufendesSatzTiebreak(6, 8).isEmpty()).isTrue();
        assertThat(LaufendesSatzTiebreak.laufendesSatzTiebreak(9, 8).isDefined()).isTrue();
        assertThat(LaufendesSatzTiebreak.laufendesSatzTiebreak(10, 8).isEmpty()).isTrue();
        assertThat(LaufendesSatzTiebreak.laufendesSatzTiebreak(11, 8).isEmpty()).isTrue();
    }


}