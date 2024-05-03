package ch.sth.dojo.gamefct;

import static ch.sth.dojo.gamefct.GameAggregateRoot.spielerPunktet;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class GameAggregateRootTest {

    @Test
    void spielerPunktet_notequal_prev() {
        Game prev = Game.initial();
        assertThat(spielerPunktet(prev)).isNotEqualTo(prev);
    }
    @Test
    void spielerPunktet_equal_empty_spielerPunktet() {
        Game prev1 = Game.initial();
        Game prev2 = Game.initial();
        assertThat(spielerPunktet(prev1)).isEqualTo(spielerPunktet(prev2));
    }
}