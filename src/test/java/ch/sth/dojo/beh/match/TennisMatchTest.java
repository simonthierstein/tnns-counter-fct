package ch.sth.dojo.beh.match;

import static org.assertj.core.api.Assertions.assertThat;

import ch.sth.dojo.beh.Condition;
import ch.sth.dojo.beh.match.domain.AbgeschlossenesMatch;
import ch.sth.dojo.beh.match.domain.TennisMatch;
import ch.sth.dojo.beh.evt.DomainEvent;
import io.vavr.Predicates;
import io.vavr.Tuple;
import io.vavr.Tuple3;
import io.vavr.collection.List;
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.util.Arrays;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TennisMatchTest {

    @Test
    void initial() {
        //        assertThat(CMatchEventHandler.handleEvent(prevMatchState(), event()))
        //            .isNotNull()
        //            .isEqualTo(nextExpectedState());
    }

    @ParameterizedTest
    @CsvSource(
        {
            "0-0,SpielerTransition,1-0",
            "1-0,SpielerTransition,MATCH",
            "0-0,GegnerTransition,0-1",
            "0-1,GegnerTransition,MATCH",
            "1-0,GegnerTransition,1-1",
            "1-1,GegnerTransition,MATCH",
            "1-1,SpielerTransition,MATCH"
        }
    )
    void count(String input, String cmd, String expected) {
        final Tuple3<TennisMatch, MatchScenario, TennisMatch> map = Tuple.of(input, cmd, expected)
            .map(parseMatchScore(), parseCommand(), parseMatchScore());
        var re = map
            .apply(TennisMatchTest::executeCommand);

        assertThat(re.isRight())
            .withFailMessage(re::getLeft)
            .isTrue();
        assertThat(re.get())
            .isEqualTo(map._3);
    }

    private static Either<String, TennisMatch> executeCommand(TennisMatch match, MatchScenario matchScenario, TennisMatch match1) {
        return Condition.condition(matchScenario, x -> x == MatchScenario.SpielerTransition,
            xx -> TennisMatch.apply(match, laufendesMatch -> Either.right(laufendesMatch.spielerPunktet()), x -> Either.left("Abgeschlossenes Match")),
            xx -> TennisMatch.apply(match, laufendesMatch -> Either.right(laufendesMatch.gegnerPunktet()), x -> Either.left("Abgeschlossenes Match")));
    }

    private Function<String, MatchScenario> parseCommand() {
        return MatchScenario::valueOf;
    }

    private Function<String, TennisMatch> parseMatchScore() {
        return input -> Option.some(input)
            .filter(Predicates.not("MATCH"::equals))
            .toEither(new AbgeschlossenesMatch())
            .map(str -> str.split("-"))
            .map(Arrays::stream)
            .map(List::ofAll)
            .map(list -> list.map(Integer::parseInt))
            .map(list -> TennisMatch.of(list.get(0), list.get(1)).get())
            .fold(Function.identity(), Function.identity());
    }

    private static TennisMatch nextExpectedState() {
        return null;
    }

    private static DomainEvent event() {
        return null;
    }

    private static TennisMatch prevMatchState() {
        return null;
    }
}

enum MatchScenario {
    SpielerTransition, GegnerTransition
}
