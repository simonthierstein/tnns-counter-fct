package ch.sth.dojo.gamefct;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class GameAggregateRootTest {

    @Test
    void spielerPunktet_notequel_prev() {
        Game prev = Game.initial();
        assertThat(GameAggregateRoot.spielerPunktet(prev)).isNotEqualTo(prev);
    }
}