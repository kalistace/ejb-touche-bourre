package projetAAE.ipl.usecasesimpl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jboss.weld.context.ejb.Ejb;

import projetAAE.ipl.dao.FetardDao;
import projetAAE.ipl.domaine.Fetard;
import projetAAE.ipl.usecases.GestionFetard;

@Stateless
public class GestionFetardImpl implements GestionFetard{
	@EJB
	private FetardDao fetardDao;
	@Override
	public Fetard enregistrer(String fetard) {
		Fetard nouveauFetard = new Fetard(fetard);
		fetardDao.enregistrer(nouveauFetard);
		return nouveauFetard;
	}

}
