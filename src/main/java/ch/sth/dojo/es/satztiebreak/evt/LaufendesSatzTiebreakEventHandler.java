/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.satztiebreak.evt;


import static ch.sth.dojo.es.Util.none;

import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.events.GegnerHatPunktGewonnen;
import ch.sth.dojo.es.events.SpielerHatPunktGewonnen;
import ch.sth.dojo.es.satztiebreak.LaufendesSatzTiebreak;
import ch.sth.dojo.es.satztiebreak.SatzTiebreak;
import io.vavr.control.Option;

public class LaufendesSatzTiebreakEventHandler {

    static Option<SatzTiebreak> handle(final LaufendesSatzTiebreak prev, final DomainEvent event) {
        final Option<SatzTiebreak> objects = DomainEvent.handleEvent(
                event,
                evt -> Option.some(spielerHatPunktGewonnen(prev, evt)),
                evt -> Option.some(gegnerHatPunktGewonnen(prev, evt)),
                none(),
                none(),
                none(),
                none(),
                none(),
                none(),
                none()
        );

        return objects;
    }

    private static SatzTiebreak spielerHatPunktGewonnen(final LaufendesSatzTiebreak prev, final SpielerHatPunktGewonnen evt) {
        return LaufendesSatzTiebreak.incrementSpieler(prev);
    }

    private static SatzTiebreak gegnerHatPunktGewonnen(final LaufendesSatzTiebreak prev, final GegnerHatPunktGewonnen evt) {
        return LaufendesSatzTiebreak.incrementGegner(prev);
    }
}

