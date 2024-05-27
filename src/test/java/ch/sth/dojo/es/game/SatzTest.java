/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.game;

import static org.assertj.core.api.Assertions.assertThat;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.cmd.satz.SatzCommandHandler;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.events.GegnerHatGameGewonnen;
import ch.sth.dojo.es.events.GegnerHatSatzGewonnen;
import ch.sth.dojo.es.events.SpielerHatGameGewonnen;
import ch.sth.dojo.es.events.SpielerHatSatzGewonnen;
import ch.sth.dojo.es.evt.satz.SatzEventHandler;
import ch.sth.dojo.es.satz.AbgeschlossenerSatz;
import ch.sth.dojo.es.satz.LaufenderSatz;
import ch.sth.dojo.es.satz.Satz;
import io.vavr.collection.List;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;

class SatzTest {
    @Test
    void spielerHatGameGewonnen_isNotNull() {
        assertThat(SatzEventHandler.handleEvent(Satz.zero(), create4_2_Event())).isNotNull();
    }

    @Test
    void spielerHatGameGewonnen_isNotEqualTo_zero() {
        assertThat(SatzEventHandler.handleEvent(Satz.zero(), create4_2_Event())).isNotEqualTo(Satz.zero());
    }

    @Test
    void gegnerHatGameGewonnen_isNotNull() {
        assertThat(SatzEventHandler.handleEvent(Satz.zero(), create2_4_Event())).isNotNull();
    }

    @Test
    void gegnerHatGameGewonnen_isNotEqualTo_zero() {
        assertThat(SatzEventHandler.handleEvent(Satz.zero(), create2_4_Event())).isNotEqualTo(Satz.zero());
    }

    @Test
    void gegnerHatGameGewonnen_isNotEqualTo_spielerHatGameGewonnen() {
        assertThat(SatzEventHandler.handleEvent(Satz.zero(), create2_4_Event()))
                .isNotEqualTo(SatzEventHandler.handleEvent(Satz.zero(), create4_2_Event()));
    }

    @Test
    void handleCommand_create4_2_Event_isright() {
        final Either<DomainError, DomainEvent> actual = SatzCommandHandler.handleCommand(Satz.zero(), create4_2_Event());

        assertThat(actual.isRight()).isTrue();
        assertThat(actual.get()).isEqualTo(create4_2_Event());
    }

    @Test
    void handleCommand_create2_4_Event_isright() {
        final Either<DomainError, DomainEvent> actual = SatzCommandHandler.handleCommand(Satz.zero(), create2_4_Event());

        assertThat(actual.isRight()).isTrue();
        assertThat(actual.get()).isEqualTo(create2_4_Event());
    }


    @Test
    void handleCommand_spielerSatzGewonnen_Event_isright() {
        final LaufenderSatz state = new LaufenderSatz(List.of(Punkt.punkt(), Punkt.punkt(), Punkt.punkt(), Punkt.punkt(), Punkt.punkt()), List.of());
        final Either<DomainError, DomainEvent> actual = SatzCommandHandler.handleCommand(state, create4_2_Event());
        assertThat(actual.get()).isInstanceOf(SpielerHatSatzGewonnen.class);

        final Either<DomainError, Satz> satzs = SatzEventHandler.handleEvent(state, actual.get());

        assertThat(satzs.isRight()).isTrue();
        assertThat(satzs.get()).isInstanceOf(AbgeschlossenerSatz.class);
    }

    @Test
    void handleCommand_gegnerSatzGewonnen_Event_isright() {
        final LaufenderSatz state = new LaufenderSatz(List.empty(), List.of(Punkt.punkt(), Punkt.punkt(), Punkt.punkt(), Punkt.punkt(), Punkt.punkt()));
        final Either<DomainError, DomainEvent> actual = SatzCommandHandler.handleCommand(state, create2_4_Event());
        assertThat(actual.get()).isInstanceOf(GegnerHatSatzGewonnen.class);

        final Either<DomainError, Satz> satzs = SatzEventHandler.handleEvent(state, actual.get());

        assertThat(satzs.isRight()).isTrue();
        assertThat(satzs.get()).isInstanceOf(AbgeschlossenerSatz.class);
    }



    private static GegnerHatGameGewonnen create2_4_Event() {
        return new GegnerHatGameGewonnen();
    }

    private static SpielerHatGameGewonnen create4_2_Event() {
        return new SpielerHatGameGewonnen(4, 2);
    }
}
