/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.game;

import static org.assertj.core.api.Assertions.assertThat;

import ch.sth.dojo.es.satz.Satz;
import org.junit.jupiter.api.Test;

class SatzTest {
    @Test
    void spielerHatGameGewonnen_isNotNull() {
        assertThat(Satz.spielerHatGameGewonnen(Satz.zero())).isNotNull();
    }

    @Test
    void spielerHatGameGewonnen_isNotEqualTo_zero() {
        assertThat(Satz.spielerHatGameGewonnen(Satz.zero())).isNotEqualTo(Satz.zero());
    }

    @Test
    void gegnerHatGameGewonnen_isNotNull() {
        assertThat(Satz.gegnerHatGameGewonnen(Satz.zero())).isNotNull();
    }

    @Test
    void gegnerHatGameGewonnen_isNotEqualTo_zero() {
        assertThat(Satz.gegnerHatGameGewonnen(Satz.zero())).isNotEqualTo(Satz.zero());
    }

    @Test
    void gegnerHatGameGewonnen_isNotEqualTo_spielerHatGameGewonnen() {
        assertThat(Satz.gegnerHatGameGewonnen(Satz.zero()))
                .isNotEqualTo(Satz.spielerHatGameGewonnen(Satz.zero()));
    }
}
