package projetAAE.ipl.dao;

import javax.ejb.Local;

import projetAAE.ipl.domaine.Fetard;
import projetAAE.ipl.domaine.Soiree;

@Local
public interface SoireeDao extends Dao<Integer, Soiree> {
	
	public Soiree rechercher(String pseudo);
	
}
