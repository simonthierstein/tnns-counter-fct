package ch.sth.dojo.beh;

public interface DomainProblem {

    DomainProblem spielerPunktetFehlgeschlagen = new SpielerPunktetFehlgeschlagen();
    DomainProblem gameAbgeschlossen = new GameAbgeschlossenProblem();
    DomainProblem gameNotInitialized = new GameNotInitializedProblem();
    DomainProblem punktNotValid = new PunktNotValidProblem();
    DomainProblem satzPunktNotValid = new SatzPunktNotValid();
    DomainProblem eventNotValid = new EventNotValid();
    DomainProblem invalidValue = new InvalidValue();
    DomainProblem InvalidEvent = new InvalidEvent();

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

    record EventNotValid() implements DomainProblem {

    }

    record InvalidValue() implements DomainProblem {

    }

    record InvalidEvent() implements DomainProblem {

    }
}
