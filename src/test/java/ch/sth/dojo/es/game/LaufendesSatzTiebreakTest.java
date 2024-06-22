package ch.sth.dojo.es.game;

import static ch.sth.dojo.es.game.LaufendesSatzTiebreak.laufendesSatzTiebreak;
import static org.assertj.core.api.Assertions.assertThat;

import io.vavr.control.Option;
import org.junit.jupiter.api.Test;

class LaufendesSatzTiebreakTest {

    @Test
    void erstelleValid_cases() {
        assertThat(laufendesSatzTiebreak(0, 0).isDefined()).isTrue();
        assertThat(laufendesSatzTiebreak(-1, 0).isEmpty()).isTrue();
        assertThat(laufendesSatzTiebreak(6, 7).isDefined()).isTrue();
        assertThat(laufendesSatzTiebreak(7, 6).isDefined()).isTrue();
        assertThat(laufendesSatzTiebreak(8, 6).isEmpty()).isTrue();
        assertThat(laufendesSatzTiebreak(6, 8).isEmpty()).isTrue();
        assertThat(laufendesSatzTiebreak(9, 8).isDefined()).isTrue();
        assertThat(laufendesSatzTiebreak(10, 8).isEmpty()).isTrue();
        assertThat(laufendesSatzTiebreak(11, 8).isEmpty()).isTrue();
    }

    @Test
    void scoreSpielerTiebreakGewonnen_ok() {
        final LaufendesSatzTiebreak zero = LaufendesSatzTiebreak.zero();
        final Option<SatzTiebreak> satzTiebreaks = SatzTiebreak.incrementSpieler(zero)
                .flatMap(SatzTiebreak::incrementSpieler)
                .flatMap(SatzTiebreak::incrementSpieler)
                .flatMap(SatzTiebreak::incrementSpieler)
                .flatMap(SatzTiebreak::incrementSpieler)
                .flatMap(SatzTiebreak::incrementSpieler)
                .flatMap(SatzTiebreak::incrementSpieler);

        assertThat(satzTiebreaks.isDefined()).isTrue();
        assertThat(satzTiebreaks.get()).isInstanceOf(AbgeschlossenesSatzTiebreak.class);
    }
}