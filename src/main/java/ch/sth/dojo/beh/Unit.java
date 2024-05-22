/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2024.
 */

package ch.sth.dojo.beh;

import lombok.NoArgsConstructor;

@NoArgsConstructor public final class Unit {

    private static final Unit unit = new Unit();

    public static Unit Unit() {
        return unit;
    }
}
