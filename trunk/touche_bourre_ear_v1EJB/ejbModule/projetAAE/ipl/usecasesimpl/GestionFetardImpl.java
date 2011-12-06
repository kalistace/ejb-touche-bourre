package projetAAE.ipl.usecasesimpl;

import javax.ejb.Stateless;

import projetAAE.ipl.domaine.Fetard;
import projetAAE.ipl.usecases.GestionFetard;

@Stateless
public class GestionFetardImpl implements GestionFetard{
	@Override
	public Fetard enregistrer(String fetard) {
		return new Fetard(fetard);
	}

}
