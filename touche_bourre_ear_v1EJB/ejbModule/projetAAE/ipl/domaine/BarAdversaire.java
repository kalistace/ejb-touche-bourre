package projetAAE.ipl.domaine;


public class BarAdversaire {
	private int[][] matrice;
	private int grandeurBar;

	public BarAdversaire(int grandeurBar) {
		this.grandeurBar =  grandeurBar;
		matrice = new int[grandeurBar][grandeurBar];
	}
}
