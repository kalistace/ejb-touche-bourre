package projetAAE.ipl.dao;

import javax.ejb.Local;

import projetAAE.ipl.domaine.Fetard;

@Local
public interface FetardDao extends Dao<Integer, Fetard>{

	public Fetard rechercher(String pseudo);
}
