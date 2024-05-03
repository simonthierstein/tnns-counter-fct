package ch.sth.dojo.gamefct;

public interface GameAggregateRoot {
    static Game spielerPunktet(Game prev) {
        return prev.spielerPunktet();
    }
}
