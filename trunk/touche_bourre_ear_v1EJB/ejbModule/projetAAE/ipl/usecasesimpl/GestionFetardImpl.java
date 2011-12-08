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
	public Fetard enregistrer(String pseudo) {
		Fetard fetard  = fetardDao.rechercher(pseudo);
		if(fetard == null){
			fetard = new Fetard(pseudo);
			fetardDao.enregistrer(fetard);
		}

		return fetard;
	}

}
