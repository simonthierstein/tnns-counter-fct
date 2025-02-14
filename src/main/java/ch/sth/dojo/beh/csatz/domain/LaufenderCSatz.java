package ch.sth.dojo.beh.csatz.domain;

import ch.sth.dojo.beh.cgame.domain.LaufendesCGame;

public record LaufenderCSatz(SpielerPunkteBisSatz spielerPunkteBisSatz, GegnerPunkteBisSatz gegnerPunkteBisSatz, LaufendesCGame currentGame) implements CSatz {

}
