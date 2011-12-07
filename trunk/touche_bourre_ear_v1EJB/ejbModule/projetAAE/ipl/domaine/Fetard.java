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

	public Fetard(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getPseudo() {
		return pseudo;
	}

	public int getId() {
		return id;
	}
}
