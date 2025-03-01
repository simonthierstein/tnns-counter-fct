package ch.sth.dojo.beh.cmatch.domain;

public interface CMatch {

    static CMatch zero() {
        return new LaufendesMatch();
    }

    class LaufendesMatch implements CMatch {

    }
}

