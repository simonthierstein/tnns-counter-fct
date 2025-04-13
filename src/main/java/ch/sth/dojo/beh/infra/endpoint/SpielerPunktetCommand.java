/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.infra.endpoint;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record SpielerPunktetCommand(
    @JsonProperty(required = true) UUID commandId,
    @JsonProperty(required = true) String command
) implements InfraCommand {

}
