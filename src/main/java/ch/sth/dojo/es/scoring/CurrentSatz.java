/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.scoring;

import ch.sth.dojo.es.satz.Satz;

public record CurrentSatz(Satz current) {
    static CurrentSatz zero() {
        return new CurrentSatz(Satz.zero());
    }

    public static CurrentSatz currentSatz(Satz current) {
        return new CurrentSatz(current);
    }
}
