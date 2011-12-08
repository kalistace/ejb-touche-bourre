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
import projetAAE.ipl.exceptions.ArgumentInvalideException;
import projetAAE.ipl.exceptions.DejaToucheException;
import projetAAE.ipl.usecases.GestionSoiree;
import projetAAE.ipl.valueObject.XY;

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
		if (soiree != null && soiree.getEtat() != Etat.FINIE) {
			return null;// throw exception
		}
		soiree = new Soiree(nomSoiree, fetard);
		soireeDao.enregistrer(soiree);
		return soiree;
	}

	@Override
	public Soiree rejoindreSoiree(String nomSoiree, String pseudoFetard2) {
		Fetard fetard = fetardDao.rechercher(pseudoFetard2);
		Soiree soiree = soireeDao.rechercher(nomSoiree);
		if (soiree == null) {
			return null;// throw exception
		}
		if (soiree != null) {
			if (soiree.getEtat() == Etat.FINIE) {
				return null;// throw exception
			}
			if (soiree.getNbrFetardConnecte() == 2) {
				return null;// throw exception
			}
		}
		soiree.ajouterFetard(fetard, soiree);
		soireeDao.mettreAJour(soiree);
		return soiree;
	}

	@Override
	public List<Soiree> listerPartiesEnAttenteDePartenaire() {
		return soireeDao.listerSoireeEnAttenteDeJoueur();
	}

	@Override
	public Soiree fetardPret(String nomSoiree, String pseudoFetard) {
		Fetard fetard = fetardDao.rechercher(pseudoFetard);
		Soiree soiree = soireeDao.rechercher(nomSoiree);
		soiree.setJoueurPret(fetard, soiree);
		soireeDao.mettreAJour(soiree);
		return soiree;
	}
	
	@Override
	public Tournee lancerUneTournee(String nomSoiree, String pseudoFetard, List<XY> coord) {
		Soiree soiree = soireeDao.rechercher(nomSoiree);
		Tournee tournee = null;
		if(soiree == null){
			return null;
		}
		if(soiree.getFetard_Soiree_Courant().getFetard().getPseudo() != pseudoFetard){
			return null;// throw exception
		}
		try {
			try {
				tournee = soiree.lancerTournee(soiree, coord);
			} catch (DejaToucheException e) {
				return null;
			}
		} catch (ArgumentInvalideException e) {
			return null;//throw exception
		}
		soireeDao.mettreAJour(soiree);
		return tournee;
	}

	@Override
	public List<Soiree> listerSoireesFinies(String pseudoFetard) {
		Fetard fetard = fetardDao.rechercher(pseudoFetard);
		if(fetard==null){
			return null;
		}
		return soireeDao.listerSoireeFinie(pseudoFetard);
	}

}
