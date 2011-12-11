package projetAAE.ipl.dao;

import java.util.Calendar;
import java.util.List;

import javax.ejb.Local;

import projetAAE.ipl.domaine.Fetard;
import projetAAE.ipl.domaine.Soiree;

@Local
public interface SoireeDao extends Dao<Integer, Soiree> {
	
	public Soiree rechercher(String nomSoiree);
	public Soiree rechercheSoireeNonFinie(String nomSoiree);
	public Soiree rechercheDerniereSoireeFinie(String nomSoiree);
	public List<Soiree> listerSoireeEnAttenteDeJoueur();
	public List<Soiree> listerSoireeFinie(String pseudoFetard);
	public Soiree chargerTournee(String pseudoFetard, Soiree soiree);
	public List<Soiree> chargerTournee(String pseudoFetard, List<Soiree> soirees);
}
