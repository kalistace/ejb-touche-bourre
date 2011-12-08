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
	private int x;
	@Column(nullable=false)
	private int y;
	
	public Coordonnee(){
		
	}
	
	public Coordonnee(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
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
