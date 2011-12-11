package projetAAE.ipl.domaine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import projetAAE.ipl.exceptions.ArgumentInvalideException;
import projetAAE.ipl.exceptions.CaseDejaOccupeeException;
import projetAAE.ipl.exceptions.DejaToucheException;
import projetAAE.ipl.exceptions.MemePositionException;
import projetAAE.ipl.exceptions.TableDejaPlaceeException;
import projetAAE.ipl.valueObject.XY;

@Entity
@Table(name = "FETARDS_SOIREES", schema = "TOUCHEBOURRE", uniqueConstraints = @UniqueConstraint(columnNames = {
		"fetard_id", "soiree_id" }))
public class Fetard_Soiree implements Serializable {
	private static int GRANDEURBAR = 10;
	private static int NB_BIERES_PAR_TOURNEE_AU_DEBUT = 5;

	@Id
	@GeneratedValue
	private int id;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Fetard fetard;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Soiree soiree;

	@Column
	private int nbBieresParTournee;

	private boolean pret;

	// @OneToMany(cascade=CascadeType.ALL)
	// @MapKey(name="table")
	// private Map<projetAAE.ipl.domaine.Table, TablePlacee> mesTables = new
	// HashMap<projetAAE.ipl.domaine.Table, TablePlacee>();
	//
	// @OneToMany(cascade=CascadeType.ALL)
	// @MapKey(name="id")
	// private Map<Integer, Tournee> mesTournees = new HashMap<Integer,
	// Tournee>();

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(nullable = false, name = "FETARD_SOIREE_ID")
	private List<TablePlacee> mesTablesPlacees = new ArrayList<TablePlacee>();

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(nullable = false, name = "FETARD_SOIREE_ID")
	private List<Tournee> mesTournees = new ArrayList<Tournee>();

	public Fetard_Soiree() {

	}

	public Fetard_Soiree(Fetard fetard, Soiree soiree) {
		this.fetard = fetard;
		this.soiree = soiree;
		this.pret = false;
		this.nbBieresParTournee = NB_BIERES_PAR_TOURNEE_AU_DEBUT;
	}

	public Fetard getFetard() {
		return fetard;
	}

	public int getNbBieresParTournee() {
		return nbBieresParTournee;
	}

	public int getId() {
		return id;
	}

	public boolean placerTable(Map<ETable, List<XY>> tables)
			throws MemePositionException, CaseDejaOccupeeException {

		// vérifier si la liste ne contient pas 2 coordonnées identiques
		for (ETable key : tables.keySet()) {

			List<XY> liste = tables.get(key);
			for (XY a : liste) {
				for (XY b : liste) {
					if (a.getX() == b.getX() && a.getY() == b.getY())
						throw new MemePositionException(
								"La table est renseignée plusieurs fois par la même coordonnée");
				}
			}
		}

		// vérifier qu'on ne place pas une table à un endroit qui contient déjà
		// une autre table
		for (ETable key : tables.keySet()) {
			List<XY> liste1 = tables.get(key);

			for (ETable key2 : tables.keySet()) {
				if (key.equals(key2))
					continue;
				List<XY> liste2 = tables.get(key2);
				for (XY a : liste1) {
					for (XY b : liste2) {
						if (a.getX() == b.getX() && a.getY() == b.getY())
							throw new MemePositionException(
									"La table est renseignée plusieurs fois par la même coordonnée");
					}
				}
			}
		}
		
		for (ETable key : tables.keySet()) {
			List<XY> listeXY = tables.get(key);
			List<Coordonnee> listeCoord = new ArrayList<Coordonnee>();
			for(XY a : listeXY) {
				listeCoord.add(new Coordonnee(a.getX(), a.getY()));
			}
			TablePlacee tp = new TablePlacee(listeCoord, key);
			mesTablesPlacees.add(tp);
		}
		
		return true;
	}

	// prends un tableau de "coups" en paramètre pour créer une "salve" et
	// l'ajouter au fetard_soirée
	public Tournee lancerTournee(List<XY> coord)
			throws ArgumentInvalideException, DejaToucheException {
		if (coord.size() != nbBieresParTournee)
			return null;

		// vérifier si on ne lance pas une Tournee sur des cases déjà atteintes
		for (Tournee t : mesTournees) {
			for (Biere b : t.getBieres()) {
				for (XY c : coord) {
					if (memePosition(b, c))
						throw new DejaToucheException(
								"Case déjà touchée par une salve antérieure");
				}
			}
		}

		Tournee t = new Tournee();
		Fetard_Soiree adversaire = soiree.getAdversaire(this);

		for (XY c : coord) {
			// ajouter la Biere dans sa Tournee
			Biere b = new Biere(c.getX(), c.getY(),
					adversaire.tableToucheePar(c));
			t.ajouterBiere(b);

			// coulerTable ==> traiter le lancer de la Biere chez l'adversaire
			// (décrémenter la vie de la table)
			if (adversaire.coulerTable(b)) {
				decrementerNbBieresParTournee();
				b.setaCoule(true);
			}
		}
		mesTournees.add(t);
		return t;
	}

	private boolean memePosition(Biere b, XY c) {
		return b.getX() == c.getX() && b.getY() == c.getY();
	}

	private void decrementerNbBieresParTournee() {
		nbBieresParTournee--;
	}

	// renvoie true si la Biere a "coulé" la table, false sinon
	private boolean coulerTable(Biere b) {

		if (b.getTableTouchee() == null)
			return false;

		for (TablePlacee tp : mesTablesPlacees) {
			if (tp.getTable().equals(b.getTableTouchee())) {
				tp.decrementerVies();
				return tp.estCoulee();
			}
		}
		return false;
	}

	// renvoie la table touchée par le "coup"
	// renvoie null si le "coup" n'a rien touché
	public ETable tableToucheePar(XY coord) {

		for (TablePlacee tp : mesTablesPlacees) {
			List<Coordonnee> listeCoord = tp.getCoordonnees();
			for (Coordonnee c : listeCoord) {
				if (c.getX() == coord.getX() && c.getY() == coord.getY()) {
					return tp.getTable();
				}
			}
		}
		return null;
	}

	public boolean isPret() {
		return pret;
	}

	public void setPret(boolean pret) {
		this.pret = pret;
	}

	public List<TablePlacee> getMesTablesPlacees() {
		return mesTablesPlacees;
	}

	public List<Tournee> getMesTournees() {
		return mesTournees;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fetard_Soiree other = (Fetard_Soiree) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
