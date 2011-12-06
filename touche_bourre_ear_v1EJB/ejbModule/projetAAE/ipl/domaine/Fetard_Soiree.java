package projetAAE.ipl.domaine;

public class Fetard_Soiree {
	private static int GRANDEURBAR = 10;
	Fetard fetard;
	Bar propreBar;
	BarAdversaire barAdversaire;
	Soiree soiree;
	
	public Fetard_Soiree(Fetard fetard){
		this.fetard = fetard;
		this.propreBar = new Bar(GRANDEURBAR);
		this.barAdversaire = new BarAdversaire(GRANDEURBAR);
	}
	
	public Fetard getFetard1() {
		return fetard;
	}

}
