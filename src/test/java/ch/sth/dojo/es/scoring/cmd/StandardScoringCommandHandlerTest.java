package ch.sth.dojo.es.scoring.cmd;

import static org.assertj.core.api.Assertions.assertThat;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.game.AbgeschlossenesGame;
import ch.sth.dojo.es.game.Game;
import ch.sth.dojo.es.game.LaufendesGame;
import ch.sth.dojo.es.game.Punkt;
import ch.sth.dojo.es.match.PunkteGegner;
import ch.sth.dojo.es.match.PunkteSpieler;
import ch.sth.dojo.es.match.StandardMatch;
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
    void spielerGewinntGame() {
        final StandardScoring s0 = emptyState();
        final Either<DomainError, StandardScoring> standardScorings = execSpielerGewinnePunkt(s0)
                .flatMap(next -> execSpielerGewinnePunkt(next))
                .flatMap(next -> execSpielerGewinnePunkt(next))
                .flatMap(next -> execSpielerGewinnePunkt(next));

        final Tuple2 gameScore = standardScorings.fold(err -> Tuple.of(0, 0), succ -> evalGameScore(succ));
        final Tuple2 satzScore = standardScorings.fold(err -> Tuple.of(0, 0), succ -> evalSatzScore(succ));
        final Tuple2 matchScore = standardScorings.fold(err -> Tuple.of(0, 0), succ -> evalMatchScore(succ));


        assertThat(gameScore).isEqualTo(Tuple.of(0, 0));
        assertThat(satzScore).isEqualTo(Tuple.of(1, 0));
        assertThat(matchScore).isEqualTo(Tuple.of(0, 0));

    }

    @Test
    void gegnerGewinntGame() {
        final StandardScoring s0 = emptyState();
        final Either<DomainError, StandardScoring> standardScorings = execGegnerGewinnePunkt(s0)
                .flatMap(next -> execGegnerGewinnePunkt(next))
                .flatMap(next -> execGegnerGewinnePunkt(next))
                .flatMap(next -> execGegnerGewinnePunkt(next));

        final Tuple2 gameScore = standardScorings.fold(err -> Tuple.of(0, 0), succ -> evalGameScore(succ));
        final Tuple2 satzScore = standardScorings.fold(err -> Tuple.of(0, 0), succ -> evalSatzScore(succ));
        final Tuple2 matchScore = standardScorings.fold(err -> Tuple.of(0, 0), succ -> evalMatchScore(succ));


        assertThat(gameScore).isEqualTo(Tuple.of(0, 4));
        assertThat(satzScore).isEqualTo(Tuple.of(0, 1));
        assertThat(matchScore).isEqualTo(Tuple.of(0, 0));

    }

    @Test
    void gegnerGewinntSatz() {
        final StandardScoring s0 = emptyState();

        final Either<DomainError, StandardScoring> result = List.range(0, 24)
                .foldLeft(execGegnerGewinnePunkt(s0), (xs, x) -> xs.flatMap(next -> execGegnerGewinnePunkt(next)));

        final Tuple2 gameScore = result.fold(err -> Tuple.of(0, 0), succ -> evalGameScore(succ));
        final Tuple2 satzScore = result.fold(err -> Tuple.of(0, 0), succ -> evalSatzScore(succ));
        final Tuple2 matchScore = result.fold(err -> Tuple.of(0, 0), succ -> evalMatchScore(succ));


        assertThat(gameScore).isEqualTo(Tuple.of(0, 0));
        assertThat(satzScore).isEqualTo(Tuple.of(0, 0));
        assertThat(matchScore).isEqualTo(Tuple.of(0, 1));

    }

    private static Either<DomainError, StandardScoring> execSpielerGewinnePunkt(final StandardScoring next) {
        return StandardScoringCommandHandler
                .spielerGewinnePunkt(next)
                .flatMap(event ->
                        StandardScoringEventHandler
                                .handleEvent(next, event));
    }

    private static Either<DomainError, StandardScoring> execGegnerGewinnePunkt(final StandardScoring next) {
        return StandardScoringCommandHandler
                .gegnerGewinnePunkt(next)
                .flatMap(event ->
                        StandardScoringEventHandler
                                .handleEvent(next, event));
    }

    private static Tuple2<Integer, Integer> evalMatchScore(final StandardScoring succ) {
        return StandardMatch.apply(succ.match(),
                laufendesStandardMatch -> laufendesStandardMatch.eval(PunkteSpieler::current, PunkteGegner::current),
                abgeschl -> abgeschl.eval(Tuple::of)
        );
    }

    private static Tuple2<Integer, Integer> trewtrew(final PunkteSpieler sp, final PunkteGegner ge) {
        return Tuple.of(sp.current(), ge.current());
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