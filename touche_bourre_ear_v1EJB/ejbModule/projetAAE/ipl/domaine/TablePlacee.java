package projetAAE.ipl.domaine;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="TABLEPLACEES", schema="TOUCHEBOURRE")
public class TablePlacee {
	
	@Id
	@GeneratedValue
	private int id;
	
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "TABLEPLACEE_ID")
	private List<Coordonnee> coordonnees;
	@Enumerated(EnumType.STRING)
	private ETable table;
	private int vies;
	
	public TablePlacee(){
		
	}
	public TablePlacee(List<Coordonnee> coordonnees, ETable table) {
		this.coordonnees = coordonnees;
		this.table = table;
		this.vies = coordonnees.size();
	}
	
	public void decrementerVies() {
		vies--;
	}
	
	public boolean estCoulee() {
		return vies == 0;
	}

	public List<Coordonnee> getCoordonnees() {
		return new ArrayList<Coordonnee>(coordonnees);
	}

	public ETable getTable() {
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
