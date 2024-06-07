package ch.sth.dojo.es.scoring.evt;

import static org.assertj.core.api.Assertions.assertThat;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.events.SpielerHatMatchGewonnen;
import ch.sth.dojo.es.scoring.StandardScoring;
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

        assertThat(result.get()).isNotNull();


    }

    private DomainEvent event() {
        return SpielerHatMatchGewonnen.spielerHatMatchGewonnen();
    }

    private StandardScoring state() {
        return StandardScoring.zero();
    }
}