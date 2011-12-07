package projetAAE.ipl.domaine;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "FETARDS_SOIREE", schema = "TOUCHEBOURRE", uniqueConstraints = @UniqueConstraint(columnNames = {
		"fetard_id", "soiree_id" }))
public class Fetard_Soiree implements Serializable{
	private static int GRANDEURBAR = 10;

	@Id
	@GeneratedValue
	private int id;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Fetard fetard;

	@OneToOne
	@JoinColumn(nullable = false)
	private Soiree soiree;
	
	private List<TablePlacee> mesTables;
	
	private List<Tournee> mesTournees;
	
	public Fetard_Soiree(){
		
	}

	public Fetard_Soiree(Fetard fetard) {
		this.fetard = fetard;
	}

	public Fetard getFetard() {
		return fetard;
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
		Fetard_Soiree other = (Fetard_Soiree) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
