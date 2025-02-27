package ch.sth.dojo.beh.evt;

public sealed interface DomainEvent permits GameGestartet, GegnerDomainEvent, SpielerDomainEvent {

    static <I extends DomainEvent> DomainEvent narrow(I domainEvent) {
        return domainEvent;
    }
}

