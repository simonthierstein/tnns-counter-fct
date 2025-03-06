package ch.sth.dojo.beh;

import io.vavr.control.Either;
import java.util.function.Function;

public interface DomainProblem {

    DomainProblem spielerPunktetFehlgeschlagen = new SpielerPunktetFehlgeschlagen();
    DomainProblem gameAbgeschlossen = new GameAbgeschlossenProblem();
    DomainProblem gameNotInitialized = new GameNotInitializedProblem();
    DomainProblem punktNotValid = new PunktNotValidProblem();
    DomainProblem satzPunktNotValid = new SatzPunktNotValid();
    DomainProblem eventNotValid = new EventNotValid();
    DomainProblem transitionNotValid = new TransitionNotValid();
    DomainProblem valueNotValid = new ValueNotValid();
    DomainProblem nullValueNotValid = new NullValueNotValid();
    DomainProblem NYIMP = new NotYetImplemented();

    record NotYetImplemented() implements DomainProblem {

    }

    record SpielerPunktetFehlgeschlagen() implements DomainProblem {

    }

    record GameAbgeschlossenProblem() implements DomainProblem {

    }

    record GameNotInitializedProblem() implements DomainProblem {

    }

    record PunktNotValidProblem() implements DomainProblem {

    }

    record SatzPunktNotValid() implements DomainProblem {

    }

    record TransitionNotValid() implements DomainProblem {

    }

    record EventNotValid() implements DomainProblem {

    }

    record ValueNotValid() implements DomainProblem {

    }

    record NullValueNotValid() implements DomainProblem {

    }

    static <T> Function<T, Either<DomainProblem, T>> asLeft(DomainProblem domainProblem) {
        return t -> Either.left(domainProblem);
    }

}
