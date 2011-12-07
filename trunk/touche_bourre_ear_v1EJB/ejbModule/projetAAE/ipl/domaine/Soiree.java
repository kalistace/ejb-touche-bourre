package projetAAE.ipl.domaine;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="SOIREES", schema="TOUCHEBOURRE")
public class Soiree implements Serializable{

	public enum Etat {
		INITIAL_ATTENTE_FETARD {
			boolean ajouterFetard(Fetard fetard, Soiree soiree) {
				if (soiree.getFetard_Soiree(fetard) != null){
					return false;
				}
				if(soiree.fetardSoiree1 == null){
					return false;
				}
				soiree.fetardSoiree2 = new Fetard_Soiree(fetard, soiree);
				soiree.etat = EN_PLACEMENT;
				return true;
			}
		},
		EN_PLACEMENT {
			boolean estPret(Fetard fetard, Soiree soiree) {
				if (soiree.getFetard_Soiree(fetard) == null){
					return false;
				}
				soiree.fetardSoiree2 = new Fetard_Soiree(fetard, soiree);
				soiree.etat = EN_PLACEMENT;
				return true;
			}
		}, 
		EN_COURS {
		}, 
		FINIE {		
		};
		boolean ajouterFetard(Fetard fetard, Soiree soiree){
			return false;
		}
	}
	
	@Id
	@GeneratedValue
	private int id;
	
	private int nbrFetardPret;
	
	private Fetard_Soiree fetard_Soiree_Courant;
	
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
	}
	
	private Fetard_Soiree getFetard_Soiree(Fetard fetard) {
		if(fetardSoiree1.getFetard().equals(fetard)){
			return fetardSoiree1;
		}
		else if(fetardSoiree2.getFetard().equals(fetard)){
			return fetardSoiree2;
		}
		return null;
	}

	public boolean ajouterFetard(Fetard fetard, Soiree soiree){
		return etat.ajouterFetard(fetard, this);
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
}
