package ch.sth.dojo.es;

import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.game.Game;
import ch.sth.dojo.es.satz.Satz;

public interface DomainError {

    record InvalidEventForGame(Game state, DomainEvent event) implements DomainError {
    }

    record InvalidCommandForGame(Game state, String command) implements DomainError {
    }

    record InvalidEventForSatz(Satz state, DomainEvent event) implements DomainError {
    }

    record InvalidCommandForSatz(Satz state, String command) implements DomainError {
    }

    record InvalidEventForMatch() implements DomainError {
    }

    record InvalidStateForMatch() implements DomainError {
    }
}
