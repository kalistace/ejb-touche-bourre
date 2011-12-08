package projetAAE.ipl.domaine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import projetAAE.ipl.exceptions.ArgumentInvalideException;
import projetAAE.ipl.exceptions.DejaToucheException;
import projetAAE.ipl.valueObject.XY;

@Entity
@Table(name = "SOIREES", schema = "TOUCHEBOURRE")
public class Soiree implements Serializable {

	public enum Etat {
		INITIAL_ATTENTE_FETARD {
			boolean ajouterFetard(Fetard fetard, Soiree soiree) {
				if (soiree.getFetard_Soiree(fetard) != null) {
					return false;
				}
				if (soiree.fetardSoiree1 == null) {
					return false;
				}
				soiree.fetardSoiree2 = new Fetard_Soiree(fetard, soiree);
				soiree.etat = EN_PLACEMENT;
				soiree.nbrFetardConnecte++;
				return true;
			}

			boolean FetardDeconnecte(Fetard fetard, Soiree soiree) {
				soiree.nbrFetardConnecte--;
				soiree.etat = FINIE;
				// pas de gagnant
				return true;
			}
		},
		EN_PLACEMENT {
			boolean setJoueurPret(Fetard fetard, Soiree soiree) {
				Fetard_Soiree monFetard_Soiree = soiree
						.getFetard_Soiree(fetard);
				if (monFetard_Soiree == null) {
					return false;
				}
				if (soiree.nbrFetardPret >= 2) {
					return false;
				}
				monFetard_Soiree.setPret(true);
				soiree.nbrFetardPret++;
				if (soiree.nbrFetardPret == 1) {
					soiree.fetard_Soiree_Courant = monFetard_Soiree;
					soiree.premierFetardAJouer = monFetard_Soiree;
				} else {// 2 joueurs prêts
					soiree.etat = EN_COURS;
				}
				return true;
			}

			boolean FetardDeconnecte(Fetard fetard, Soiree soiree) {
				Fetard_Soiree monFetard_Soiree = soiree
						.getFetard_Soiree(fetard);
				soiree.nbrFetardConnecte--;
				if (monFetard_Soiree.isPret()) {
					monFetard_Soiree.setPret(false);
					soiree.nbrFetardPret--;
				}
				if (soiree.nbrFetardConnecte == 0) {
					soiree.etat = FINIE;
					// pas de gagnant
				}
				return true;
			}
		},
		EN_COURS {

			Tournee lancerTournee(Soiree soiree, List<XY> coord)
					throws ArgumentInvalideException, DejaToucheException {
				Tournee tournee = soiree.fetard_Soiree_Courant
						.lancerTournee(coord);

				soiree.fetard_Soiree_Courant = soiree
						.getAdversaire(soiree.fetard_Soiree_Courant);

				if (soiree.fetardSoiree1.getNbBieresParTournee() == 0) {
					soiree.gagnant = soiree.fetardSoiree1;
					soiree.etat = FINIE;
				} else if (soiree.fetardSoiree2.getNbBieresParTournee() == 0) {
					soiree.gagnant = soiree.fetardSoiree2;
					soiree.etat = FINIE;
					soiree.dateFin = new GregorianCalendar();
				}
				return tournee;
			}

			boolean FetardDeconnecte(Fetard fetard, Soiree soiree) {
				soiree.nbrFetardConnecte--;
				if (soiree.nbrFetardConnecte == 0) {
					soiree.etat = FINIE;
					// le joueur restant gagne par forfait
					soiree.gagnant = soiree.getAdversaire(soiree
							.getFetard_Soiree(fetard));
				}
				return true;
			}

		},
		FINIE {

		};
		boolean setJoueurPret(Fetard fetard, Soiree soiree) {
			return false;
		}

		boolean ajouterFetard(Fetard fetard, Soiree soiree) {
			return false;
		}

		boolean FetardDeconnecte(Fetard fetard, Soiree soiree) {
			return false;
		}

		Tournee lancerTournee(Soiree soiree, List<XY> coord)
				throws ArgumentInvalideException, DejaToucheException {
			return null;
		}
	}

