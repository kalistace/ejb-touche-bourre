package projetAAE.ipl.usecases;

import java.util.List;

import javax.ejb.Remote;

import projetAAE.ipl.domaine.Soiree;
import projetAAE.ipl.domaine.Tournee;

@Remote
public interface GestionSoiree {
	Soiree creerSoiree(String nomSoiree, String pseudoFetard1);
	Soiree rejoindreSoiree(String nomSoiree, String pseudoFetard2);
	List<Soiree> listerPartiesEnAttenteDePartenaire();
	Soiree fetardPret(String nomSoiree, String fetard);
	Soiree demarrerSoiree(String nomSoiree);
	Soiree joueurUnTour(String nomSoiree, String fetard);
	List<Tournee> listerTournees(String nomSoiree);
}
