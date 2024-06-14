package ch.sth.dojo.es.scoring.evt;

import static ch.sth.dojo.es.match.LaufendesStandardMatch.laufendesStandardMatch;
import static ch.sth.dojo.es.scoring.CurrentGame.currentGame;
import static ch.sth.dojo.es.scoring.CurrentSatz.currentSatz;
import static ch.sth.dojo.es.scoring.StandardScoring.standardScoring;
import static org.assertj.core.api.Assertions.assertThat;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.events.GegnerHatGameGewonnen;
import ch.sth.dojo.es.events.GegnerHatSatzGewonnen;
import ch.sth.dojo.es.events.SpielerHatGameGewonnen;
import ch.sth.dojo.es.events.SpielerHatMatchGewonnen;
import ch.sth.dojo.es.game.AbgeschlossenesGame;
import ch.sth.dojo.es.game.LaufendesGame;
import ch.sth.dojo.es.match.AbgeschlossenesStandardMatch;
import ch.sth.dojo.es.match.LaufendesStandardMatch;
import ch.sth.dojo.es.match.PunkteGegner;
import ch.sth.dojo.es.match.PunkteSpieler;
import ch.sth.dojo.es.satz.AbgeschlossenerSatz;
import ch.sth.dojo.es.satz.LaufenderSatz;
import ch.sth.dojo.es.satz.Satz;
import ch.sth.dojo.es.scoring.StandardScoring;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;

class StandardScoringEventHandler2Test {

    @Test
    void handleMatchGewonnenSpieler() {
        final Either<DomainError, StandardScoring> result = StandardScoringEventHandler.handleEvent(preSpielerGewinntMatchState(),
                spielerHatMatchGewonnenEvent());

        assertThat(result)
                .isNotNull()
                .isInstanceOf(Either.Right.class)
                .isNotEqualTo(Either.right(scoringZero()));

        assertThat(result.get())
                .isNotNull()
                .isEqualTo(spielerMatchGewonnenState());
    }


    @Test
    void handleSpielerHatGameGewonnen_ok() {
        final Either<DomainError, StandardScoring> standardScorings = StandardScoringEventHandler.handleEvent(preSpielerGewinntGameState(),
                spielerHatGameGewonnenEvent());

        standardScorings
                .peek(standardScoring -> assertThat(standardScoring.currentGame().current()).isInstanceOf(AbgeschlossenesGame.class)
                        .isEqualTo(AbgeschlossenesGame.fromInteger(4, 0).get()))
                .peek(standardScoring -> assertThat(standardScoring.currentSatz().current()).isEqualTo(LaufenderSatz.fromInteger(1, 0).get()));

        assertThat(standardScorings.isRight()).isTrue();

    }

    @Test
    void handleGegnerHatGameGewonnen_ok() {
        final Either<DomainError, StandardScoring> standardScorings = StandardScoringEventHandler.handleEvent(preGegnerGewinntGameState(),
                gegnerHatGameGewonnenEvent());

        standardScorings
                .peek(standardScoring -> assertThat(standardScoring.currentGame().current()).isInstanceOf(AbgeschlossenesGame.class)
                        .isEqualTo(AbgeschlossenesGame.fromInteger(0, 4).get()))
                .peek(standardScoring -> assertThat(standardScoring.currentSatz().current()).isEqualTo(LaufenderSatz.fromInteger(0, 1).get()));

        assertThat(standardScorings.isRight()).isTrue();

    }

    @Test
    void handleGegnerHatSatzGewonnen_ok() {
        final Either<DomainError, StandardScoring> standardScorings = StandardScoringEventHandler.handleEvent(preGegnerGewinntSatzState(),
                gegnerHatSatzGewonnenEvent());

        standardScorings
                .peek(standardScoring -> assertThat(standardScoring.currentGame().current()).isInstanceOf(AbgeschlossenesGame.class)
                        .isEqualTo(AbgeschlossenesGame.fromInteger(0, 4).get()))
                .peek(standardScoring -> assertThat(standardScoring.currentSatz().current()).isEqualTo(AbgeschlossenerSatz.fromInteger(4, 6).get()))
                .peek(standardScoring -> assertThat(standardScoring.match()).isEqualTo(matchSpieler0Gegner1()));

        assertThat(standardScorings.isRight()).isTrue();

    }

