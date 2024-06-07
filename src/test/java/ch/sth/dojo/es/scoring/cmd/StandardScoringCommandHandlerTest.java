package ch.sth.dojo.es.scoring.cmd;

import static org.assertj.core.api.Assertions.assertThat;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.game.AbgeschlossenesGame;
import ch.sth.dojo.es.game.Game;
import ch.sth.dojo.es.game.LaufendesGame;
import ch.sth.dojo.es.game.Punkt;
import ch.sth.dojo.es.satz.Satz;
import ch.sth.dojo.es.scoring.StandardScoring;
import ch.sth.dojo.es.scoring.evt.StandardScoringEventHandler;
import io.vavr.Function2;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.control.Either;
import java.util.function.Function;
import org.junit.jupiter.api.Test;

class StandardScoringCommandHandlerTest {

    @Test
    void fdsafdsa() {
        final StandardScoring s0 = emptyState();
        final Either<DomainError, StandardScoring> standardScorings = StandardScoringCommandHandler
                .spielerGewinnePunkt(s0)
                .flatMap(event ->
                        StandardScoringEventHandler
                                .handleEvent(s0, event))
                .flatMap(next -> StandardScoringCommandHandler
                        .spielerGewinnePunkt(next)
                        .flatMap(event ->
                                StandardScoringEventHandler
                                        .handleEvent(next, event)))
                .flatMap(next -> StandardScoringCommandHandler
                        .spielerGewinnePunkt(next)
                        .flatMap(event ->
                                StandardScoringEventHandler
                                        .handleEvent(next, event)))
                .flatMap(next -> StandardScoringCommandHandler
                        .spielerGewinnePunkt(next)
                        .flatMap(event ->
                                StandardScoringEventHandler
                                        .handleEvent(next, event)));

        final Tuple2 gameScore = standardScorings.fold(err -> Tuple.of(0, 0), succ -> evalGameScore(succ));
        final Tuple2 satzScore = standardScorings.fold(err -> Tuple.of(0, 0), succ -> evalSatzScore(succ));


        assertThat(gameScore).isEqualTo(Tuple.of(2, 0));
        assertThat(satzScore).isEqualTo(Tuple.of(2, 0));

    }

    private static Tuple2<Integer, Integer> evalSatzScore(final StandardScoring succ) {
        return Satz.apply(succ.currentSatz().current(),
                laufenderSatz -> laufenderSatz.eval(List::size, List::size),
                abgeschlossenerSatz -> abgeschlossenerSatz.eval(Function.identity(), Function.identity())
        );
    }

    private static Tuple2<Integer, Integer> evalGameScore(final Either<DomainError, StandardScoring> s1) {
        final StandardScoring standardScoring = s1.get();
        return evalGameScore(standardScoring);
    }

    private static Tuple2<Integer, Integer> evalGameScore(final StandardScoring standardScoring) {
        return Game.apply(standardScoring.currentGame().current(),
                laufend -> LaufendesGame.eval(laufend, extractGamePunkte()),
                abgeschlossen -> AbgeschlossenesGame.eval(abgeschlossen, extractGamePunkte()),
                preInitializedGame -> Tuple.of(0, 0));
    }

    private static Function2<List<Punkt>, List<Punkt>, Tuple2<Integer, Integer>> extractGamePunkte() {
        return (spieler, gegner) -> Tuple.of(spieler.size(), gegner.size());
    }

    private StandardScoring emptyState() {
        return StandardScoring.zero();
    }
}