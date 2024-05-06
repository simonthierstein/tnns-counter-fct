/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

import ch.sth.dojo.es.events.DomainEvent;
import ch.sth.dojo.es.events.GameErzeugt;
import ch.sth.dojo.es.events.GegnerHatPunktGewonnen;
import ch.sth.dojo.es.events.SpielerHatPunktGewonnen;
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

    LaufendesGame handleEvent(DomainEvent domainEvent) {
        return Match(domainEvent).of(
                Case($(instanceOf(GameErzeugt.class)), x -> LaufendesGame.initial()),
                Case($(), x->(LaufendesGame) Game.throwException(this, x))
        );
    }
}
