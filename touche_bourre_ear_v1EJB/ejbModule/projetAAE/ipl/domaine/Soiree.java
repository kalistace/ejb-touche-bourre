package projetAAE.ipl.domaine;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

	public Soiree() {
	}
	
	public Soiree(Fetard fetard1) {
		fetardSoiree1 = new Fetard_Soiree(fetard1);
	}

	public void rejoindrePartie(Fetard fetard2) {
		fetardSoiree2 = new Fetard_Soiree(fetard2);
	}
}
