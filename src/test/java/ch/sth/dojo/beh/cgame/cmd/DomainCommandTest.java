package ch.sth.dojo.beh.cgame.cmd;

import static org.assertj.core.api.Assertions.assertThat;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.cgame.domain.GegnerPunkteBisGame;
import ch.sth.dojo.beh.cgame.domain.LaufendesCGame;
import ch.sth.dojo.beh.cgame.domain.SpielerPunkteBisGame;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.GegnerGameGewonnen;
import ch.sth.dojo.beh.evt.GegnerPunktGewonnen;
import ch.sth.dojo.beh.evt.SpielerGameGewonnen;
import ch.sth.dojo.beh.evt.SpielerPunktGewonnen;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;

class DomainCommandTest {

    @Test
    void spielerPunktet() {
        final Either<DomainProblem, DomainEvent> domainEvents = DomainCommand.handleCommand(LaufendesCGame.zero(), createSpielerPunktet());

        assertThat(domainEvents.isRight()).isTrue();
        assertThat(domainEvents.get()).isInstanceOf(SpielerPunktGewonnen.class);
    }

    @Test
    void spielerPunktetGame() {
        final Either<DomainProblem, DomainEvent> domainEvents = DomainCommand.handleCommand(new LaufendesCGame(new SpielerPunkteBisGame(1), new GegnerPunkteBisGame(5)), createSpielerPunktet());

        assertThat(domainEvents.isRight()).isTrue();
        assertThat(domainEvents.get()).isInstanceOf(SpielerGameGewonnen.class);
    }

    @Test
    void gegnerPunktet() {
        final Either<DomainProblem, DomainEvent> domainEvents = DomainCommand.handleCommand(LaufendesCGame.zero(), createGegnerPunktet());

        assertThat(domainEvents.isRight()).isTrue();
        assertThat(domainEvents.get()).isInstanceOf(GegnerPunktGewonnen.class);
    }

    @Test
    void gegnerPunktetGame() {
        final Either<DomainProblem, DomainEvent> domainEvents = DomainCommand.handleCommand(new LaufendesCGame(new SpielerPunkteBisGame(3), new GegnerPunkteBisGame(1)), createGegnerPunktet());

        assertThat(domainEvents.isRight()).isTrue();
        assertThat(domainEvents.get()).isInstanceOf(GegnerGameGewonnen.class);
    }

    private DomainCommand createGegnerPunktet() {
        return new GegnerPunktet();
    }

    private static DomainCommand createSpielerPunktet() {
        return new SpielerPunktet();
    }
}