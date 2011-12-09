
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import projetAAE.ipl.domaine.Soiree;
import projetAAE.ipl.domaine.Soiree.Etat;
import projetAAE.ipl.usecases.GestionSoiree;

public class GestionSoireeTest {

	private static String nomSoiree;
	private static String pseudoFetard1;
	private static Soiree soiree;
	private static GestionSoiree gs;

	@BeforeClass
	public static void init() throws Exception {
		Context jndi = new InitialContext();
		gs = (GestionSoiree) jndi
				.lookup("touche_bourre_ear_v1/GestionSoireeImpl/remote");

	}

	@Before
	public void setUp() {
		nomSoiree = "MA SOIREE";
		pseudoFetard1 = "Michael Jackson";
	}

	/*
	 * V�rifier que, quand on cr�e une soir�e, son �tat est bien �
	 * INITIAL_ATTENTE_FETARD
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
