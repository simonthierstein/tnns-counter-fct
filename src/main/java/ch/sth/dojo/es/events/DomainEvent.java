package ch.sth.dojo.es.events;

public sealed interface DomainEvent permits GameErzeugt, GegnerHatGameGewonnen, GegnerHatPunktGewonnen, SpielerHatGameGewonnen, SpielerHatPunktGewonnen {

}