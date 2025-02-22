package ch.sth.dojo.beh.cgame.cmd;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.RootEventHandler;
import ch.sth.dojo.beh.cgame.domain.AbgeschlossenesCGame;
import ch.sth.dojo.beh.cgame.domain.CGame;
import ch.sth.dojo.beh.cgame.domain.GegnerPunkteBisGame;
import ch.sth.dojo.beh.cgame.domain.LaufendesCGame;
import ch.sth.dojo.beh.cgame.domain.SpielerPunkteBisGame;
import ch.sth.dojo.beh.csatz.domain.CSatz;
import ch.sth.dojo.beh.csatz.domain.LaufenderCSatz;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.GegnerGameGewonnen;
import ch.sth.dojo.beh.evt.GegnerPunktGewonnen;
import ch.sth.dojo.beh.evt.SpielerGameGewonnen;
import ch.sth.dojo.beh.evt.SpielerPunktGewonnen;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ScenarioTest {

    @ParameterizedTest
    @ValueSource(strings = {"00", "15", "30"})
    void spielerGewinntGame_standard(String other) {

        final Tuple2<CSatz, CGame> prev = SatzGameTupleWith();
        final DomainCommand command = DomainCommandWith();

        final Either<DomainProblem, Tuple2<CSatz, CGame>> result = List.range(0, 4)
            .foldLeft(Either.right(prev),
                (acc, it) ->
                    acc.flatMap(prevState ->
                        DomainCommand.handleCommand(prevState, command).flatMap(evt -> RootEventHandler.handleEvent(prevState, evt))));

        assertThat(result.isRight()).isTrue();
        result.forEach(t -> assertAll(
            () -> assertThat(t._1).isInstanceOf(LaufenderCSatz.class),
            () -> assertThat(t._2).isInstanceOf(AbgeschlossenesCGame.class),
            () -> assertThat(t._2).isEqualTo(new AbgeschlossenesCGame())
        ));

        System.out.println(other);

    }

    private CGame LaufendesCGameWith(final int spieler, final int gegner) {
        return CGame.of(spieler, gegner).get();
    }

    private static Tuple2<CSatz, CGame> SatzGameTupleWith() {
        return Tuple.of(CSatz.zero(), CGame.zero());
    }

    private static DomainCommand DomainCommandWith() {
        return new SpielerPunktet();
    }

    @Test
    void gegnerGewinntGame_standard() {
    }

    @Test
    void spielerGewinntGame_deuce() {
    }

    @Test
    void gegnerGewinntGame_deuce() {
    }

    @Test
    void spielerGewinntSatz_standard() {
    }

    @Test
    void gegnerGewinntSatz_standard() {
    }

    @Test
    void spielerPunktet() {
        final Either<DomainProblem, DomainEvent> domainEvents = DomainCommand.handleCommand(zeroGame(), createSpielerPunktet());

        assertThat(domainEvents.isRight()).isTrue();
        assertThat(domainEvents.get()).isInstanceOf(SpielerPunktGewonnen.class);
    }

    private static Tuple2<CSatz, CGame> zeroGame() {
        return Tuple.of(LaufenderCSatz.zero(), LaufendesCGame.zero());
    }

    @Test
    void spielerPunktetGame() {
        final Either<DomainProblem, DomainEvent> domainEvents = DomainCommand.handleCommand(laufendesGameWith(1, 5), createSpielerPunktet());

        assertThat(domainEvents.isRight()).isTrue();
        assertThat(domainEvents.get()).isInstanceOf(SpielerGameGewonnen.class);
    }

    private static Tuple2<CSatz, CGame> laufendesGameWith(final int spielerValue, final int gegnerValue) {
        return Tuple.of(LaufenderCSatz.zero(), new LaufendesCGame(new SpielerPunkteBisGame(spielerValue), new GegnerPunkteBisGame(gegnerValue)));
    }

    @Test
    void gegnerPunktet() {
        final Either<DomainProblem, DomainEvent> domainEvents = DomainCommand.handleCommand(zeroGame(), createGegnerPunktet());

        assertThat(domainEvents.isRight()).isTrue();
        assertThat(domainEvents.get()).isInstanceOf(GegnerPunktGewonnen.class);
    }

    @Test
    void gegnerPunktetGame() {
        final Either<DomainProblem, DomainEvent> domainEvents = DomainCommand.handleCommand(laufendesGameWith(3, 1), createGegnerPunktet());

        assertThat(domainEvents.isRight()).isTrue();
        assertThat(domainEvents.get()).isInstanceOf(GegnerGameGewonnen.class);
    }

    private static DomainCommand createGegnerPunktet() {
        return new GegnerPunktet();
    }

    private static DomainCommand createSpielerPunktet() {
        return new SpielerPunktet();
    }
}