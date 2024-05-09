package ch.sth.dojo.es;

import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.game.Game;

public interface DomainError {

    record InvalidEventForState(Game state, DomainEvent event) implements DomainError {
    }
}
