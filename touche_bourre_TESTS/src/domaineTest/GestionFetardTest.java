package domaineTest;

import static org.junit.Assert.assertTrue;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import projetAAE.ipl.domaine.Fetard;
import projetAAE.ipl.usecases.GestionFetard;


public class GestionFetardTest {
	
	

	private static String pseudoFetard1;
	private static GestionFetard gf;
	

	@BeforeClass
	public static void init() throws Exception {
		Context jndi = new InitialContext();
		gf = (GestionFetard) jndi
				.lookup("touche_bourre_ear_v1/GestionFetardImpl/remote");

	}

	@Before
	public void setUp() throws Exception {
		pseudoFetard1 = "Adrien";
	}

	/*
	 * Vérifier qu'un enregistrement initialise bien le pseudo du Fetard
	 */
	@Test
	public void testEnregistrer() {
		Fetard f = gf.enregistrer(pseudoFetard1);
		assertTrue(f.getPseudo().equals(pseudoFetard1));
	}

}
