/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.game;

import ch.sth.dojo.es.DomainError;
import ch.sth.dojo.es.events.DomainEvent;
import io.vavr.Function2;
import io.vavr.collection.List;
import io.vavr.control.Either;
import java.util.function.Function;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode @ToString
public class AbgeschlossenesGame implements Game {
    private final List<Punkt> punkteSpieler;
    private final List<Punkt> punkteGegner;

    static Function2<List<Punkt>, List<Punkt>, AbgeschlossenesGame> abgeschlossenesGame() {
        return AbgeschlossenesGame::new;
    }

    static Function<AbgeschlossenesGame, Either<DomainError, DomainEvent>> handleSpielerPunktet() {
        return AbgeschlossenesGame::handleSpielerPunktet;
    }

    private static Either<DomainError, DomainEvent> handleSpielerPunktet(final AbgeschlossenesGame prev) {
        return Either.left(new DomainError.InvalidCommandForState(prev, "handleSpielerPunktet"));
    }
}
