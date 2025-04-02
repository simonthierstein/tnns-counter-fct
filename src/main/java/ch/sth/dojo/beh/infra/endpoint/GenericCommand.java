/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.infra.endpoint;

import java.util.UUID;

public record GenericCommand(UUID commandId, String command) {

}
