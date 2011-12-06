package projetAAE.ipl.usecasesimpl;

import java.util.List;

import javax.ejb.Stateless;

import projetAAE.ipl.domaine.Fetard_Soiree;
import projetAAE.ipl.domaine.Soiree;
import projetAAE.ipl.usecases.GestionSoiree;

@Stateless
public class GestionSoireeImpl implements GestionSoiree {

	@Override
	public Soiree creerSoiree(String pseudoFetard1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Soiree rejoindreSoiree(String nomSoiree, String pseudoFetard2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Soiree> listerPartiesEnAttenteDePartenaire() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Soiree fetardPret(String nomSoiree, String fetard) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Soiree demarrerSoiree(String nomSoiree) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Soiree joueurUnTour(String nomSoiree, String fetard) {
		// TODO Auto-generated method stub
		return null;
	}
}
