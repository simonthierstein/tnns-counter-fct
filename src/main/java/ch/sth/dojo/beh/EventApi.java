package ch.sth.dojo.beh;

import ch.sth.dojo.beh.domain.Game;
import ch.sth.dojo.beh.evt.DomainEvent;
import ch.sth.dojo.beh.evt.GegnerPunktGewonnen;
import ch.sth.dojo.beh.evt.SpielerPunktGewonnen;
import io.vavr.Function2;
import io.vavr.control.Either;

public interface EventApi {

    Function2<GegnerPunktGewonnen, Game, Either<DomainProblem, Game>> gegnerPunktet = DomainEvent::handleEvent;
    Function2<SpielerPunktGewonnen, Game, Either<DomainProblem, Game>> spielerPunktet = DomainEvent::handleEvent;

}
