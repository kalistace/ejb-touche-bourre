package projetAAE.ipl.domaine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import projetAAE.ipl.exceptions.CaseDejaOccupeeException;
import projetAAE.ipl.exceptions.DejaToucheException;
import projetAAE.ipl.exceptions.MemePositionException;
import projetAAE.ipl.exceptions.NombreDeBieresIncorrectParTourneeException;
import projetAAE.ipl.valueObject.XY;

@Entity
@Table(name = "SOIREES", schema = "TOUCHEBOURRE")
public class Soiree implements Serializable {

	public enum Etat {
		INITIAL_ATTENTE_FETARD {
			boolean ajouterFetard(Fetard fetard, Soiree soiree) {
				if (soiree.getFetard_Soiree(fetard.getPseudo()) != null) {
					return false;
				}
				if (soiree.getFetardSoiree1() == null) {
					return false;
				}
				soiree.rajouterFetard_Soiree(new Fetard_Soiree(fetard, soiree));
				soiree.etat = EN_PLACEMENT;
				soiree.nbrFetardConnecte++;
				return true;
			}

			boolean fetardDeconnecte(Fetard fetard, Soiree soiree) {
				soiree.nbrFetardConnecte--;
				soiree.etat = FINIE;
				// pas de gagnant
				return true;
			}
		},
		EN_PLACEMENT {

			boolean setJoueurPret(Fetard fetard, Soiree soiree,
					Map<ETable, List<XY>> tables) throws MemePositionException,
					 CaseDejaOccupeeException {
				Fetard_Soiree monFetard_Soiree = soiree
						.getFetard_Soiree(fetard.getPseudo());
				if (monFetard_Soiree == null) {
					return false;
				}
				if (soiree.nbrFetardPret >= 2) {
					return false;
				}
				if(monFetard_Soiree.isPret()){
					return false;
				}
				
					monFetard_Soiree.placerTable(tables);
				
				monFetard_Soiree.setPret(true);
				soiree.nbrFetardPret++;
				if (soiree.nbrFetardPret == 1) {
					soiree.fetard_Soiree_Courant = monFetard_Soiree;
					soiree.premierFetardAJouer = monFetard_Soiree;
				} else {// 2 joueurs prêts
					soiree.etat = EN_COURS;
					soiree.dateDebut = new GregorianCalendar();
				}
				return true;
			}

			boolean fetardDeconnecte(Fetard fetard, Soiree soiree) {
				Fetard_Soiree monFetard_Soiree = soiree
						.getFetard_Soiree(fetard.getPseudo());
				soiree.nbrFetardConnecte--;
				if (monFetard_Soiree.isPret()) {
					monFetard_Soiree.setPret(false);
					soiree.nbrFetardPret--;
				}
				if (soiree.nbrFetardConnecte == 0) {
					soiree.etat = FINIE;
					soiree.dateFin = new GregorianCalendar();
					// pas de gagnant
				}
				return true;
			}
		},
		EN_COURS {

			boolean lancerTournee(Soiree soiree, List<XY> coord)
					throws DejaToucheException, NombreDeBieresIncorrectParTourneeException {
				soiree.fetard_Soiree_Courant.lancerTournee(coord);

				soiree.fetard_Soiree_Courant = soiree
						.getAdversaire(soiree.fetard_Soiree_Courant);

				if (soiree.getFetardSoiree1().getNbBieresParTournee() == 0) {
					soiree.gagnant = soiree.getFetardSoiree1();
					soiree.etat = FINIE;
					soiree.dateFin = new GregorianCalendar();
				} else if (soiree.getFetardSoiree2().getNbBieresParTournee() == 0) {
					soiree.gagnant = soiree.getFetardSoiree2();
					soiree.etat = FINIE;
					soiree.dateFin = new GregorianCalendar();
				}
				return true;
			}

			boolean fetardDeconnecte(Fetard fetard, Soiree soiree) {
				soiree.nbrFetardConnecte--;
				if (soiree.nbrFetardConnecte == 0) {
					soiree.etat = FINIE;
					// le joueur restant gagne par forfait
					soiree.gagnant = soiree.getAdversaire(soiree
							.getFetard_Soiree(fetard.getPseudo()));
					soiree.dateFin = new GregorianCalendar();
				}
				return true;
			}

		},
		FINIE {

		};
		boolean setJoueurPret(Fetard fetard, Soiree soiree,
				Map<ETable, List<XY>> tables) throws MemePositionException,
				CaseDejaOccupeeException {
			return false;
		}

