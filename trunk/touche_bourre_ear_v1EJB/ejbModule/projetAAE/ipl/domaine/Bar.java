package projetAAE.ipl.domaine;

import projetAAE.ipl.exceptions.DejaToucheException;

public class Bar {
	private int[][] matrice;
	private int grandeurBar;

	public Bar(int grandeurBar) {
		this.grandeurBar = grandeurBar;
		matrice = new int[grandeurBar][grandeurBar];

		for (int i = 0; i < grandeurBar; i++) {
			for (int j = 0; j < grandeurBar; j++) {
				matrice[i][j] = 0;
			}
		}
	}

	public void envoyerBiere(int x, int y) throws DejaToucheException {

		if (matrice[x][y] > 0) { // si il y a un bateau
			matrice[x][y] = -matrice[x][y]; // alors bateau touché (=> négatif)
		} else if (matrice[x][y] < 0) { // si le bateau de cette case a déjà été
										// touché => rien
			throw new DejaToucheException(
					"Ce bateau a déjà été touché à cet endroit");
		}
	}

	public void positionnerTable(int numTable, int taille, int x, int y,
			boolean horizontal) {

		if (horizontal) {
			for (int i = 0; i < taille; i++) {
				matrice[x + i][y] = numTable;
			}
		} else {
			for (int i = 0; i < taille; i++) {
				matrice[x][y + i] = numTable;
			}
		}
	}

	public void positionnerTable(int numTable, int xSource, int ySource,
			int xDestination, int yDestination) {

		for (int i = xSource; i <= xDestination; i++) {

			matrice[i][ySource] = numTable;

		}

		for (int j = ySource; j < yDestination; j++) {

			matrice[xSource][j] = numTable;

		}
	}

	public boolean tousBourres() {

		for (int i = 0; i < grandeurBar; i++) {
			for (int j = 0; j < grandeurBar; j++) {
				if (matrice[i][j] > 0)
					return false;
			}
		}
		return true;
	}
}
