package projetAAE.ipl.domaine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import projetAAE.ipl.exceptions.ArgumentInvalideException;
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

	@OneToOne
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
	private List<TablePlacee> mesTablesPlacees = new ArrayList<TablePlacee>();

	@OneToMany(cascade = CascadeType.ALL)
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

	public boolean placerTable(XY[] coord, ETable etable) {

		List<Coordonnee> listeCoord = new ArrayList<Coordonnee>();
		for (XY c : coord) {
			listeCoord.add(new Coordonnee(c.getX(), c.getY()));
		}

		TablePlacee tp = new TablePlacee(listeCoord, etable);
		mesTablesPlacees.add(tp);
		return true;
	}

	// prends un tableau de "coups" en paramètre pour créer une "salve et
	// l'ajouter au fetard_soirée
	public boolean lancerTournee(XY[] coord) throws ArgumentInvalideException {
		if (coord.length != nbBieresParTournee)
			return false;

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
			}
		}
		mesTournees.add(t);
		return true;
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
