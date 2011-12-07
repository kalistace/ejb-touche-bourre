package projetAAE.ipl.domaine;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="SOIREES", schema="TOUCHEBOURRE")
public class Soiree implements Serializable{

	@Id
	@GeneratedValue
	private int id;

	@OneToMany(mappedBy = "soiree", cascade = (CascadeType.ALL))	
	private Map<String, Fetard_Soiree> lesFetards;

	public Soiree(Fetard fetard1) {
		lesFetards = new Hashtable<String, Fetard_Soiree>(2);
		lesFetards.put(fetard1.getPseudo(), new Fetard_Soiree(fetard1));
	}

	public void rejoindrePartie(Fetard fetard2) {
		lesFetards.put(fetard2.getPseudo(), new Fetard_Soiree(fetard2));
	}
}
