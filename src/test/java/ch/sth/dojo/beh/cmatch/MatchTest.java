package ch.sth.dojo.beh.cmatch;

import static org.assertj.core.api.Assertions.assertThat;

import ch.sth.dojo.beh.Condition;
import ch.sth.dojo.beh.cmatch.domain.AbgeschlossenesMatch;
import ch.sth.dojo.beh.cmatch.domain.Match;
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

class MatchTest {

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
        final Tuple3<Match, MatchScenario, Match> map = Tuple.of(input, cmd, expected)
            .map(parseMatchScore(), parseCommand(), parseMatchScore());
        var re = map
            .apply(MatchTest::executeCommand);

        assertThat(re.isRight())
            .withFailMessage(re::getLeft)
            .isTrue();
        assertThat(re.get())
            .isEqualTo(map._3);
    }

    private static Either<String, Match> executeCommand(Match match, MatchScenario matchScenario, Match match1) {
        return Condition.condition(matchScenario, x -> x == MatchScenario.SpielerTransition,
            xx -> Match.apply(match, laufendesMatch -> Either.right(laufendesMatch.spielerPunktet()), x -> Either.left("Abgeschlossenes Match")),
            xx -> Match.apply(match, laufendesMatch -> Either.right(laufendesMatch.gegnerPunktet()), x -> Either.left("Abgeschlossenes Match")));
    }

    private Function<String, MatchScenario> parseCommand() {
        return MatchScenario::valueOf;
    }

    private Function<String, Match> parseMatchScore() {
        return input -> Option.some(input)
            .filter(Predicates.not("MATCH"::equals))
            .toEither(new AbgeschlossenesMatch())
            .map(str -> str.split("-"))
            .map(Arrays::stream)
            .map(List::ofAll)
            .map(list -> list.map(Integer::parseInt))
            .map(list -> Match.of(list.get(0), list.get(1)).get())
            .fold(Function.identity(), Function.identity());
    }

    private static Match nextExpectedState() {
        return null;
    }

    private static DomainEvent event() {
        return null;
    }

    private static Match prevMatchState() {
        return null;
    }
}

enum MatchScenario {
    SpielerTransition, GegnerTransition
}
