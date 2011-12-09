package projetAAE.ipl.daoimpl;

import javax.ejb.Singleton;

import projetAAE.ipl.dao.FetardDao;
import projetAAE.ipl.domaine.Fetard;

@Singleton
public class FetardDaoImpl extends DaoImpl<Integer, Fetard> implements FetardDao {

	@Override
	public Fetard rechercher(String pseudo) {
		String queryString = "select j from Fetard j where j.pseudo = ?1";
		return recherche(queryString, pseudo);
	}
	
}
