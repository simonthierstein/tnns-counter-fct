package ch.sth.dojo.beh.cmatch;

import static org.assertj.core.api.Assertions.assertThat;

import ch.sth.dojo.beh.Condition;
import ch.sth.dojo.beh.cmatch.domain.LaufendesMatch;
import ch.sth.dojo.beh.cmatch.domain.MatchScore;
import ch.sth.dojo.beh.cmatch.domain.state.AbgeschlossenesMatchState;
import ch.sth.dojo.beh.cmatch.domain.state.MatchState;
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

class CMatchTest {

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
        final Tuple3<MatchState, MatchCommand, MatchState> map = Tuple.of(input, cmd, expected)
            .map(parseMatchScore(), parseCommand(), parseMatchScore());
        var re = map
            .apply(CMatchTest::executeCommand);

        assertThat(re.isRight())
            .withFailMessage(re::getLeft)
            .isTrue();
        assertThat(re.get())
            .isEqualTo(map._3);
    }

    private static Either<String, MatchState> executeCommand(MatchState match, MatchCommand matchCommand, MatchState match1) {
        return Condition.condition(matchCommand, x -> x == MatchCommand.SpielerTransition,
            xx -> MatchScore.apply(match, laufendesMatch -> Either.right(LaufendesMatch.spielerPunktet(laufendesMatch)), x -> Either.left("Abgeschlossenes Match")),
            xx -> MatchScore.apply(match, laufendesMatch -> Either.right(LaufendesMatch.gegnerPunktet(laufendesMatch)), x -> Either.left("Abgeschlossenes Match")));
    }

    private Function<String, MatchCommand> parseCommand() {
        return MatchCommand::valueOf;
    }

    private Function<String, MatchState> parseMatchScore() {
        return input -> Option.some(input)
            .filter(Predicates.not("MATCH"::equals))
            .toEither(new AbgeschlossenesMatchState())
            .map(str -> str.split("-"))
            .map(Arrays::stream)
            .map(List::ofAll)
            .map(list -> list.map(Integer::parseInt))
            .map(list -> MatchScore.of(list.get(0), list.get(1)).get())
            .fold(Function.identity(), Function.identity());
    }

    private static MatchState nextExpectedState() {
        return null;
    }

    private static DomainEvent event() {
        return null;
    }

    private static MatchState prevMatchState() {
        return null;
    }
}

enum MatchCommand {
    SpielerTransition, GegnerTransition
}
