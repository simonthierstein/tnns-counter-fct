package ch.sth.dojo.gamefct.infra;

import static ch.sth.dojo.gamefct.GameAggregateRoot.gegnerPunktet;
import static ch.sth.dojo.gamefct.GameAggregateRoot.spielerPunktet;
import static org.assertj.core.api.Assertions.allOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.condition.MappedCondition.mappedCondition;

import ch.sth.dojo.gamefct.Game;
import ch.sth.dojo.gamefct.GameAggregateRoot;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.assertj.core.condition.AllOf;
import org.assertj.core.condition.MappedCondition;
import org.junit.jupiter.api.Test;

class GameAggregateRootTest {

    @Test
    void spielerPunktet_notequal_prev() {
        Game prev = Game.initial();
        assertThat(spielerPunktet(prev)).isNotEqualTo(prev);
    }
    @Test
    void spielerPunktet_equal_empty_spielerPunktet() {
        Game prev1 = Game.initial();
        Game prev2 = Game.initial();
        assertThat(spielerPunktet(prev1)).isEqualTo(spielerPunktet(prev2));
    }
    @Test
    void gegnerPunktet_notequal_prev() {
        Game prev = Game.initial();
        assertThat(gegnerPunktet(prev)).isNotEqualTo(prev);
    }
    @Test
    void gegnerPunktet_equal_empty_gegnerPunktet() {
        Game prev1 = Game.initial();
        Game prev2 = Game.initial();
        assertThat(gegnerPunktet(prev1)).isEqualTo(gegnerPunktet(prev2));
    }

    @Test
    void fdsafas() {
        Game prev1 = Game.initial();
        final Tuple2<Integer, Integer> integerIntegerTuple2 = GameAggregateRoot.eval2Integer(gegnerPunktet(spielerPunktet(prev1)));
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