package ch.sth.dojo.beh.cmatch.domain;

public interface CMatch {

    static CMatch zero() {
        return new LaufendesMatch();
    }

    record LaufendesMatch() implements CMatch {

    }
}

