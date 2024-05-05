package ch.sth.dojo.es.events;

public sealed interface DomainEvent permits GegnerHatGameGewonnen, GegnerHatPunktGewonnen, SpielerHatGameGewonnen, SpielerHatPunktGewonnen {

}