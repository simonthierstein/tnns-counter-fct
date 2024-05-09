package ch.sth.dojo.es.events;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

import ch.sth.dojo.es.game.Game;
import java.util.function.Function;

public sealed interface DomainEvent permits GameErzeugt, GegnerHatGameGewonnen, GegnerHatPunktGewonnen, SpielerHatGameGewonnen, SpielerHatPunktGewonnen {

    static Game handleEvent(final DomainEvent elem,
                            Function<SpielerHatPunktGewonnen, Game> f1,
                            Function<GegnerHatPunktGewonnen, Game> f2,
                            Function<SpielerHatGameGewonnen, Game> f3,
                            Function<GegnerHatGameGewonnen, Game> f4) {
        return Match(elem).of(
                Case($(instanceOf(SpielerHatPunktGewonnen.class)), f1),
                Case($(instanceOf(GegnerHatPunktGewonnen.class)), f2),
                Case($(instanceOf(SpielerHatGameGewonnen.class)), f3),
                Case($(instanceOf(GegnerHatGameGewonnen.class)), f4)
        );
    }

}