    @Test
    void handleTransitionToTiebreak_ok() {
        final Either<DomainError, StandardScoring> standardScorings = StandardScoringEventHandler.handleEvent(preTiebreakState(),
                spielerHatGameGewonnenEvent());

        assertThat(standardScorings.isRight()).isTrue();
    }

    private StandardScoring preTiebreakState() {
        return standardScoring(currentGame(LaufendesGame.laufendesGame(spieler40(), gegner0()).get()),
                currentSatz(LaufenderSatz.fromInteger(5, 6).get()),
                anyMatchState());
    }

    private static StandardScoring preGegnerGewinntSatzState() {
        return standardScoring(currentGame(LaufendesGame.laufendesGame(0, 3).get()),
                currentSatz(LaufenderSatz.fromInteger(4, 5).get()),
                matchSpieler0Gegner0());
    }

    private static DomainEvent gegnerHatSatzGewonnenEvent() {
        return GegnerHatSatzGewonnen.gegnerHatSatzGewonnen();
    }

    private static DomainEvent gegnerHatGameGewonnenEvent() {
        return GegnerHatGameGewonnen.gegnerHatGameGewonnen();
    }

    private static StandardScoring preGegnerGewinntGameState() {
        return standardScoring(currentGame(LaufendesGame.laufendesGame(spieler0(), gegner40()).get()),
                currentSatz(Satz.zero()),
                anyMatchState());

    }

    private static LaufendesStandardMatch anyMatchState() {
        return matchSpieler1Gegner0();
    }

    private static Integer gegner40() {
        return 3;
    }

    private static Integer spieler0() {
        return 0;
    }

    private static DomainEvent spielerHatGameGewonnenEvent() {
        return SpielerHatGameGewonnen.spielerHatGameGewonnen(4, 0);
    }

    private static StandardScoring preSpielerGewinntGameState() {
        return standardScoring(currentGame(LaufendesGame.laufendesGame(spieler40(), gegner0()).get()),
                currentSatz(Satz.zero()),
                anyMatchState());
    }

    private static StandardScoring spielerMatchGewonnenState() {
        return standardScoring(currentGame(AbgeschlossenesGame.fromInteger(spielerGame(), gegner0()).get()),
                currentSatz(AbgeschlossenerSatz.fromInteger(6, 0).get()),
                AbgeschlossenesStandardMatch.abgeschlossenesStandardMatch(2, 0).get());
    }

    private static Integer spielerGame() {
        return 4;
    }

    private static StandardScoring preSpielerGewinntMatchState() {
        final StandardScoring standardScoring = standardScoring(currentGame(
                        LaufendesGame.laufendesGame(spieler40(), gegner0()).get()),
                currentSatz(LaufenderSatz.fromInteger(spielerSatz5(), gegnerSatz0()).get()),
                matchSpieler1Gegner0());
        return standardScoring;
    }

    private static LaufendesStandardMatch matchSpieler0Gegner0() {
        return laufendesStandardMatch(spielerMatch0(), gegnerMatch0()).get();
    }

    private static LaufendesStandardMatch matchSpieler0Gegner1() {
        return laufendesStandardMatch(spielerMatch0(), gegnerMatch1()).get();
    }

    private static PunkteGegner gegnerMatch1() {
        return PunkteGegner.fromInteger(1).get();
    }

    private static PunkteSpieler spielerMatch0() {
        return PunkteSpieler.zero();
    }

    private static LaufendesStandardMatch matchSpieler1Gegner0() {
        return laufendesStandardMatch(spielerMatch1(), gegnerMatch0()).get();
    }

    private static PunkteGegner gegnerMatch0() {
        return PunkteGegner.fromInteger(0).get();
    }

    private static PunkteSpieler spielerMatch1() {
        return PunkteSpieler.fromInteger(1).get();
    }


    private static Integer gegnerSatz0() {
        return 0;
    }

    private static Integer spielerSatz5() {
        return 5;
    }

    private static Integer gegner0() {
        return 0;
    }

    private static Integer spieler40() {
        return 3;
    }

    private static DomainEvent spielerHatMatchGewonnenEvent() {
        return SpielerHatMatchGewonnen.spielerHatMatchGewonnen();
    }

    private static StandardScoring scoringZero() {
        return StandardScoring.zero();
    }
}