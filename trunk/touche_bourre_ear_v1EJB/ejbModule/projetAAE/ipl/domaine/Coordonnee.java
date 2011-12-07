package projetAAE.ipl.domaine;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="COORDONNEE", schema="TOUCHEBOURRE")
public class Coordonnee implements Serializable {
	
	@Id
	@GeneratedValue
	private int id;

	@Column(nullable=false)
	private int coordLigne;
	@Column(nullable=false)
	private int coordColonne;
	
	public Coordonnee(){
		
	}
	
	public Coordonnee(int coordLigne, int coordColonne) {
		super();
		this.coordLigne = coordLigne;
		this.coordColonne = coordColonne;
	}
	
	public int getCoordLigne() {
		return coordLigne;
	}
	public int getCoordColonne() {
		return coordColonne;
	}
	public void setCoordLigne(int coordLigne) {
		this.coordLigne = coordLigne;
	}
	public void setCoordColonne(int coordColonne) {
		this.coordColonne = coordColonne;
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
		Coordonnee other = (Coordonnee) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
