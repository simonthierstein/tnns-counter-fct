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
import org.assertj.core.api.Condition;
import org.assertj.core.condition.AllOf;
import org.junit.jupiter.api.Test;

class GameAggregateRootTest {

    @Test
    void spielerPunktet_notequal_prev() {
        LaufendesGame prev = LaufendesGame.initial();
        assertThat(spielerPunktet(prev)).isNotEqualTo(prev);
    }
    @Test
    void spielerPunktet_equal_empty_spielerPunktet() {
        LaufendesGame prev1 = LaufendesGame.initial();
        LaufendesGame prev2 = LaufendesGame.initial();
        assertThat(spielerPunktet(prev1)).isEqualTo(spielerPunktet(prev2));
    }
    @Test
    void gegnerPunktet_notequal_prev() {
        LaufendesGame prev = LaufendesGame.initial();
        assertThat(gegnerPunktet(prev)).isNotEqualTo(prev);
    }
    @Test
    void gegnerPunktet_equal_empty_gegnerPunktet() {
        LaufendesGame prev1 = LaufendesGame.initial();
        LaufendesGame prev2 = LaufendesGame.initial();
        assertThat(gegnerPunktet(prev1)).isEqualTo(gegnerPunktet(prev2));
    }

    @Test
    void spielerPunktetAndGegnerPunktetEval2Integer_is1To1() {
        LaufendesGame prev1 = LaufendesGame.initial();
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
    void fdsafdsa() {
        final LaufendesGame initial = LaufendesGame.initial();

        final Game game = spielerPunktet(initial);

        Either<String, Game> x = Match(game).of(
                Case($(instanceOf(LaufendesGame.class)), prev -> Either.right(spielerPunktet(prev))),
                Case($(instanceOf(AbgeschlossenesGame.class)), Either.left("geht nicht"))
        );

        final Either<String, Game> gehtNicht = x.flatMap(game1 -> Match(game1).of(
                Case($(instanceOf(LaufendesGame.class)), prev -> Either.right(spielerPunktet(prev))),
                Case($(instanceOf(AbgeschlossenesGame.class)), Either.left("geht nicht"))
        ));

        System.out.println(gehtNicht);

        final Either<String, Game> gehtNoch = gehtNicht.flatMap(game1 -> Match(game1).of(
                Case($(instanceOf(LaufendesGame.class)), prev -> Either.right(spielerPunktet(prev))),
                Case($(instanceOf(AbgeschlossenesGame.class)), Either.left("geht nicht"))
        ));

        System.out.println(gehtNoch);
        final Either<String, Game> gehtNichtMehr = gehtNoch.flatMap(game1 -> Match(game1).of(
                Case($(instanceOf(LaufendesGame.class)), prev -> Either.right(spielerPunktet(prev))),
                Case($(instanceOf(AbgeschlossenesGame.class)), Either.left("geht nicht"))
        ));

        System.out.println(gehtNichtMehr);

    }

    static Condition<String> hasSpieler1() {
        return new Condition<String>(str -> str.contains("spieler:1"), "");
    }
}