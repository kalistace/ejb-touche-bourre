package projetAAE.ipl.usecases;

import javax.ejb.Remote;

import projetAAE.ipl.domaine.Fetard;

@Remote
public interface GestionFetard {
	Fetard enregistrer(String fetard);
}
