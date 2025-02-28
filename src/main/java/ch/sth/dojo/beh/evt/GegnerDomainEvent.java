/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2025.
 */

package ch.sth.dojo.beh.evt;

public sealed interface GegnerDomainEvent extends DomainEvent permits GegnerGameGewonnen, GegnerMatchGewonnen, GegnerPunktGewonnen, GegnerSatzGewonnen {

}
