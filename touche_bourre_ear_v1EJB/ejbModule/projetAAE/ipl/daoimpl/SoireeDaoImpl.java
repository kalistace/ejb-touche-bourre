package projetAAE.ipl.daoimpl;

import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;

import projetAAE.ipl.dao.SoireeDao;
import projetAAE.ipl.domaine.Soiree;
import projetAAE.ipl.domaine.Soiree.Etat;
import projetAAE.ipl.domaine.Tournee;

@Stateless
public class SoireeDaoImpl extends DaoImpl<Integer, Soiree> implements SoireeDao {

	@Override
	public Soiree rechercher(String nomSoiree) {
		String queryString = "select s from Soiree s where s.nom = ?1";
		return recherche(queryString, nomSoiree);
	}

	@Override
	public List<Soiree> listerSoireeEnAttenteDeJoueur() {
		Etat etat = Etat.INITIAL_ATTENTE_FETARD;
		String queryString = "select s from Soiree s where s.etat = ?1";
		return liste(queryString, etat);
	}

	@Override
	public List<Soiree> listerSoireeFinie(String pseudoFetard) {
		Etat etat = Etat.FINIE;
		String queryString = "select s from Soiree s JOIN s.lesDeuxFetard_Soiree f where s.etat = ?1 and f.fetard.pseudo = ?2";
		return liste(queryString, etat, pseudoFetard);
	}

	@Override
	public Soiree rechercheSoireeNonFinie(String nomSoiree) {
		Etat etat = Etat.FINIE;
		String queryString = "select s from Soiree s where s.nom = ?1 and s.etat!=?2";
		return recherche(queryString, nomSoiree, etat);
	}

	@Override
	public Soiree chargerTournee(Soiree soiree) {
		List<Tournee> tournees = soiree.getFetardSoiree1().getMesTournees();
		List<Tournee> tournees2 = soiree.getFetardSoiree2().getMesTournees();
		tournees.addAll(tournees2);
		for(Tournee t : tournees){
			t.getBieres().size();
		}
		
		return soiree;
	}

	@Override
	public List<Soiree> chargerTournee(List<Soiree> soirees) {
		for(Soiree s : soirees){
			s = this.chargerTournee(s);
		}
		return soirees;
	}

	@Override
	public Soiree rechercheDerniereSoireeFinie(String nomSoiree) {
		Etat etat = Etat.FINIE;
		String queryString = "select s from Soiree s where s.id = (select max(s.id) from Soiree s  where s.nom = ?1 and s.etat = ?2)";
		return recherche(queryString, nomSoiree, etat);
	}
}
