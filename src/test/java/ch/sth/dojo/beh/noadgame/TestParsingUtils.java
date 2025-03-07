/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.noadgame;

import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.GegnerGameGewonnen;
import ch.sth.dojo.beh.evt.GegnerPunktGewonnen;
import ch.sth.dojo.beh.evt.SpielerGameGewonnen;
import ch.sth.dojo.beh.evt.SpielerPunktGewonnen;
import ch.sth.dojo.beh.noadgame.domain.NoAdGame;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import io.vavr.Tuple;
import io.vavr.collection.List;
import java.util.Arrays;
import java.util.function.Function;

public class TestParsingUtils {

    public static Function<String, NoAdGame> parseTennisToNoAdGame() {
        return tennisStr -> List.ofAll(Arrays.stream(tennisStr.split("-")))
            .map(parseTennisToNumber())
            .foldLeft(Tuple.of(0, 0), (acc, it) -> acc.update1(it).swap()).apply((sp, ge) ->
                NoAdGame.of(sp, ge).get());
    }

    public static Function<String, Integer> parseTennisToNumber() {
        return str -> Match(str).of(
            Case($("00"), 0),
            Case($("15"), 1),
            Case($("30"), 2),
            Case($("40"), 3),
            Case($("GAME"), 4)
        );
    }

    public static Function<String, DomainEvent> parseEvent() {
        return str -> Match(str).of(
            Case($("SpielerPunktGewonnen"), new SpielerPunktGewonnen()),
            Case($("SpielerGameGewonnen"), new SpielerGameGewonnen()),
            Case($("GegnerPunktGewonnen"), new GegnerPunktGewonnen()),
            Case($("GegnerGameGewonnen"), new GegnerGameGewonnen())
        );
    }
}
