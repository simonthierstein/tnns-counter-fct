/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.cgame.domain;

import static ch.sth.dojo.beh.shsared.domain.GewinnerVerlierer.of;

import ch.sth.dojo.beh.shsared.domain.Gewinner;
import ch.sth.dojo.beh.shsared.domain.GewinnerVerlierer;
import ch.sth.dojo.beh.shsared.domain.StateTransition;
import ch.sth.dojo.beh.shsared.domain.Verlierer;
import io.vavr.collection.List;
import java.util.function.Function;
import java.util.function.Predicate;

public final class CGameStateTransitions {

    private static final Predicate<GewinnerVerlierer> deuce30AllCondition = t2 ->
        t2.gewinner().value() == 2 && t2.verlierer().value() == 2;
    private static final Function<GewinnerVerlierer, GewinnerVerlierer> deuce30AllTransition = t2 ->
        of(new Gewinner(1), new Verlierer(3));
    private static final Predicate<GewinnerVerlierer> breakpointCondition = t2 ->
        t2.gewinner().value() == 3 && t2.verlierer().value() == 1;
    private static final Function<GewinnerVerlierer, GewinnerVerlierer> breakpointTransition =
        t2 -> of(new Gewinner(2), new Verlierer(2));
    private static final Predicate<GewinnerVerlierer> standardCondition = t2 ->
        (t2.gewinner().value() >= 2 && t2.verlierer().value() >= 3)
            || (t2.gewinner().value() >= 3 && t2.verlierer().value() == 2)
            || (t2.gewinner().value() >= 4 && t2.verlierer().value() == 1);
    private static final Function<GewinnerVerlierer, GewinnerVerlierer> standardTransition =
        t2 -> of(new Gewinner(t2.gewinner().value() - 1), t2.verlierer());

    static List<StateTransition> stateTransitions = List.of(
        new StateTransition(deuce30AllCondition, deuce30AllTransition),
        new StateTransition(breakpointCondition, breakpointTransition),
        new StateTransition(standardCondition, standardTransition)
    );
}
