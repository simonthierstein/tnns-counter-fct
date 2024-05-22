package ch.sth.dojo.beh.state;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.domain.AbgeschlossenesGame;
import ch.sth.dojo.beh.domain.Game;
import ch.sth.dojo.beh.domain.GegnerPunktestand;
import ch.sth.dojo.beh.domain.LaufenderSatz;
import ch.sth.dojo.beh.domain.LaufendesGame;
import ch.sth.dojo.beh.domain.NotIncrementPunkt;
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
import ch.sth.dojo.beh.domain.SpielerSatzPunktestand;
import ch.sth.dojo.beh.domain.ToIncrementPunkt;
import io.vavr.Tuple2;
import io.vavr.control.Either;
import java.util.function.Function;

public interface StateHandler {

    static Either<DomainProblem, Game> gegnerPunktet(Game game) {
        return Game.apply(game, StateHandler::gegnerPunktet, x -> left(DomainProblem.gameAbgeschlossen));
    }

    static Either<DomainProblem, Game> spielerPunktet(Game game) {
        return Game.apply(game, StateHandler::spielerPunktet, x -> left(DomainProblem.gameAbgeschlossen));
    }

    static Either<DomainProblem, Game> spielerGameGewonnen(Game prev) {
        return Game.apply(prev,
            laufendesGame -> right(
                new AbgeschlossenesGame(
                    new SpielerPunktestand(new Punkte(new Schnubbi(Punkt.game))),
                    laufendesGame.gegnerPunktestand())),
            abgeschlossenesGame -> left(DomainProblem.gameAbgeschlossen));
    }

    static Either<DomainProblem, Game> gegnerGameGewonnen(Game prev) {
        return Game.apply(prev,
            laufendesGame -> right(
                new AbgeschlossenesGame(laufendesGame.spielerPunktestand(),
                    new GegnerPunktestand(new Punkte(new Schnubbi(Punkt.game))))),
            abgeschlossenesGame -> left(DomainProblem.gameAbgeschlossen));

    }

    static Either<DomainProblem, Game> spielerPunktet(final LaufendesGame game) {
        return Punkt.inc(game.spielerPunktestand().punkte().schnubbi().eval(ToIncrementPunkt::new),
                game.gegnerPunktestand().eval(Function.identity()).eval(Function.identity()).eval(NotIncrementPunkt::new))
            .map(t4 -> t4.map(
                incrementedPunkt -> incrementedPunkt.eval(Schnubbi::new),
                adaptedPunkt -> adaptedPunkt.eval(Schnubbi::new)))
            .map(t4 -> t4.map(Punkte::new, Punkte::new))
            .map(t3 -> t3.map(SpielerPunktestand::new, GegnerPunktestand::new))
            .map(t2 -> t2.apply(Game::of));
    }

    static Either<DomainProblem, Game> gegnerPunktet(final LaufendesGame game) {
        return Punkt.inc(game.gegnerPunktestand().punkte().schnubbi().eval(ToIncrementPunkt::new),
                game.spielerPunktestand().punkte().schnubbi().eval(NotIncrementPunkt::new))
            .map(t2 -> Game.of(
                new SpielerPunktestand(new Punkte(t2._2.eval(Schnubbi::new))),
                new GegnerPunktestand(new Punkte(t2._1.eval(Schnubbi::new)))
            ));
    }

    static Satz spielerGameGewonnen(LaufenderSatz prev) {
        final SpielerSatzPunktestand nextSpielerSatzPunktestand = prev.spielerSatzPunktestand().plusEins();
        return new LaufenderSatz(nextSpielerSatzPunktestand, prev.gegnerSatzPunktestand());
    }

    static Satz gegnerGameGewonnen(LaufenderSatz prev) {
        return new LaufenderSatz(prev.spielerSatzPunktestand(), prev.gegnerSatzPunktestand().plusEins());
    }

    static Either<DomainProblem, Tuple2<Punkt, Punkt>> doInc(final Punkt toIncrement, final Punkt gegnerPunkt) {
        return Match(toIncrement).of(
            Case($(instanceOf(P00.class)), x -> Punkt.rightTuple(Punkt.p15, gegnerPunkt)),
            Case($(instanceOf(P15.class)), x -> Punkt.rightTuple(Punkt.p30, gegnerPunkt)),
            Case($(instanceOf(P30.class)), x -> gegnerPunkt.equals(Punkt.p40) ? Punkt.rightTuple(Punkt.deuce, Punkt.deuce) : Punkt.rightTuple(Punkt.p40, gegnerPunkt)),
            Case($(instanceOf(P40.class)), x -> Punkt.rightTuple(Punkt.game, gegnerPunkt)),
            Case($(instanceOf(DEUCE.class)), x -> Punkt.rightTuple(Punkt.ad, Punkt.disAd)),
            Case($(instanceOf(Ad.class)), x -> Punkt.rightTuple(Punkt.game, gegnerPunkt)),
            Case($(instanceOf(DisAd.class)), x -> Punkt.rightTuple(Punkt.deuce, Punkt.deuce)),
            Case($(instanceOf(Punkt.Game.class)), x -> left(DomainProblem.gameAbgeschlossen))
        );
    }
}
