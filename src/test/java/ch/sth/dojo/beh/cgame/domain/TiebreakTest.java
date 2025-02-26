package ch.sth.dojo.beh.cgame.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ch.sth.dojo.beh.DomainProblem;
import io.vavr.control.Either;
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
}