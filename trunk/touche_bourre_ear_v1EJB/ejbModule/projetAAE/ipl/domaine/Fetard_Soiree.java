package projetAAE.ipl.domaine;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
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
	Fetard fetard;
	
	//@OneToOne(cascade = CascadeType.ALL) @PrimaryKeyJoinColumn
	@Transient//ignore
	Bar propreBar;
	
	//@OneToOne(cascade = CascadeType.ALL) @PrimaryKeyJoinColumn
	@Transient//ignore
	BarAdversaire barAdversaire;
	
//	@ManyToOne
//	@JoinColumn(nullable = false)
//	Soiree soiree;

	public Fetard_Soiree(Fetard fetard) {
		this.fetard = fetard;
		this.propreBar = new Bar(GRANDEURBAR);
		this.barAdversaire = new BarAdversaire(GRANDEURBAR);
	}

	public Fetard getFetard() {
		return fetard;
	}

	public int getId() {
		return id;
	}

}
