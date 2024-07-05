/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.satztiebreak.evt;

import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.satztiebreak.SatzTiebreak;
import io.vavr.control.Option;

public interface SatzTiebreakEventHandler {

    static Option<SatzTiebreak> handleEvent(SatzTiebreak state, DomainEvent event) {
        return SatzTiebreak.apply(state,
                prev -> LaufendesSatzTiebreakEventHandler.handle(prev, event),
                prev -> AbgeschlossenesSatzTiebreakEventHandler.handle(prev, event)
        );
    }


}
