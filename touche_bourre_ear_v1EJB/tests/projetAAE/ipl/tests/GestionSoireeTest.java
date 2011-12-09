package projetAAE.ipl.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import projetAAE.ipl.domaine.Soiree;
import projetAAE.ipl.domaine.Soiree.Etat;
import projetAAE.ipl.usecases.GestionSoiree;
import projetAAE.ipl.usecasesimpl.GestionSoireeImpl;

public class GestionSoireeTest {
	
	private String nomSoiree;
	private String pseudoFetard1;
	private Soiree soiree;
	private GestionSoiree gs;
	
	@Before
	public void setUp() {
		nomSoiree = "MA SOIREE";
		pseudoFetard1 = "Michael Jackson";
		gs = new GestionSoireeImpl();
	}
	
	/*
	 * Vérifier que, quand on crée une soirée, son état est bien à INITIAL_ATTENTE_FETARD
	 */
	@Test
	public void testCreerSoiree() {
		soiree = gs.creerSoiree(nomSoiree, pseudoFetard1);
		assertTrue(soiree.getEtat() == Etat.INITIAL_ATTENTE_FETARD);
	}

	@Test
	public void testRejoindreSoiree() {
		fail("Not yet implemented");
	}

	@Test
	public void testListerPartiesEnAttenteDePartenaire() {
		fail("Not yet implemented");
	}

	@Test
	public void testFetardPret() {
		fail("Not yet implemented");
	}

	@Test
	public void testLancerUneTournee() {
		fail("Not yet implemented");
	}

	@Test
	public void testListerSoireesFinies() {
		fail("Not yet implemented");
	}

}
