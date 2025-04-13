/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.infra.endpoint;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.function.Function;

class ToCommandConverter {

    static Function<? super Map<String, Object>, InfraCommand> buildCommand(ObjectMapper objectMapper) {
        return raw -> routeCommandMap(raw, objectMapper);

    }

    private static InfraCommand routeCommandMap(final Map<String, Object> raw, ObjectMapper mapper) {
        return Match(raw.get("command")).of(
            Case($("SpielerPunktet"), () -> parseCommand(mapper, SpielerPunktetCommand.class).apply(raw)),
            Case($("GegnerPunktet"), () -> parseCommand(mapper, GegnerPunktetCommand.class).apply(raw))
        );
    }

    private static Function<Map<String, Object>, InfraCommand> parseCommand(ObjectMapper objectMapper, final Class<? extends InfraCommand> toValueType) {
        return raw -> objectMapper.convertValue(raw, toValueType);
    }
}
