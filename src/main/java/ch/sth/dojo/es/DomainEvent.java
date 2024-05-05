package ch.sth.dojo.es;

public sealed interface DomainEvent permits GegnerHatGameGewonnen, GegnerHatPunktGewonnen, SpielerHatGameGewonnen, SpielerHatPunktGewonnen {

}