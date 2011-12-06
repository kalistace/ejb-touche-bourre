package projetAAE.ipl.domaine;

public class Soiree {

	Fetard_Soiree fetard1;
	Fetard_Soiree fetard2;

	public Soiree(Fetard_Soiree fetard1) {
		this.fetard1 = fetard1;
	}

	public void rejoindrePartie(Fetard_Soiree fetard2) {
		this.fetard2 = fetard2;
	}
}
