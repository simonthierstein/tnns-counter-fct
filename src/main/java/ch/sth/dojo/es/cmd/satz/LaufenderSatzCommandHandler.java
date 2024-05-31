/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.cmd.satz;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.Routing;
import ch.sth.dojo.es.Util;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.events.GegnerHatGameGewonnen;
import ch.sth.dojo.es.events.GegnerHatSatzGewonnen;
import ch.sth.dojo.es.events.SpielerHatGameGewonnen;
import ch.sth.dojo.es.events.SpielerHatSatzGewonnen;
import ch.sth.dojo.es.satz.LaufenderSatz;
import ch.sth.dojo.es.satz.Satz;
import io.vavr.control.Either;
import java.util.function.Function;

class LaufenderSatzCommandHandler {
    public static Either<DomainError, DomainEvent> handleLaufenderSatzCmd(LaufenderSatz state, DomainEvent event) {
        return DomainEvent.handleEvent(
                event,
                Util.left(Satz.eventToError(state, "handleLaufenderSatzCmd")),
                Util.left(Satz.eventToError(state, "handleLaufenderSatzCmd")),
                Util.right(x -> spielerGewinneGameCmd(state, x)),
                Util.right(x -> gegnerGewinneGameCmd(state, x)),
                Util.left(Satz.eventToError(state, "handleLaufenderSatzCmd")),
                Util.left(Satz.eventToError(state, "handleLaufenderSatzCmd")),
                Util.left(Satz.eventToError(state, "handleLaufenderSatzCmd")),
                Util.left(Satz.eventToError(state, "handleLaufenderSatzCmd")),
                Util.left(Satz.eventToError(state, "handleLaufenderSatzCmd"))
        );
    }

    private static DomainEvent spielerGewinneGameCmd(LaufenderSatz state, SpielerHatGameGewonnen event) {
        return Routing.selection(state.incrementSpieler(),
                LaufenderSatz::passIfSpielerWon,
                toAbgeschlossenEventSpieler(),
                toLaufendEvent(event));
    }

    private static DomainEvent gegnerGewinneGameCmd(final LaufenderSatz state, final GegnerHatGameGewonnen event) {
        return Routing.selection(state.incrementGegner(),
                LaufenderSatz::passIfGegnerWon,
                toAbgeschlossenEventGegner(),
                toLaufendEvent(event));
    }

    private static Function<LaufenderSatz, DomainEvent> toLaufendEvent(final DomainEvent event) {
        return prev -> event;
    }

    private static Function<LaufenderSatz, DomainEvent> toAbgeschlossenEventSpieler() {
        return prev -> SpielerHatSatzGewonnen.spielerHatSatzGewonnen();
    }

    private static Function<LaufenderSatz, DomainEvent> toAbgeschlossenEventGegner() {
        return prev -> GegnerHatSatzGewonnen.gegnerHatSatzGewonnen();
    }
}
