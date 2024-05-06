/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.commands;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

import ch.sth.dojo.es.events.DomainEvent;
import java.util.function.Function;
import java.util.function.Supplier;

public interface DomainCommand {
    static DomainEvent handleCommand(DomainCommand command,
                                     Function<SpielerPunktet, DomainEvent> spielerPunktetDomainEventFunction,
                                     Function<GegnerPunktet, DomainEvent> gegnerPunktetDomainEventFunction) {
        return Match(command).of(
                Case($(instanceOf(SpielerPunktet.class)), spielerPunktetDomainEventFunction),
                Case($(instanceOf(GegnerPunktet.class)), gegnerPunktetDomainEventFunction)
        );

    }

    static DomainEvent handleCommand(DomainCommand command,
                                     Supplier<DomainEvent> spielerPunktetDomainEventFunction,
                                     Supplier<DomainEvent> gegnerPunktetDomainEventFunction) {
        return Match(command).of(
                Case($(instanceOf(SpielerPunktet.class)), spielerPunktetDomainEventFunction),
                Case($(instanceOf(GegnerPunktet.class)), gegnerPunktetDomainEventFunction)
        );

    }
}
