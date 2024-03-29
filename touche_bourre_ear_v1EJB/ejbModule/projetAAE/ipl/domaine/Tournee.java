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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="TOURNEES", schema="TOUCHEBOURRE")
public class Tournee implements Serializable {

	
	@Id
	@GeneratedValue
	@Column(name = "ID_TOURNEE")
	private int id;

	@OneToMany(cascade = { CascadeType.ALL })
    @JoinColumn(nullable = false, name = "TOURNEE_ID")
	private List<Biere> bieres = new ArrayList<Biere>();


	public Tournee() {

	}

	public int getId() {
		return id;
	}
	
	public List<Biere> getBieres() {
		return bieres;
	}
	
	public boolean ajouterBiere(Biere b) {
		if(bieres.contains(b)) return false;
		bieres.add(b);
		return true;
	}
	
	public boolean supprimerBiere(Biere b) {
		if(!bieres.contains(b)) return false;
		bieres.remove(b);
		return true;
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
		Tournee other = (Tournee) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
