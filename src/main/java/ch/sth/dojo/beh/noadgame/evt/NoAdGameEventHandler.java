/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.noadgame.evt;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.noadgame.domain.NoAdGame;
import io.vavr.control.Either;

public class NoAdGameEventHandler {

    public static Either<DomainProblem, NoAdGame> handleEvent(final NoAdGame prevGame, final DomainEvent event) {
        return;
    }
}
