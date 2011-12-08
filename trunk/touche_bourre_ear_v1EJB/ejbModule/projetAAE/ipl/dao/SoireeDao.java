package projetAAE.ipl.dao;

import java.util.List;

import javax.ejb.Local;

import projetAAE.ipl.domaine.Fetard;
import projetAAE.ipl.domaine.Soiree;

@Local
public interface SoireeDao extends Dao<Integer, Soiree> {
	
	public Soiree rechercher(String pseudo);
	public Soiree rechercheSoireeNonFinie(String pseudo);
	public List<Soiree> listerSoireeEnAttenteDeJoueur();
	public List<Soiree> listerSoireeFinie(String pseudoFetard);
}
