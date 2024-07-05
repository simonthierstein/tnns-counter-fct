/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.satztiebreak.evt;

import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.satztiebreak.AbgeschlossenesSatzTiebreak;
import ch.sth.dojo.es.satztiebreak.SatzTiebreak;
import io.vavr.control.Option;

public class AbgeschlossenesSatzTiebreakEventHandler {
    static Option<SatzTiebreak> handle(final AbgeschlossenesSatzTiebreak prev, final DomainEvent event) {
        return Option.none();
    }
}
