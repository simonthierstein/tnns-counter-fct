package ch.sth.dojo.beh.cgame.domain;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.tiebreak.domain.Tiebreak;
import io.vavr.control.Either;
import static org.assertj.core.api.Assertions.assertThat;
import org.assertj.core.api.Condition;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TiebreakTest {

    @ParameterizedTest(name = "Tiebreak scoring {0}-{1}")
    @CsvSource(
        "7,7,7,7"
    )
    void create(Integer spielerBisGameInput, Integer gegnerBisGameInput, Integer spielerBisGameExpected, Integer gegnerBisGameExpected) {

        final Either<DomainProblem, Tiebreak> tiebreaks = Tiebreak.of(spielerBisGameInput, gegnerBisGameInput);

        assertThat(tiebreaks.isRight()).isTrue();
        assertThat(tiebreaks).is(new Condition<>(tb -> Tiebreak.of(spielerBisGameExpected, gegnerBisGameExpected).equals(tb), "Wrong Tiebreak"));
    }

    @ParameterizedTest(name = "Tiebreak scoring {0}-{1}")
    @CsvSource(
        {
            "7,7,false",
            "2,2,false",
            "1,3,true",
            "3,1,false"
        }
    )
    void predicatesSpieler(Integer spielerBisGameInput, Integer gegnerBisGameInput, Boolean expectedPredicate) {
        assertThat(Tiebreak.passIfSpielerOnePunktBisSatz.test(Tiebreak.of(spielerBisGameInput, gegnerBisGameInput).get())).isEqualTo(expectedPredicate);
    }

    @ParameterizedTest(name = "Tiebreak scoring {0}-{1}")
    @CsvSource(
        {
            "7,7,false",
            "2,2,false",
            "1,3,false",
            "3,1,true"
        }
    )
    void predicatesGegner(Integer spielerBisGameInput, Integer gegnerBisGameInput, Boolean expectedPredicate) {
        assertThat(Tiebreak.passIfGegnerOnePunktBisSatz.test(Tiebreak.of(spielerBisGameInput, gegnerBisGameInput).get())).isEqualTo(expectedPredicate);
    }

}