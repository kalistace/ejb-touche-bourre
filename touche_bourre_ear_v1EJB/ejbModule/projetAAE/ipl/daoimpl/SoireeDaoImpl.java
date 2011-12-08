package projetAAE.ipl.daoimpl;

import java.util.List;

import javax.ejb.Stateless;

import projetAAE.ipl.dao.SoireeDao;
import projetAAE.ipl.domaine.Fetard;
import projetAAE.ipl.domaine.Soiree;
import projetAAE.ipl.domaine.Soiree.Etat;

@Stateless
public class SoireeDaoImpl extends DaoImpl<Integer, Soiree> implements SoireeDao {

	@Override
	public Soiree rechercher(String pseudo) {
		String queryString = "select j from Soiree j where j.pseudo = ?1";
		return recherche(queryString, pseudo);
	}

	@Override
	public List<Soiree> listerSoireeEnAttenteDeJoueur() {
		String etat = Etat.INITIAL_ATTENTE_FETARD.toString();
		String queryString = "select j from Soiree j where j.etat = ?1";
		return liste(queryString, etat);
	}

	@Override
	public List<Soiree> listerSoireeFinie(String pseudoFetard) {
		String etat = Etat.FINIE.toString();
		String queryString = "select s from Soiree s where s.etat = ?1 and s.fetardSoiree1.fetard.pseudo = ?2";
		return liste(queryString, etat, pseudoFetard);
	}
}
