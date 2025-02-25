package ch.sth.dojo.beh.shared.domain;

import static ch.sth.dojo.beh.shared.domain.GewinnerVerlierer.compose;

import java.util.function.Predicate;

public record Verlierer(Integer value) {

    public static final Predicate<Verlierer> lte4 = compose(GewinnerVerlierer.lte4, Verlierer::value);
    public static final Predicate<Verlierer> lte5 = compose(GewinnerVerlierer.lte5, Verlierer::value);
    public static final Predicate<Verlierer> eq5 = compose(GewinnerVerlierer.eq5, Verlierer::value);
    public static final Predicate<Verlierer> eq6 = compose(GewinnerVerlierer.eq6, Verlierer::value);

}