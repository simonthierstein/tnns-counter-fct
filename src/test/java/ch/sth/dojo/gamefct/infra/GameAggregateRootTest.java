package ch.sth.dojo.gamefct.infra;

import static ch.sth.dojo.gamefct.GameAggregateRoot.gegnerPunktet;
import static ch.sth.dojo.gamefct.GameAggregateRoot.spielerPunktet;
import static org.assertj.core.api.Assertions.allOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.condition.MappedCondition.mappedCondition;

import ch.sth.dojo.gamefct.LaufendesGame;
import ch.sth.dojo.gamefct.GameAggregateRoot;
import io.vavr.Tuple;
import io.vavr.Tuple2;
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
        final Tuple2<Integer, Integer> integerIntegerTuple2 = GameAggregateRoot.eval(GameAggregateRoot.eval2Integer, gegnerPunktet(spielerPunktet(prev1)));
        assertThat(integerIntegerTuple2).isEqualTo(Tuple.of(1, 1));
        assertThat(integerIntegerTuple2).is(new Condition<>(t2 -> t2._1 == 1, "fdsa"));
        assertThat(integerIntegerTuple2).is(AllOf.allOf(
                new Condition<>(t2 -> t2._1 == 1, "spieler score %d", 1),
                new Condition<>(t2 -> t2._2 == 1, "geger score %d", 1)));

        assertThat(integerIntegerTuple2).is(mappedCondition(t2 -> t2.apply((s, g) -> String.format("{spieler:%s,gegner:%s}", s, g)),
                hasSpieler1(), "must have spieler one"));
    }

    static Condition<String> hasSpieler1() {
        return new Condition<String>(str -> str.contains("spieler:1"), "");
    }
}