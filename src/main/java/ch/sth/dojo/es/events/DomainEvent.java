package ch.sth.dojo.es.events;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

import java.util.function.Function;

public sealed interface DomainEvent permits GameErzeugt, GegnerHatGameGewonnen, GegnerHatPunktGewonnen, SpielerHatGameGewonnen, SpielerHatPunktGewonnen {

    static <T> T handleEvent(final DomainEvent elem,
                             Function<SpielerHatPunktGewonnen, T> f1,
                             Function<GegnerHatPunktGewonnen, T> f2,
                             Function<SpielerHatGameGewonnen, T> f3,
                             Function<GegnerHatGameGewonnen, T> f4,
                             Function<GameErzeugt, T> f5
    ) {
        return Match(elem).of(
                Case($(instanceOf(SpielerHatPunktGewonnen.class)), f1),
                Case($(instanceOf(GegnerHatPunktGewonnen.class)), f2),
                Case($(instanceOf(SpielerHatGameGewonnen.class)), f3),
                Case($(instanceOf(GegnerHatGameGewonnen.class)), f4),
                Case($(instanceOf(GameErzeugt.class)), f5));
    }

}