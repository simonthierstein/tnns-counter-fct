/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.infra;

import java.util.UUID;

public record GenericCommand(UUID commandId, String command) {

}
