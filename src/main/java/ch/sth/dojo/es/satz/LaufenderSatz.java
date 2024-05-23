/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.satz;

import static ch.sth.dojo.es.game.Punkt.punkt;
import static ch.sth.dojo.es.satz.Satz.narrowSatz;

import ch.sth.dojo.es.Routing;
import ch.sth.dojo.es.events.GegnerHatGameGewonnen;
import ch.sth.dojo.es.events.SpielerHatGameGewonnen;
import ch.sth.dojo.es.game.Punkt;
import io.vavr.collection.List;
import io.vavr.control.Option;
import java.util.function.Function;

public record LaufenderSatz(List<Punkt> punkteSpieler, List<Punkt> punkteGegner) implements Satz {
    static LaufenderSatz zero() {
        return new LaufenderSatz(List.empty(), List.empty());
    }


    static Function<LaufenderSatz, LaufenderSatz> toLaufenderSatzSpieler() {
        return laufenderSatz -> new LaufenderSatz(laufenderSatz.punkteSpieler.append(punkt()), laufenderSatz.punkteGegner);
    }

    static Function<LaufenderSatz, AbgeschlossenerSatz> toAbgeschlossenerSatz() {
        return laufenderSatz -> new AbgeschlossenerSatz(laufenderSatz.punkteSpieler.size(), laufenderSatz.punkteGegner.size());
    }

    static Function<LaufenderSatz, LaufenderSatz> toLaufenderSatzGegner() {
        return laufenderSatz -> new LaufenderSatz(laufenderSatz.punkteSpieler, laufenderSatz.punkteGegner.append(punkt()));
    }

    static Function<SpielerHatGameGewonnen, Satz> spielerHatGameGewonnen(LaufenderSatz prev) {
        return evt -> Option.of(prev)
                .map(spielerGewinneGame())
                .get();
    }

    static Function<GegnerHatGameGewonnen, Satz> gegnerHatGameGewonnen(LaufenderSatz prev) {
        return evt -> gegnerGewinneGame().apply(prev);
    }

    public static Function<LaufenderSatz, Satz> spielerGewinneGame() {
        return prev -> spielerGewinneGame(prev);
    }

    public static Function<LaufenderSatz, Satz> gegnerGewinneGame() {
        return prev -> gegnerGewinneGame(prev);

    }

    private static Satz spielerGewinneGame(final LaufenderSatz prev) {
        return Routing.selective2Split(prev,
                LaufenderSatz::passIfSpielerWon,
                narrowSatz(toAbgeschlossenerSatz()),
                narrowSatz(toLaufenderSatzSpieler()));
    }

    private static Satz gegnerGewinneGame(final LaufenderSatz prev) {
        return Routing.selective2Split(prev,
                LaufenderSatz::passIfGegnerWon,
                narrowSatz(toAbgeschlossenerSatz()),
                narrowSatz(toLaufenderSatzGegner()));
    }

    private static boolean passIfSpielerWon(final LaufenderSatz laufenderSatz) {
        return Option.of(laufenderSatz)
                .filter(x -> x.punkteSpieler.size() == 6 && x.punkteGegner.size() <= 4)
                .filter(x -> x.punkteSpieler.size() == 7)
                .isDefined();
    }

    private static boolean passIfGegnerWon(final LaufenderSatz laufenderSatz) {
        return Option.of(laufenderSatz)
                .filter(x -> x.punkteGegner.size() == 6 && x.punkteSpieler.size() <= 4)
                .filter(x -> x.punkteGegner.size() == 7)
                .isDefined();
    }
}
