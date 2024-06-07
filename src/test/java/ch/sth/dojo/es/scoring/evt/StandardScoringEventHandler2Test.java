package ch.sth.dojo.es.scoring.evt;

import static ch.sth.dojo.es.match.LaufendesStandardMatch.laufendesStandardMatch;
import static ch.sth.dojo.es.scoring.CurrentGame.currentGame;
import static ch.sth.dojo.es.scoring.CurrentSatz.currentSatz;
import static ch.sth.dojo.es.scoring.StandardScoring.standardScoring;
import static org.assertj.core.api.Assertions.assertThat;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.events.SpielerHatMatchGewonnen;
import ch.sth.dojo.es.game.AbgeschlossenesGame;
import ch.sth.dojo.es.game.LaufendesGame;
import ch.sth.dojo.es.match.AbgeschlossenesStandardMatch;
import ch.sth.dojo.es.match.PunkteGegner;
import ch.sth.dojo.es.match.PunkteSpieler;
import ch.sth.dojo.es.satz.AbgeschlossenerSatz;
import ch.sth.dojo.es.satz.LaufenderSatz;
import ch.sth.dojo.es.scoring.StandardScoring;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;

class StandardScoringEventHandler2Test {

    @Test
    void ffdsafdsa() {

        final Either<DomainError, StandardScoring> result = StandardScoringEventHandler.handleEvent2(preSpielerGewinntMatchState(), event());

        assertThat(result)
                .isNotNull()
                .isInstanceOf(Either.Right.class)
                .isNotEqualTo(Either.right(state()));

        assertThat(result.get()).isNotNull()
                .isEqualTo(spielerMatchGewonnenState());



    }

    private StandardScoring spielerMatchGewonnenState() {
        return standardScoring(currentGame(AbgeschlossenesGame.fromInteger(spielerGame(), gegner0()).get()),
                currentSatz(AbgeschlossenerSatz.fromInteger(6, 0).get()),
                AbgeschlossenesStandardMatch.abgeschlossenesStandardMatch(2, 0).get());
    }

    private Integer spielerGame() {
        return 4;
    }

    private StandardScoring preSpielerGewinntMatchState() {
        final StandardScoring standardScoring = standardScoring(currentGame(
                        LaufendesGame.laufendesGame(spieler40(), gegner0()).get()),
                currentSatz(LaufenderSatz.fromInteger(spielerSatz5(), gegnerSatz0()).get()),
                laufendesStandardMatch(spielerMatch1(), gegnerMatch0()).get());
        return standardScoring;
    }

    private PunkteGegner gegnerMatch0() {
        return PunkteGegner.fromInteger(0).get();
    }

    private PunkteSpieler spielerMatch1() {
        return PunkteSpieler.fromInteger(1).get();
    }


    private Integer gegnerSatz0() {
        return 0;
    }

    private Integer spielerSatz5() {
        return 5;
    }

    private Integer gegner0() {
        return 0;
    }

    private Integer spieler40() {
        return 3;
    }

    private DomainEvent event() {
        return SpielerHatMatchGewonnen.spielerHatMatchGewonnen();
    }

    private StandardScoring state() {
        return StandardScoring.zero();
    }
}