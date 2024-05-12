/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.game.trans;

import static ch.sth.dojo.es.events.GegnerHatGameGewonnen.gegnerHatGameGewonnen;
import static ch.sth.dojo.es.events.SpielerHatGameGewonnen.spielerHatGameGewonnen;
import static ch.sth.dojo.es.game.AbgeschlossenesGame.abgeschlossenesGame;
import static ch.sth.dojo.es.game.Punkt.punkt;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.events.GegnerHatGameGewonnen;
import ch.sth.dojo.es.events.SpielerHatGameGewonnen;
import ch.sth.dojo.es.game.AbgeschlossenesGame;
import ch.sth.dojo.es.game.LaufendesGame;
import ch.sth.dojo.es.game.Punkt;
import io.vavr.collection.List;
import io.vavr.control.Either;
import java.util.function.Function;

public class Laufend2Abgeschlossen {
    public static Function<SpielerHatGameGewonnen, AbgeschlossenesGame> shgg(LaufendesGame prev) {
        return event -> abgeschlossenesGame(prev.punkteSpieler.append(punkt()), prev.punkteGegner);
    }

    public static Function<GegnerHatGameGewonnen, AbgeschlossenesGame> ghgg(LaufendesGame prev) {
        return event -> abgeschlossenesGame(prev.punkteSpieler, prev.punkteGegner.append(punkt()));
    }

    public static SpielerHatGameGewonnen SpielerHatGameGewonnen(final List<Punkt> punkteSpieler, final List<Punkt> punkteGegner) {
        return spielerHatGameGewonnen(punkteSpieler.size(), punkteGegner.size());
    }

    public static GegnerHatGameGewonnen GegnerHatGameGewonnen(final List<Punkt> punkteSpieler, final List<Punkt> punkteGegner) {
        return gegnerHatGameGewonnen();
    }

    public static Function<LaufendesGame, Either<DomainError, DomainEvent>> spielerGewinneGame() {
        return laufendesGame -> Either.right(SpielerHatGameGewonnen(laufendesGame.punkteSpieler.append(punkt()), laufendesGame.punkteGegner));
    }

    public static Function<LaufendesGame, Either<DomainError, DomainEvent>> gegnerGewinneGame() {
        return laufendesGame -> Either.right(GegnerHatGameGewonnen(laufendesGame.punkteSpieler, laufendesGame.punkteGegner.append(punkt())));
    }
}
