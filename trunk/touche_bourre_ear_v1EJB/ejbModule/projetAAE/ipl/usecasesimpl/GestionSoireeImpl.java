package projetAAE.ipl.usecasesimpl;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Singleton;

import projetAAE.ipl.dao.FetardDao;
import projetAAE.ipl.dao.Fetard_SoireeDao;
import projetAAE.ipl.dao.SoireeDao;
import projetAAE.ipl.domaine.ETable;
import projetAAE.ipl.domaine.Fetard;
import projetAAE.ipl.domaine.Soiree;
import projetAAE.ipl.domaine.Soiree.Etat;
import projetAAE.ipl.exceptions.ArgumentInvalideException;
import projetAAE.ipl.exceptions.CaseDejaOccupeeException;
import projetAAE.ipl.exceptions.DejaToucheException;
import projetAAE.ipl.exceptions.MemePositionException;
import projetAAE.ipl.exceptions.TableDejaPlaceeException;
import projetAAE.ipl.usecases.GestionSoiree;
import projetAAE.ipl.valueObject.XY;

@Singleton
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
		if (fetard == null)
			return null;

		Soiree soiree = soireeDao.rechercheSoireeNonFinie(nomSoiree);
		if (soiree != null) {
			return null;// throw exception, peut pas avoir 2 soirée 'en cours'
						// avec le meme nom
		}
		soiree = new Soiree(nomSoiree, fetard);
		soireeDao.enregistrer(soiree);
		return soiree;
	}

	@Override
	public Soiree rejoindreSoiree(String nomSoiree, String pseudoFetard2) {
		Fetard fetard = fetardDao.rechercher(pseudoFetard2);
		if (fetard == null)
			return null;
		Soiree soiree = soireeDao.rechercheSoireeNonFinie(nomSoiree);
		if (soiree == null) {
			return null;// throw exception
		}
		if (soiree.getNbrFetardConnecte() == 2) {
			return null;// throw exception
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
	public Soiree fetardPret(String nomSoiree, String pseudoFetard,
			Map<ETable, List<XY>> tables) throws MemePositionException,
			TableDejaPlaceeException, CaseDejaOccupeeException {

		Fetard fetard = fetardDao.rechercher(pseudoFetard);
		Soiree soiree = soireeDao.rechercheSoireeNonFinie(nomSoiree);

		try {
			soiree.setJoueurPret(fetard, soiree, tables);
		} catch (MemePositionException e) {
			return null;
		} catch (CaseDejaOccupeeException e) {
			return null;
		}

		soireeDao.mettreAJour(soiree);
		
		return soiree;
	}

	@Override
	public Soiree lancerUneTournee(String nomSoiree, String pseudoFetard,
			List<XY> coord) throws Exception {
		Soiree soiree = soireeDao.rechercheSoireeNonFinie(nomSoiree);
		if (soiree == null) {
			throw new Exception("soiree null !");
		}
		if (!soiree.getFetard_Soiree_Courant().getFetard().getPseudo()
				.equalsIgnoreCase(pseudoFetard)) {
			throw new Exception("C'est n'est pas le tour de ce joueur !");
		}

		try {
			soiree.lancerTournee(soiree, coord);
		} catch (ArgumentInvalideException e) {
			throw new Exception("mauvaises coordonnées");
		} catch (DejaToucheException e) {
			throw new Exception("coordonnées sur bateau déjà touché !");
		}

		soiree = soireeDao.chargerTournee(pseudoFetard, soiree);
		soireeDao.mettreAJour(soiree);
		return soiree;
	}

	@Override
	public List<Soiree> listerSoireesFinies(String pseudoFetard) {
		Fetard fetard = fetardDao.rechercher(pseudoFetard);
		List<Soiree> soirees = soireeDao.listerSoireeFinie(pseudoFetard);
		if (fetard == null) {
			return null;
		}
		soirees = soireeDao.chargerTournee(pseudoFetard, soirees);
		return soirees;
	}

	@Override
	public Soiree fetardDeconnecte(String nomSoiree, String pseudoFetard) {
		Fetard fetard = fetardDao.rechercher(pseudoFetard);
		Soiree soiree = soireeDao.rechercheSoireeNonFinie(nomSoiree);
		if (fetard == null) {
			return null;
		}
		if (soiree == null) {
			return null;
		}
		soiree.fetardDeconnecte(fetard, soiree);
		soireeDao.mettreAJour(soiree);
		return soiree;
	}

	@Override
	public Soiree commencerPlacement(String nomSoiree) throws Exception {
		Soiree soiree = soireeDao.rechercheSoireeNonFinie(nomSoiree);

		if (soiree == null) {
			throw new Exception("soiree null");
		}
		if (soiree.getEtat() != Etat.EN_PLACEMENT) {
			throw new Exception(
					"conditions non remplies pour commencer le placement");
		}
		return soiree;
	}

	@Override
	public Soiree commencerSoiree(String nomSoiree) throws Exception {
		Soiree soiree = soireeDao.rechercheSoireeNonFinie(nomSoiree);
		if (soiree == null) {
			throw new Exception("soiree null");
		}
		if (soiree.getEtat() != Etat.EN_COURS) {
			throw new Exception(
					"conditions non remplies pour commencer la soirée");
		}
		return soiree;
	}

	@Override
	public Soiree finirSoiree(String nomSoiree, Calendar dateDebut)
			throws Exception {
		Soiree soiree = soireeDao.rechercheSoireeFinie(nomSoiree, dateDebut);
		if (soiree == null) {
			throw new Exception("soiree null");
		}
		return soiree;
	}
}
