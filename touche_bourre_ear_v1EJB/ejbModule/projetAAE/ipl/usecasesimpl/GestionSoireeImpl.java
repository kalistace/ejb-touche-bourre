package projetAAE.ipl.usecasesimpl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import projetAAE.ipl.dao.Fetard_SoireeDao;
import projetAAE.ipl.dao.SoireeDao;
import projetAAE.ipl.domaine.Fetard;
import projetAAE.ipl.domaine.Soiree;
import projetAAE.ipl.usecases.GestionSoiree;

@Stateless
public class GestionSoireeImpl implements GestionSoiree {
	
	
	@EJB Fetard_SoireeDao fetard_SoireeDao;
	@EJB SoireeDao SoireeDao;

	@Override
	public Soiree creerSoiree(String pseudoFetard1) {
		Fetard fetard = fetardDao.rechercher()
		return new Soiree(fetard);
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