	@Id
	@GeneratedValue
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dateDebut;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dateFin;

	private int nbrFetardPret;

	private int nbrFetardConnecte;

	@OneToOne(mappedBy = "soiree", cascade = (CascadeType.ALL))
	private Fetard_Soiree fetard_Soiree_Courant;
	@OneToOne(mappedBy = "soiree", cascade = (CascadeType.ALL))
	private Fetard_Soiree gagnant;
	@OneToOne(mappedBy = "soiree", cascade = (CascadeType.ALL))
	private Fetard_Soiree premierFetardAJouer;
	@OneToOne(mappedBy = "soiree", cascade = (CascadeType.ALL))
	private Fetard_Soiree fetardSoiree1;
	@OneToOne(mappedBy = "soiree", cascade = (CascadeType.ALL))
	private Fetard_Soiree fetardSoiree2;
	@Enumerated
	private Etat etat = Etat.INITIAL_ATTENTE_FETARD;

	private String nom;

	public Soiree() {
	}

	public Soiree(String nom, Fetard fetard1) {
		this.nom = nom;
		this.fetardSoiree1 = new Fetard_Soiree(fetard1, this);
		this.fetard_Soiree_Courant = null;
		this.nbrFetardPret = 0;
		this.nbrFetardConnecte = 1;
		this.gagnant = null;
		this.dateDebut = new GregorianCalendar();
		this.dateFin = null;
	}

	private Fetard_Soiree getFetard_Soiree(Fetard fetard) {
		if (fetardSoiree1.getFetard().equals(fetard)) {
			return fetardSoiree1;
		} else if (fetardSoiree2.getFetard().equals(fetard)) {
			return fetardSoiree2;
		}
		return null;
	}

	public boolean ajouterFetard(Fetard fetard, Soiree soiree) {
		return etat.ajouterFetard(fetard, this);
	}

	public boolean setJoueurPret(Fetard fetard, Soiree soiree) {
		return etat.setJoueurPret(fetard, soiree);
	}

	public Tournee lancerTournee(Soiree soiree, List<XY> coord)
			throws ArgumentInvalideException, DejaToucheException {
		return etat.lancerTournee(soiree, coord);
	}

	public Fetard_Soiree getAdversaire(Fetard_Soiree soi_meme) {
		if (soi_meme.equals(fetardSoiree1))
			return fetardSoiree2;
		else if (soi_meme.equals(fetardSoiree2))
			return fetardSoiree1;
		return null;
	}

	public List<Tournee> listePermuteeEtOrdonneeDeTournees() {

		List<Tournee> liste1 = premierFetardAJouer.getMesTournees();
		List<Tournee> liste2 = null;
		if (premierFetardAJouer.equals(fetardSoiree1))
			liste2 = fetardSoiree2.getMesTournees();
		else
			liste2 = fetardSoiree1.getMesTournees();
		List<Tournee> listeARenvoyer = new ArrayList<Tournee>();

		for (int i = 0; i < liste1.size(); i++) {
			listeARenvoyer.add(liste1.get(i));
			if (i < liste2.size())
				listeARenvoyer.add(liste2.get(i));
		}
		return listeARenvoyer;
	}

	public int getId() {
		return id;
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
		Soiree other = (Soiree) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Fetard_Soiree getFetardSoiree1() {
		return fetardSoiree1;
	}

	public Fetard_Soiree getFetardSoiree2() {
		return fetardSoiree2;
	}

	public Etat getEtat() {
		return etat;
	}

	public int getNbrFetardPret() {
		return nbrFetardPret;
	}

	public int getNbrFetardConnecte() {
		return nbrFetardConnecte;
	}

	public Fetard_Soiree getFetard_Soiree_Courant() {
		return fetard_Soiree_Courant;
	}

	public Fetard_Soiree getGagnant() {
		return gagnant;
	}

	public Fetard_Soiree getPremierFetardAJouer() {
		return premierFetardAJouer;
	}

	public Calendar getDateDebut() {
		return dateDebut;
	}

	public Calendar getDateFin() {
		return dateFin;
	}
}
