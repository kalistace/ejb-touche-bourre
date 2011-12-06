package projetAAE.ipl.domaine;


public class Bar {
	private Case[][] matrice;
	private int grandeurBar;

	public Bar(int grandeurBar) {
		this.grandeurBar =  grandeurBar;
		matrice = new Case[grandeurBar][grandeurBar];

		for (int i = 0; i < grandeurBar; i++) {
			for (int j = 0; j < grandeurBar; j++) {
				matrice[i][j] = new Case();
			}
		}
	}
}
