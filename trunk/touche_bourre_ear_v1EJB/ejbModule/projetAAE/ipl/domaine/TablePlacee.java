package projetAAE.ipl.domaine;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name="TABLEPLACEES", schema="TOUCHEBOURRE")
public class TablePlacee {
	
	@Id
	@GeneratedValue
	private int id;
	
	@ManyToMany
	@JoinTable(schema="JACK",
			joinColumns=@JoinColumn(name="PARTIE_ID"),
			inverseJoinColumns=@JoinColumn(name="JOUEUR_ID"))
	@OrderBy("pseudo")
	private List<Coordonnee> coordonnees;
	private Table table;
	private int vies;
	
	public TablePlacee(){
		
	}
	public TablePlacee(List<Coordonnee> coordonnees, Table table, int vies) {
		this.coordonnees = coordonnees;
		this.table = table;
		this.vies = vies;
	}

	public List<Coordonnee> getCoordonnees() {
		return new ArrayList<Coordonnee>(coordonnees);
	}

	public Table getTable() {
		return table;
	}

	public int getVies() {
		return vies;
	}

	public void setCoordonnees(List<Coordonnee> coordonnees) {
		this.coordonnees = coordonnees;
	}

	public void setVies(int vies) {
		this.vies = vies;
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
		TablePlacee other = (TablePlacee) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
}
