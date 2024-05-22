package ch.sth.dojo.beh.cmd;

import ch.sth.dojo.beh.Condition;
import org.junit.jupiter.api.Test;

class ConditionTest {

    @Test
    void name() {
        var dfsa = Condition.condition("fdsa", x -> x.equals("fdsa"), x -> "pos", x -> "neg");

        System.out.println(dfsa);
    }
}