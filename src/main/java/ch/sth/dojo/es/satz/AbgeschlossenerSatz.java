/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.es.satz;

public record AbgeschlossenerSatz(int punkteSpieler, int punkteGegner) implements Satz {
    public static AbgeschlossenerSatz AbgeschlossenerSatz(final int punkteSpieler, final int punkteGegner) {
        return new AbgeschlossenerSatz(punkteSpieler, punkteGegner);
    }
}
