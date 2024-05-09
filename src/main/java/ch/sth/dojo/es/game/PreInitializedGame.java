/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.game;

import static ch.sth.dojo.es.events.GameErzeugt.gameErzeugt;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.commands.DomainCommand;
import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.events.GameErzeugt;
import io.vavr.control.Either;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode @ToString
public class PreInitializedGame implements Game {

    static PreInitializedGame preInitializedGame() {
        return new PreInitializedGame();
    }

    public GameErzeugt erzeugeGame() {
        return gameErzeugt();
    }

    LaufendesGame handleEvent(DomainEvent domainEvent) {
        return Match(domainEvent).of(
                Case($(instanceOf(GameErzeugt.class)), x -> LaufendesGame.initial()),
                Case($(), x -> (LaufendesGame) Game.throwException(this, x))
        );
    }

    Either<DomainError, DomainEvent> handleCommand(final DomainCommand command) {
        return DomainCommand.handleCommand(
                command,
                () -> Either.left(new InvalidCommandForState(this, command)),
                () -> Either.left(new InvalidCommandForState(this, command)),
                () -> Either.right(erzeugeGame()));
    }
}
