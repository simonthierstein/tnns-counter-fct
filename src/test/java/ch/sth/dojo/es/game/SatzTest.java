/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.game;

import static org.assertj.core.api.Assertions.assertThat;

import ch.sth.dojo.es.events.GegnerHatGameGewonnen;
import ch.sth.dojo.es.events.SpielerHatGameGewonnen;
import ch.sth.dojo.es.satz.Satz;
import org.junit.jupiter.api.Test;

class SatzTest {
    @Test
    void spielerHatGameGewonnen_isNotNull() {
        assertThat(Satz.handleEvent(Satz.zero(), create4_2_Event())).isNotNull();
    }

    @Test
    void spielerHatGameGewonnen_isNotEqualTo_zero() {
        assertThat(Satz.handleEvent(Satz.zero(), create4_2_Event())).isNotEqualTo(Satz.zero());
    }

    @Test
    void gegnerHatGameGewonnen_isNotNull() {
        assertThat(Satz.handleEvent(Satz.zero(), create2_4_Event())).isNotNull();
    }

    @Test
    void gegnerHatGameGewonnen_isNotEqualTo_zero() {
        assertThat(Satz.handleEvent(Satz.zero(), create2_4_Event())).isNotEqualTo(Satz.zero());
    }

    @Test
    void gegnerHatGameGewonnen_isNotEqualTo_spielerHatGameGewonnen() {
        assertThat(Satz.handleEvent(Satz.zero(), create2_4_Event()))
                .isNotEqualTo(Satz.handleEvent(Satz.zero(), create4_2_Event()));
    }

    private static GegnerHatGameGewonnen create2_4_Event() {
        return new GegnerHatGameGewonnen();
    }

    private static SpielerHatGameGewonnen create4_2_Event() {
        return new SpielerHatGameGewonnen(4, 2);
    }
}
