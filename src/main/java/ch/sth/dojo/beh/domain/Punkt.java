package ch.sth.dojo.beh.domain;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

import ch.sth.dojo.beh.DomainProblem;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.state.StateHandler;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Either;
import io.vavr.control.Option;

public interface Punkt {

    static Either<DomainProblem, Tuple2<IncrementedPunkt, AdaptedPunkt>> inc(ToIncrementPunkt toIncrement, NotIncrementPunkt gegnerPunkt) {
        return StateHandler.doInc(toIncrement.value(), gegnerPunkt.value())
            .map(x -> x.map(IncrementedPunkt::from, AdaptedPunkt::from));

    }

    static Either<DomainProblem, DomainEvent> gameAbgeschlossenError() {
        return left(DomainProblem.gameAbgeschlossen);
    }

    static Either<DomainProblem, Tuple2<Punkt, Punkt>> rightTuple(Punkt spieler, Punkt gegner) {
        return right(Tuple.of(spieler, gegner));
    }

    static Either<DomainProblem, Punkt> of(String punkteString) {
        Option<Punkt> x1 = Option.narrow(P00.of(punkteString));
        Option<Punkt> x2 = Option.narrow(P15.of(punkteString));
        Option<Punkt> x3 = Option.narrow(P30.of(punkteString));

        return x1.orElse(x2).orElse(x3).toEither(DomainProblem.punktNotValid);
    }

    record P00() implements Punkt {

        private static Option<P00> of(String value) {
            return Option.when("00".equals(value), p00);
        }
    }

    record P15() implements Punkt {

        private static Option<P15> of(String value) {
            return Option.when("15".equals(value), p15);
        }
    }

    record P30() implements Punkt {

        private static Option<P30> of(String value) {
            return Option.when("30".equals(value), p30);
        }

    }

    record P40() implements Punkt {

    }

    record DEUCE() implements Punkt {

    }

    record Ad() implements Punkt {

    }

    record DisAd() implements Punkt {

    }

    record Game() implements Punkt {

    }

    P00 p00 = new P00();
    P15 p15 = new P15();
    P30 p30 = new P30();
    P40 p40 = new P40();
    DEUCE deuce = new DEUCE();
    Ad ad = new Ad();
    DisAd disAd = new DisAd();
    Game game = new Game();

}
