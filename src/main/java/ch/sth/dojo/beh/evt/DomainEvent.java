package ch.sth.dojo.beh.evt;

import java.util.function.Function;

public sealed interface DomainEvent permits GegnerDomainEvent, SpielerDomainEvent {

    static <T> T apply(DomainEvent target,
        Function<SpielerGameGewonnen, T> f1,
        Function<SpielerMatchGewonnen, T> f2,
        Function<SpielerPunktGewonnen, T> f3,
        Function<SpielerSatzGewonnen, T> f4,
        Function<GegnerGameGewonnen, T> f5,
        Function<GegnerMatchGewonnen, T> f6,
        Function<GegnerPunktGewonnen, T> f7,
        Function<GegnerSatzGewonnen, T> f8
    ) {
        return switch (target) {
            case SpielerDomainEvent spielerDomainEvent -> SpielerDomainEvent.apply(spielerDomainEvent, f1, f2, f3, f4);
            case GegnerDomainEvent gegnerDomainEvent -> GegnerDomainEvent.apply(gegnerDomainEvent, f5, f6, f7, f8);
        };
    }

    static <I extends DomainEvent> DomainEvent narrow(I domainEvent) {
        return domainEvent;
    }
}

