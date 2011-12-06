package projetAAE.ipl.domaine;

public class Fetard {
	private String pseudo;

	private Bar monBar;

	private BarAdversaire barAdversaire;

	public Fetard(String pseudo) {
		this.pseudo = pseudo;
		this.monBar = null;
		this.barAdversaire = null;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void initialiserBar() {
		monBar = new Bar(10);
		barAdversaire =  new BarAdversaire(10);
	}

}
