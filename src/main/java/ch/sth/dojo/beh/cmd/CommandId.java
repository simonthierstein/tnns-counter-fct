/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cmd;

import io.vavr.control.Option;
import java.util.UUID;
import java.util.function.Function;

record CommandId(UUID value) {

    static final Function<UUID, Option<CommandId>> bind = id -> Option.of(id).map(CommandId::new);

}
