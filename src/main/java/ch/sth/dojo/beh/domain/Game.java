package ch.sth.dojo.beh.domain;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

import io.vavr.collection.List;
import io.vavr.control.Option;
import java.util.function.Function;

public interface Game {

    static Game zero() {
        return new LaufendesGame(SpielerPunktestand.zero(), GegnerPunktestand.zero());
    }

    static Game of(Punkt spielerPunkte, Punkt gegnerPunkte) {
        return of(new SpielerPunktestand(new Punkte(new Schnubbi(spielerPunkte))),
            new GegnerPunktestand(new Punkte(new Schnubbi(gegnerPunkte))));
    }

    static Game of(SpielerPunktestand spieler, GegnerPunktestand gegner) {
        return List.of(Option.some(spieler).filter(spieler.asPredicate(punkte -> punkte.schnubbi().punkt().equals(Punkt.game))),
                Option.some(gegner).filter(gegner.asPredicate(punkt -> punkt.schnubbi().punkt().equals(Punkt.game))))
            .map(Option::isDefined)
            .reduce(Boolean::logicalOr)
            ? new AbgeschlossenesGame(spieler, gegner)
            : new LaufendesGame(spieler, gegner);
    }

    static <T> T apply(final Game game, final Function<LaufendesGame, T> laufendesGameFunction, final Function<AbgeschlossenesGame, T> abgeschlossenesGameFunction) {
        return Match(game).of(
            Case($(instanceOf(LaufendesGame.class)), laufendesGameFunction),
            Case($(instanceOf(AbgeschlossenesGame.class)), abgeschlossenesGameFunction)
        );
    }

}
