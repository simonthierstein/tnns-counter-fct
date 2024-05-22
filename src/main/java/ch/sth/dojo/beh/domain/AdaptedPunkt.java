package ch.sth.dojo.beh.domain;

import java.util.function.Function;

public record AdaptedPunkt(Punkt value) {

    static AdaptedPunkt from(Punkt punkt) {
        return new AdaptedPunkt(punkt);
    }

    public <T> T eval(Function<Punkt, T> mapper) {
        return mapper.apply(value);
    }

}
