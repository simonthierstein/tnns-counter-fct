package ch.sth.dojo.beh.domain;

import java.util.function.Function;

public record IncrementedPunkt(Punkt value) {

    static IncrementedPunkt from(Punkt punkt) {
        return new IncrementedPunkt(punkt);
    }

    public <T> T eval(Function<Punkt, T> mapper) {
        return mapper.apply(value);
    }

}
