package projetAAE.ipl.domaine;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="FETARDS", schema="TOUCHEBOURRE")
public class Fetard implements Serializable{
	
	@Id @GeneratedValue
	private int id;
	
	@Column(nullable=false, unique=true)
	private String pseudo;
	
	public Fetard(){
		
	}

	public Fetard(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getPseudo() {
		return pseudo;
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
		Fetard other = (Fetard) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
