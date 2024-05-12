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

    public static AbgeschlossenesGame abgeschlossenesGame(final List<Punkt> punkteSpieler, final List<Punkt> punkteGegner) {
        return new AbgeschlossenesGame(punkteSpieler, punkteGegner);
    }

}
