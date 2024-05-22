package ch.sth.dojo.beh.evt;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;
import static io.vavr.control.Either.left;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.domain.Game;
import ch.sth.dojo.beh.domain.LaufenderSatz;
import ch.sth.dojo.beh.domain.LaufendesGame;
import ch.sth.dojo.beh.domain.Punkt;
import ch.sth.dojo.beh.domain.Punkt.Ad;
import ch.sth.dojo.beh.domain.Punkt.DEUCE;
import ch.sth.dojo.beh.domain.Punkt.DisAd;
import ch.sth.dojo.beh.domain.Punkt.P00;
import ch.sth.dojo.beh.domain.Punkt.P15;
import ch.sth.dojo.beh.domain.Punkt.P30;
import ch.sth.dojo.beh.domain.Punkt.P40;
import ch.sth.dojo.beh.domain.Punkte;
import ch.sth.dojo.beh.domain.Satz;
import ch.sth.dojo.beh.domain.Schnubbi;
import ch.sth.dojo.beh.domain.SpielerPunktestand;
import ch.sth.dojo.beh.domain.ToIncrementPunkt;
import ch.sth.dojo.beh.state.StateHandler;
import io.vavr.control.Either;
import java.util.function.Function;

public interface EventHandler {

    static Either<DomainProblem, DomainEvent> spielerPunktetToEvent(Game prev) {
        return Game.apply(prev,
            EventHandler::spielerPunktetToEvent,
            abgeschlossenesGame -> left(DomainProblem.gameAbgeschlossen)
        );
    }

    static Either<DomainProblem, DomainEvent> gegnerPunktetToEvent(Game prev) {
        return Game.apply(prev,
            EventHandler::gegnerPunktetToEvent,
            abgeschlossenesGame -> left(DomainProblem.gameAbgeschlossen)
        );
    }

    static Either<DomainProblem, DomainEvent> spielerPunktetToEvent(final LaufendesGame game) {
        return punktetToEvent(game.spielerPunktestand());
    }

    static Either<DomainProblem, DomainEvent> gegnerPunktetToEvent(final LaufendesGame game) {
        return incToEvent(game.gegnerPunktestand().punkte().schnubbi().eval(ToIncrementPunkt::new), p -> Either.right(new GegnerPunktGewonnen()),
            punkt -> Either.right(new GegnerGameGewonnen()));
    }

    static Satz handleEvent(LaufenderSatz prev, GameDomainEvent event) {
        return switch (event) {
            case SpielerGameGewonnen x -> StateHandler.spielerGameGewonnen(prev);
            case GegnerGameGewonnen x -> StateHandler.gegnerGameGewonnen(prev);
        };
    }

    static Either<DomainProblem, DomainEvent> spielerPunktetToEvent(final Schnubbi punkter) {
        return incToEvent(punkter.eval(ToIncrementPunkt::new),
            punkt1 -> Either.right(new SpielerPunktGewonnen()),
            punkt1 -> Either.right(new SpielerGameGewonnen()));
    }

    static Either<DomainProblem, DomainEvent> punktetToEvent(final SpielerPunktestand spielerPunktestand) {
        return spielerPunktetToEvent(spielerPunktestand.punkte());
    }

    static Either<DomainProblem, DomainEvent> spielerPunktetToEvent(final Punkte punkter) {
        return spielerPunktetToEvent(punkter.schnubbi());
    }

    static Either<DomainProblem, DomainEvent> incToEvent(ToIncrementPunkt toIncrement, final Function<Punkt, Either<DomainProblem, DomainEvent>> punktFct,
        final Function<Punkt, Either<DomainProblem, DomainEvent>> gameFct) {
        return doIncToEvent(toIncrement.value(), punktFct, gameFct);
    }

    static Either<DomainProblem, DomainEvent> doIncToEvent(final Punkt toIncrement,
        Function<Punkt, Either<DomainProblem, DomainEvent>> punktGewonnen,
        Function<Punkt, Either<DomainProblem, DomainEvent>> gameGewonnen
    ) {
        return Match(toIncrement).of(
            Case($(instanceOf(P00.class)), punktGewonnen),
            Case($(instanceOf(P15.class)), punktGewonnen),
            Case($(instanceOf(P30.class)), punktGewonnen),
            Case($(instanceOf(P40.class)), gameGewonnen),
            Case($(instanceOf(DEUCE.class)), punktGewonnen),
            Case($(instanceOf(Ad.class)), gameGewonnen),
            Case($(instanceOf(DisAd.class)), punktGewonnen),
            Case($(instanceOf(Punkt.Game.class)), Punkt::gameAbgeschlossenError)
        );
    }
}
