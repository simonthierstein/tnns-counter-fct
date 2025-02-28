package ch.sth.dojo.beh.cmatch.domain;

import ch.sth.dojo.beh.evt.DomainEvent;

public interface CMatch {

    static CMatch handleEvent(CMatch cMatch, DomainEvent event) {
        return null;
    }
}
