package ch.sth.dojo.beh.domain;

public record AbgeschlossenesGame(SpielerPunktestand spieler, GegnerPunktestand gegner) implements Game {

}
