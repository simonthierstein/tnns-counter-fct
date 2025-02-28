package ch.sth.dojo.beh.cmatch;

import static org.assertj.core.api.Assertions.assertThat;

import ch.sth.dojo.beh.cmatch.domain.CMatch;
import ch.sth.dojo.beh.evt.DomainEvent;
import org.junit.jupiter.api.Test;

class CMatchTest {

    @Test
    void initial() {
        assertThat(CMatch.handleEvent(prevMatchState(), event()))
            .isNotNull()
            .isEqualTo(nextExpectedState());
    }

    private static CMatch nextExpectedState() {
        return null;
    }

    private static DomainEvent event() {
        return null;
    }

    private static CMatch prevMatchState() {
        return null;
    }
}