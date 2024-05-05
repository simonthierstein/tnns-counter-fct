package ch.sth.dojo.gamefct.infra;

import static ch.sth.dojo.gamefct.GameAggregateRoot.gegnerPunktet;
import static ch.sth.dojo.gamefct.GameAggregateRoot.spielerPunktet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.condition.MappedCondition.mappedCondition;

import ch.sth.dojo.gamefct.AbgeschlossenesGame;
import ch.sth.dojo.gamefct.Game;
import ch.sth.dojo.gamefct.GameAggregateRoot;
import ch.sth.dojo.gamefct.LaufendesGame;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Either;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.assertj.core.condition.AllOf;
import org.junit.jupiter.api.Test;

class ESGameAggregateRootTest {
}