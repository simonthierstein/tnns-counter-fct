package ch.sth.dojo.gamefct.infra;

import static ch.sth.dojo.gamefct.GameAggregateRoot.gegnerPunktet;
import static ch.sth.dojo.gamefct.GameAggregateRoot.spielerPunktet;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;
import static org.assertj.core.api.Assertions.allOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.condition.MappedCondition.mappedCondition;

import ch.sth.dojo.gamefct.AbgeschlossenesGame;
import ch.sth.dojo.gamefct.Game;
import ch.sth.dojo.gamefct.LaufendesGame;
import ch.sth.dojo.gamefct.GameAggregateRoot;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Either;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.assertj.core.condition.AllOf;
import org.junit.jupiter.api.Test;

class GameAggregateRootTest {

    @Test
    void spielerPunktet_notequal_prev() {
        LaufendesGame prev = GameAggregateRoot.initial();
        assertThat(spielerPunktet(prev)).isNotEqualTo(prev);
    }
    @Test
    void spielerPunktet_equal_empty_spielerPunktet() {
        LaufendesGame prev1 = GameAggregateRoot.initial();
        LaufendesGame prev2 = GameAggregateRoot.initial();
        assertThat(spielerPunktet(prev1)).isEqualTo(spielerPunktet(prev2));
    }
    @Test
    void gegnerPunktet_notequal_prev() {
        LaufendesGame prev = GameAggregateRoot.initial();
        assertThat(gegnerPunktet(prev)).isNotEqualTo(prev);
    }
    @Test
    void gegnerPunktet_equal_empty_gegnerPunktet() {
        LaufendesGame prev1 = GameAggregateRoot.initial();
        LaufendesGame prev2 = GameAggregateRoot.initial();
        assertThat(gegnerPunktet(prev1)).isEqualTo(gegnerPunktet(prev2));
    }

    @Test
    void spielerPunktetAndGegnerPunktetEval2Integer_is1To1() {
        LaufendesGame prev1 = GameAggregateRoot.initial();
        final LaufendesGame game = (LaufendesGame) spielerPunktet(prev1);
        final LaufendesGame game1 = (LaufendesGame) gegnerPunktet(game);
        final Tuple2<Integer, Integer> integerIntegerTuple2 = GameAggregateRoot.eval(GameAggregateRoot.eval2Integer, game1);
        assertThat(integerIntegerTuple2).isEqualTo(Tuple.of(1, 1));
        assertThat(integerIntegerTuple2).is(new Condition<>(t2 -> t2._1 == 1, "fdsa"));
        assertThat(integerIntegerTuple2).is(AllOf.allOf(
                new Condition<>(t2 -> t2._1 == 1, "spieler score %d", 1),
                new Condition<>(t2 -> t2._2 == 1, "geger score %d", 1)));

        assertThat(integerIntegerTuple2).is(mappedCondition(t2 -> t2.apply((s, g) -> String.format("{spieler:%s,gegner:%s}", s, g)),
                hasSpieler1(), "must have spieler one"));
    }


    @Test
    void spielerPunktet_4punkte_abgeschlossenesGame() {
        final LaufendesGame initial = GameAggregateRoot.initial();

        final Game game = spielerPunktet(initial);

        final Function<LaufendesGame, Either<String, Game>> laufendesGameEitherFunction = prev1 -> Either.right(spielerPunktet(prev1));
        final Function<AbgeschlossenesGame, Either<String, Game>> gehtNicht = x1 -> Either.left("geht nicht");
        final Function<Game, Either<String, ? extends Game>> gameEitherFunction = game1 -> Game.apply(game1, laufendesGameEitherFunction, gehtNicht);

        final String fold = gameEitherFunction.apply(game)
                .flatMap(gameEitherFunction)
                .flatMap(gameEitherFunction)
                .fold(err -> err, Object::toString);

        assertThat(fold).contains("AbgeschlossenesGame");
    }

    static Condition<String> hasSpieler1() {
        return new Condition<String>(str -> str.contains("spieler:1"), "");
    }
}