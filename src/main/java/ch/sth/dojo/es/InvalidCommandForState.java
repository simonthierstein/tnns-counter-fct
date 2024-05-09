/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es;

import ch.sth.dojo.es.commands.DomainCommand;

public record InvalidCommandForState(Game state, DomainCommand command) implements DomainError {
}
