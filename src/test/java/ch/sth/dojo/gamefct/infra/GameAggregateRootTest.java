package ch.sth.dojo.gamefct.infra;

import static ch.sth.dojo.gamefct.GameAggregateRoot.gegnerPunktet;
import static ch.sth.dojo.gamefct.GameAggregateRoot.spielerPunktet;
import static org.assertj.core.api.Assertions.assertThat;

import ch.sth.dojo.gamefct.Game;
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
    @Test
    void gegnerPunktet_notequal_prev() {
        Game prev = Game.initial();
        assertThat(gegnerPunktet(prev)).isNotEqualTo(prev);
    }
    @Test
    void gegnerPunktet_equal_empty_gegnerPunktet() {
        Game prev1 = Game.initial();
        Game prev2 = Game.initial();
        assertThat(gegnerPunktet(prev1)).isEqualTo(gegnerPunktet(prev2));
    }
}