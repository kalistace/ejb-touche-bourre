package projetAAE.ipl.daoimpl;

import java.util.List;

import javax.ejb.Stateless;

import projetAAE.ipl.dao.FetardDao;
import projetAAE.ipl.domaine.Fetard;

@Stateless
public class FetardDaoImpl extends DaoImpl<Integer, Fetard> implements FetardDao {

	@Override
	public Fetard rechercher(String pseudo) {
		String queryString = "select j from Fetard j where j.pseudo = ?1";
		return recherche(queryString, pseudo);
	}
	
}
