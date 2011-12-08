package projetAAE.ipl.usecasesimpl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import projetAAE.ipl.dao.FetardDao;
import projetAAE.ipl.dao.Fetard_SoireeDao;
import projetAAE.ipl.dao.SoireeDao;
import projetAAE.ipl.domaine.Fetard;
import projetAAE.ipl.domaine.Soiree;
import projetAAE.ipl.domaine.Soiree.Etat;
import projetAAE.ipl.domaine.Tournee;
import projetAAE.ipl.usecases.GestionSoiree;

@Stateless
public class GestionSoireeImpl implements GestionSoiree {
	
	
	@EJB 
	private Fetard_SoireeDao fetard_SoireeDao;
	@EJB 
	private FetardDao fetardDao;
	@EJB 
	private SoireeDao soireeDao;

	@Override
	public Soiree creerSoiree(String nomSoiree, String pseudoFetard1) {
		Fetard fetard = fetardDao.rechercher(pseudoFetard1);
		Soiree soiree = soireeDao.rechercher(nomSoiree);
		if(soiree != null && soiree.getEtat()!=Etat.FINIE){
			return null;//throw exception
		}
		return new Soiree(nomSoiree, fetard);
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

	@Override
	public List<Tournee> listerTournees(String nomSoiree) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
