/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cmd;

import io.vavr.control.Option;
import java.time.LocalDateTime;
import java.util.function.Function;

public record WinningTimestamp(LocalDateTime value) {

    static final Function<LocalDateTime, Option<WinningTimestamp>> bind = input -> Option.of(input).map(WinningTimestamp::new);

}
