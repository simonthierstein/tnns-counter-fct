package ch.sth.dojo.es.scoring.evt;

import static ch.sth.dojo.es.game.LaufendesGame.laufendesGame;
import static ch.sth.dojo.es.match.LaufendesStandardMatch.laufendesStandardMatch;
import static ch.sth.dojo.es.match.PunkteGegner.punkteGegner;
import static ch.sth.dojo.es.match.PunkteSpieler.punkteSpieler;
import static ch.sth.dojo.es.satz.LaufenderSatz.laufenderSatz;
import static ch.sth.dojo.es.scoring.CurrentGame.currentGame;
import static ch.sth.dojo.es.scoring.CurrentSatz.currentSatz;
import static ch.sth.dojo.es.scoring.StandardScoring.standardScoring;
import static org.assertj.core.api.Assertions.assertThat;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.events.SpielerHatMatchGewonnen;
import ch.sth.dojo.es.game.Punkt;
import ch.sth.dojo.es.match.Punkte;
import ch.sth.dojo.es.match.PunkteGegner;
import ch.sth.dojo.es.match.PunkteSpieler;
import ch.sth.dojo.es.scoring.StandardScoring;
import io.vavr.collection.List;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;

class StandardScoringEventHandler2Test {

    @Test
    void ffdsafdsa() {

        final Either<DomainError, StandardScoring> result = StandardScoringEventHandler.handleEvent2(state(), event());

        assertThat(result)
                .isNotNull()
                .isInstanceOf(Either.Right.class)
                .isNotEqualTo(Either.right(state()));

        assertThat(result.get()).isNotNull()
                .isEqualTo(spielerGewinntMatchState());



    }

    private StandardScoring spielerGewinntMatchState() {
        return standardScoring(currentGame(
                        laufendesGame(spieler40(), gegner0())),
                currentSatz(laufenderSatz(spielerSatz5(), gegnerSatz0())),
                laufendesStandardMatch(spielerMatch1(), gegnerMatch0()).get());
    }

    private PunkteGegner gegnerMatch0() {
        return punkteGegner(Punkte.punkte(List.empty()));
    }

    private PunkteSpieler spielerMatch1() {
        return punkteSpieler(Punkte.punkte(List.of(Punkt.punkt())));
    }

    private List<Punkt> gegnerSatz0() {
        return null;
    }

    private List<Punkt> spielerSatz5() {
        return null;
    }

    private List<Punkt> gegner0() {
        return null;
    }

    private List<Punkt> spieler40() {
        return null;
    }

    private DomainEvent event() {
        return SpielerHatMatchGewonnen.spielerHatMatchGewonnen();
    }

    private StandardScoring state() {
        return StandardScoring.zero();
    }
}