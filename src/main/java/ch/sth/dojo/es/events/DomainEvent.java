package ch.sth.dojo.es.events;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

import io.vavr.Function2;
import java.util.function.Function;

public sealed interface DomainEvent permits GameErzeugt, GegnerHatGameGewonnen, GegnerHatMatchGewonnen, GegnerHatPunktGewonnen, GegnerHatSatzGewonnen,
        SpielerHatGameGewonnen, SpielerHatMatchGewonnen, SpielerHatPunktGewonnen, SpielerHatSatzGewonnen {

    static <T> T handleEvent(final DomainEvent elem,
                             Function<SpielerHatPunktGewonnen, T> f1,
                             Function<GegnerHatPunktGewonnen, T> f2,
                             Function<SpielerHatGameGewonnen, T> f3,
                             Function<GegnerHatGameGewonnen, T> f4,
                             Function<GameErzeugt, T> f5,
                             Function<SpielerHatSatzGewonnen, T> f6,
                             Function<GegnerHatSatzGewonnen, T> f7,
                             Function<SpielerHatMatchGewonnen, T> f8,
                             Function<GegnerHatMatchGewonnen, T> f9
    ) {
        return Match(elem).of(
                Case($(instanceOf(SpielerHatPunktGewonnen.class)), f1),
                Case($(instanceOf(GegnerHatPunktGewonnen.class)), f2),
                Case($(instanceOf(SpielerHatGameGewonnen.class)), f3),
                Case($(instanceOf(GegnerHatGameGewonnen.class)), f4),
                Case($(instanceOf(GameErzeugt.class)), f5),
                Case($(instanceOf(SpielerHatSatzGewonnen.class)), f6),
                Case($(instanceOf(GegnerHatSatzGewonnen.class)), f7),
                Case($(instanceOf(SpielerHatMatchGewonnen.class)), f8),
                Case($(instanceOf(GegnerHatMatchGewonnen.class)), f9)
        );
    }

    static <S, T> T handleEventF2(final DomainEvent elem, S state,
                                  Function2<S, SpielerHatPunktGewonnen, T> f1,
                                  Function2<S, GegnerHatPunktGewonnen, T> f2,
                                  Function2<S, SpielerHatGameGewonnen, T> f3,
                                  Function2<S, GegnerHatGameGewonnen, T> f4,
                                  Function2<S, GameErzeugt, T> f5,
                                  Function2<S, SpielerHatSatzGewonnen, T> f6,
                                  Function2<S, GegnerHatSatzGewonnen, T> f7,
                                  Function2<S, SpielerHatMatchGewonnen, T> f8,
                                  Function2<S, GegnerHatMatchGewonnen, T> f9
    ) {
        return Match(elem).of(
                Case($(instanceOf(SpielerHatPunktGewonnen.class)), f1.apply(state)),
                Case($(instanceOf(GegnerHatPunktGewonnen.class)), f2.apply(state)),
                Case($(instanceOf(SpielerHatGameGewonnen.class)), f3.apply(state)),
                Case($(instanceOf(GegnerHatGameGewonnen.class)), f4.apply(state)),
                Case($(instanceOf(GameErzeugt.class)), f5.apply(state)),
                Case($(instanceOf(SpielerHatSatzGewonnen.class)), f6.apply(state)),
                Case($(instanceOf(GegnerHatSatzGewonnen.class)), f7.apply(state)),
                Case($(instanceOf(SpielerHatMatchGewonnen.class)), f8.apply(state)),
                Case($(instanceOf(GegnerHatMatchGewonnen.class)), f9.apply(state)));
    }

}