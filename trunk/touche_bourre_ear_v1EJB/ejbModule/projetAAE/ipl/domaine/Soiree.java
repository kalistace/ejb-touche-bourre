package projetAAE.ipl.domaine;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="SOIREES", schema="TOUCHEBOURRE")
public class Soiree implements Serializable{

	@Id
	@GeneratedValue
	private int id;

	@OneToOne(mappedBy = "soiree", cascade = (CascadeType.ALL))	
	private Fetard_Soiree fetardSoiree1;
	@OneToOne(mappedBy = "soiree", cascade = (CascadeType.ALL))
	private Fetard_Soiree fetardSoiree2;

	private String nom;
	
	public Soiree() {
	}
	
	public Soiree(String nom, Fetard fetard1) {
		this.nom = nom;
		fetardSoiree1 = new Fetard_Soiree(fetard1);
	}

	public void rejoindrePartie(Fetard fetard2) {
		fetardSoiree2 = new Fetard_Soiree(fetard2);
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
}
