package projetAAE.ipl.usecases;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import projetAAE.ipl.domaine.ETable;
import projetAAE.ipl.domaine.Soiree;
import projetAAE.ipl.domaine.Tournee;
import projetAAE.ipl.valueObject.XY;

@Remote
public interface GestionSoiree {
	Soiree creerSoiree(String nomSoiree, String pseudoFetard1);
	Soiree rejoindreSoiree(String nomSoiree, String pseudoFetard2);
	List<Soiree> listerPartiesEnAttenteDePartenaire();
	Soiree fetardPret(String nomSoiree, String fetard, Map<ETable, List<XY>> tables);
	Soiree lancerUneTournee(String nomSoiree, String pseudoFetard, List<XY> coord);
	List<Soiree> listerSoireesFinies(String pseudoFetard);
	Soiree fetardDeconnecte(String nomSoiree, String pseudoFetard);
	Soiree commencerPlacement(String nomSoiree) throws Exception;
	Soiree commencerSoiree(String nomSoiree) throws Exception;
}