		boolean ajouterFetard(Fetard fetard, Soiree soiree) {
			return false;
		}

		boolean fetardDeconnecte(Fetard fetard, Soiree soiree) {
			return false;
		}

		boolean lancerTournee(Soiree soiree, List<XY> coord)
				throws DejaToucheException, NombreDeBieresIncorrectParTourneeException {
			return false;
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

	@OneToOne(cascade = (CascadeType.ALL))
	private Fetard_Soiree fetard_Soiree_Courant;
	@OneToOne(cascade = (CascadeType.ALL))
	private Fetard_Soiree gagnant;
	@OneToOne(cascade = (CascadeType.ALL))
	private Fetard_Soiree premierFetardAJouer;

	@OneToMany(mappedBy = "soiree", cascade = (CascadeType.ALL), fetch = FetchType.EAGER)
	private List<Fetard_Soiree> lesDeuxFetard_Soiree = new ArrayList<Fetard_Soiree>();

	@Enumerated(EnumType.STRING)
	private Etat etat = Etat.INITIAL_ATTENTE_FETARD;

	private String nom;

	public Soiree() {
	}

	public Soiree(String nom, Fetard fetard1) {
		this.nom = nom;
		this.lesDeuxFetard_Soiree.add(new Fetard_Soiree(fetard1, this));
		this.fetard_Soiree_Courant = null;
		this.nbrFetardPret = 0;
		this.nbrFetardConnecte = 1;
		this.gagnant = null;
		this.dateDebut = null;
		this.dateFin = null;
	}

	public Fetard_Soiree getFetard_Soiree(String nomFetard) {
		for (Fetard_Soiree fetardTmp : lesDeuxFetard_Soiree) {
			if (fetardTmp.getFetard().getPseudo().equals(nomFetard)) {
				return fetardTmp;
			}
		}
		return null;
	}

	public boolean ajouterFetard(Fetard fetard, Soiree soiree) {
		return etat.ajouterFetard(fetard, this);
	}

	public boolean setJoueurPret(Fetard fetard, Soiree soiree,
			Map<ETable, List<XY>> tables) throws MemePositionException, CaseDejaOccupeeException {
		return etat.setJoueurPret(fetard, soiree, tables);
	}

	public boolean lancerTournee(Soiree soiree, List<XY> coord)
			throws DejaToucheException, NombreDeBieresIncorrectParTourneeException {
		return etat.lancerTournee(soiree, coord);
	}

	public boolean fetardDeconnecte(Fetard fetard, Soiree soiree) {
		return etat.fetardDeconnecte(fetard, soiree);
	}

	public Fetard_Soiree getAdversaire(Fetard_Soiree soi_meme) {

		if (soi_meme.getFetard().getPseudo().equals(getFetardSoiree1().getFetard().getPseudo()))
			return getFetardSoiree2();
		else if (soi_meme.getFetard().getPseudo().equals(getFetardSoiree2().getFetard().getPseudo()))
			return getFetardSoiree1();
		return null;
	}

	public List<Tournee> listePermuteeEtOrdonneeDeTournees() {
		List<Tournee> listeARenvoyer = new ArrayList<Tournee>();
		if (nbrFetardPret != 2 || lesDeuxFetard_Soiree.size() != 2)
			return listeARenvoyer;
		List<Tournee> liste1 = premierFetardAJouer.getMesTournees();
		List<Tournee> liste2 = null;
		if (premierFetardAJouer.equals(getFetardSoiree1()))
			liste2 = getFetardSoiree2().getMesTournees();
		else
			liste2 = getFetardSoiree1().getMesTournees();

		for (int i = 0; i < liste1.size(); i++) {
			listeARenvoyer.add(liste1.get(i));
			if (i < liste2.size())
				listeARenvoyer.add(liste2.get(i));
		}
		return listeARenvoyer;
	}

	private void rajouterFetard_Soiree(Fetard_Soiree fs) {
		lesDeuxFetard_Soiree.add(fs);
	}

	public int getId() {
		return id;
	}
	

	public void setEtat(Etat etat) {
		this.etat = etat;
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
		if (lesDeuxFetard_Soiree.size() < 1)
			return null;
		return lesDeuxFetard_Soiree.get(0);
	}

	public Fetard_Soiree getFetardSoiree2() {
		if (lesDeuxFetard_Soiree.size() < 2)
			return null;
		return lesDeuxFetard_Soiree.get(1);
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
