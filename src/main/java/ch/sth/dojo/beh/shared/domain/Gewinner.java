/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.shared.domain;

import static ch.sth.dojo.beh.shared.domain.GewinnerVerlierer.compose;

import java.util.function.Predicate;

public record Gewinner(Integer value) {

    public static final Predicate<Gewinner> lte4 = compose(GewinnerVerlierer.lte4, Gewinner::value);
    public static final Predicate<Gewinner> lte5 = compose(GewinnerVerlierer.lte5, Gewinner::value);
    public static final Predicate<Gewinner> eq5 = compose(GewinnerVerlierer.eq5, Gewinner::value);
    public static final Predicate<Gewinner> eq6 = compose(GewinnerVerlierer.eq6, Gewinner::value);

}
