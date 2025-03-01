/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.evt;

import java.util.function.Function;

public sealed interface GegnerDomainEvent extends DomainEvent permits GegnerGameGewonnen, GegnerMatchGewonnen, GegnerPunktGewonnen, GegnerSatzGewonnen {

    static <T> T apply(GegnerDomainEvent event,
        Function<GegnerGameGewonnen, T> f1,
        Function<GegnerMatchGewonnen, T> f2,
        Function<GegnerPunktGewonnen, T> f3,
        Function<GegnerSatzGewonnen, T> f4
    ) {
        switch (event) {
            case GegnerGameGewonnen evt -> f1.apply(evt);
            case GegnerMatchGewonnen evt -> f2.apply(evt);
            case GegnerPunktGewonnen evt -> f3.apply(evt);
            case GegnerSatzGewonnen evt -> f4.apply(evt);
        }
    }
}
