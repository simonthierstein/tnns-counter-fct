/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.gamefct.infra;

import ch.sth.dojo.es.Game;
import ch.sth.dojo.es.commands.DomainCommand;

public record InvalidCommandForState(DomainCommand command, Game state) implements DomainError {
}
