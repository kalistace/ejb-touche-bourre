package domaineTest;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import projetAAE.ipl.domaine.ETable;
import projetAAE.ipl.domaine.Fetard;
import projetAAE.ipl.domaine.Soiree;
import projetAAE.ipl.domaine.Soiree.Etat;
import projetAAE.ipl.exceptions.ArgumentInvalideException;
import projetAAE.ipl.exceptions.CaseDejaOccupeeException;
import projetAAE.ipl.exceptions.MemePositionException;
import projetAAE.ipl.exceptions.TableDejaPlaceeException;
import projetAAE.ipl.usecases.GestionFetard;
import projetAAE.ipl.usecases.GestionSoiree;
import projetAAE.ipl.valueObject.XY;

public class GestionSoireeTest {

	private static String nomSoiree;
	private static String pseudoFetard1;
	private static String pseudoFetard2;
	private static String pseudoFetard3;
	// private static Fetard fetard;
	private static Soiree soiree_en_attente_de_partenaire1;
	private static Soiree soiree_en_attente_de_partenaire2;
	private static Soiree soiree_en_placement;
	private static Soiree soiree_en_cours;
	private static GestionSoiree gs;
	private static GestionFetard gf;

	@BeforeClass
	public static void init() throws Exception {

		nomSoiree = "MA SOIREE";
		pseudoFetard1 = "Stefano";
		pseudoFetard2 = "Cedric";
		pseudoFetard3 = "Michael";

		Context jndi1 = new InitialContext();
		gf = (GestionFetard) jndi1
				.lookup("TB/GestionFetardImpl/remote");

		Context jndi2 = new InitialContext();
		gs = (GestionSoiree) jndi2
				.lookup("TB/GestionSoireeImpl/remote");

		gf.enregistrer(pseudoFetard1);
		gf.enregistrer(pseudoFetard2);
		gf.enregistrer(pseudoFetard3);
		
	}

	@Before
	public void setUp() {

	}

	/*
	 * Vérifier que, quand on crée une soirée, son état est bien à
	 * INITIAL_ATTENTE_FETARD
	 */
	@Test
	public void testCreerSoiree1() {
		soiree_en_attente_de_partenaire1 = gs.creerSoiree(nomSoiree, pseudoFetard1);
		assertTrue(soiree_en_attente_de_partenaire1.getEtat() == Etat.INITIAL_ATTENTE_FETARD);
		soiree_en_attente_de_partenaire2 = gs.creerSoiree(nomSoiree+"2", pseudoFetard1);
		assertTrue(soiree_en_attente_de_partenaire2.getEtat() == Etat.INITIAL_ATTENTE_FETARD);
	}

	/*
	 * Vérifier que, quand on crée une soirée, 
	 * son créateur fait bien partie de la soirée
	 */
	@Test
	public void testCreerSoiree2() {
		assertTrue(soiree_en_attente_de_partenaire1.getFetardSoiree1().getFetard().getPseudo().equals(pseudoFetard1));
	}
	
	/*
	 * Vérifier que, tant que la partie n'a pas commencé, il n'y a toujours pas de joueur courant
	 */
	@Test
	public void testCreerSoiree3() {
		assertTrue(soiree_en_attente_de_partenaire1.getFetard_Soiree_Courant() == null);
	}
	
	/*
	 * Vérifier que, tant que la partie n'a pas commencé, il y a toujours 0 joueurs "prêts"
	 */
	@Test
	public void testCreerSoiree5() {
		assertTrue(soiree_en_attente_de_partenaire1.getNbrFetardPret() == 0);
	}
	
	/*
	 * Vérifier que, tant que la partie n'a pas commencé, il n'y a toujours pas de gagnant
	 */
	@Test
	public void testCreerSoiree6() {
		assertTrue(soiree_en_attente_de_partenaire1.getGagnant() == null);
	}
	
	/*
	 * Vérifier que, si on crée une soirée pour un client qui n'existe pas, on retourne null
	 */
	@Test // EXCEPTION !
	public void testCreerSoiree7() { 
		Soiree s = gs.creerSoiree("Une soiree", "unNomQuiNexistePasDansLaDB");
		assertTrue(s == null);
	}
	
	/*
	 * Vérifier qu'on ne puisse pas créer une partie du même nom qu'une autre déjà toujours non finie
	 */
	@Test // EXCEPTION !
	public void testCreerSoiree8() { 
		Soiree s = gs.creerSoiree(nomSoiree, pseudoFetard1);
		assertTrue(s == null);
	}
	
	/*
	 * Vérifier que le nombre de joueurs connectés vaut bien 1
	 */
	@Test
	public void testCreerSoiree9() { 
		Soiree s = gs.creerSoiree(nomSoiree, pseudoFetard1);
		assertTrue(s.getNbrFetardConnecte() == 1);
	}
	
	

	/*
	 * Vérifier qu'un client qui n'existe pas dans la DB ne puisse pas rejoindre une soirée
	 */
	@Test // EXCEPTION !
	public void testRejoindreSoiree1() {
		Soiree s = gs.rejoindreSoiree(nomSoiree, "UnGarsQuiNexistePas");
		assertTrue(s == null);
	}
	
	/*
	 * Vérifier qu'un joueur ne puisse pas rejoindre une partie inexistante
	 */
	@Test // EXCEPTION !
	public void testRejoindreSoiree2() {
		Soiree s = gs.rejoindreSoiree("SoireeQuiNexistePas", pseudoFetard2);
		assertTrue(s == null);
	}
	
	/*
	 * Vérifier qu'un joueur ne puisse pas rejoindre une partie terminée
	 */
	@Test // EXCEPTION !
	public void testRejoindreSoiree3() {
		Soiree s = new Soiree("un nom", new Fetard("un pseudo"));
		s.setEtat(Etat.FINIE);
		Soiree s2 = gs.rejoindreSoiree("un nom", pseudoFetard1);
		assertTrue(s2 == null);
	}
	
	/*
	 * Vérifier qu'un joueur qui rejoint une soirée y fait bien partie
	 */
	@Test
	public void testRejoindreSoiree4() {
		soiree_en_placement = gs.rejoindreSoiree(nomSoiree, pseudoFetard2);
		
		assertTrue(soiree_en_placement.getFetardSoiree2().getFetard().getPseudo().equals(pseudoFetard2));
		assertTrue(soiree_en_placement.getAdversaire(soiree_en_placement.getFetardSoiree1()).getFetard().getPseudo().equals(pseudoFetard2));
	}
	
	/*
	 * Vérifier que lors d'un ajout d'adversaire, le nombre de joueurs connectés vaut bien 2
	 */
	@Test
	public void testRejoindreSoiree5() {
		
		assertTrue(soiree_en_placement.getNbrFetardConnecte() == 2);
	}
	
	/*
	 * Vérifier que lors d'un ajout d'adversaire, l'état de la partie vaut désormais EN_PLACEMENT
	 */
	@Test
	public void testRejoindreSoiree6() {
		
		assertTrue(soiree_en_placement.getEtat() == Etat.EN_PLACEMENT);
	}

	
	/*
	 * Vérifier qu'un joueur ne puisse pas rejoindre une partie complète
	 */
	@Test // EXCEPTION !
	public void testRejoindreSoiree7() {
		Soiree s = gs.rejoindreSoiree(nomSoiree, pseudoFetard3);
		assertTrue(s == null);
	}
	
	
	

	/*
	 * Vérifier qu'on renvoie bien la liste des parties en attente de partenaires
	 */
	@Test
	public void testListerPartiesEnAttenteDePartenaire() {
		List<Soiree> soireesEnAttente = gs.listerPartiesEnAttenteDePartenaire();
		assertTrue(soireesEnAttente.contains(soiree_en_attente_de_partenaire1));
		assertTrue(soireesEnAttente.contains(soiree_en_attente_de_partenaire2));
	}
	
	/*
	 * Vérifier qu'on lance bien une exception quand on envoie 2 fois la même coordonnée pour positionner une table
	 */
	@Test(expected = MemePositionException.class)
	public void testFetardPret1() throws MemePositionException, TableDejaPlaceeException, CaseDejaOccupeeException {
		Map<ETable, List<XY>> map = new HashMap<ETable, List<XY>>();
		List<XY> liste1 = new ArrayList<XY>();
		try {
		liste1.add(new XY(1,1));
		liste1.add(new XY(1,1));
		} catch (ArgumentInvalideException e) {
			throw new InternalError();
		}
		map.put(ETable.Comptoir, liste1);
		gs.fetardPret(nomSoiree, pseudoFetard1, map);
	}
	

	/*
	 * Tester qu'une exception est bien lancée quand on place une table sur une case où se trouve déjà une table
	 */
	@Test(expected = CaseDejaOccupeeException.class)
	public void testFetardPret2() throws MemePositionException, TableDejaPlaceeException, CaseDejaOccupeeException {
		Map<ETable, List<XY>> map = new HashMap<ETable, List<XY>>();
		List<XY> liste1 = new ArrayList<XY>();
		List<XY> liste2 = new ArrayList<XY>();
		try {
		liste1.add(new XY(1,1));
		liste2.add(new XY(1,1));
		} catch (ArgumentInvalideException e) {
			throw new InternalError();
		}
		map.put(ETable.Comptoir, liste1);
		map.put(ETable.TableDeCouple, liste2);
		gs.fetardPret(nomSoiree, pseudoFetard1, map);
	}
	
	
	/*
	 * Vérifier que le nombre de fêtards prêts a bien été incrémenté quand un joueur a placé ses tables
	 */
	@Test
	public void testFetardPret3() throws MemePositionException, TableDejaPlaceeException, CaseDejaOccupeeException {
		Map<ETable, List<XY>> map = new HashMap<ETable, List<XY>>();
		List<XY> liste1 = new ArrayList<XY>();
		List<XY> liste2 = new ArrayList<XY>();
		try {
		liste1.add(new XY(1,1));
		liste2.add(new XY(2,2));
		} catch (ArgumentInvalideException e) {
			throw new InternalError();
		}
		map.put(ETable.Comptoir, liste1);
		map.put(ETable.TableDeCouple, liste2);
		Soiree s = gs.fetardPret(nomSoiree, pseudoFetard1, map);
		assertTrue(s.getNbrFetardPret() == 1);
	}
	
	
	/*
	 * Vérifier que, quand le second joueur a placé ses tables, l'état de la partie devient EN_COURS
	 */
	@Test
	public void testFetardPret4() throws MemePositionException, TableDejaPlaceeException, CaseDejaOccupeeException {
		Map<ETable, List<XY>> map = new HashMap<ETable, List<XY>>();
		List<XY> liste1 = new ArrayList<XY>();
		List<XY> liste2 = new ArrayList<XY>();
		try {
		liste1.add(new XY(7,7));
		liste2.add(new XY(8,8));
		} catch (ArgumentInvalideException e) {
			throw new InternalError();
		}
		map.put(ETable.Comptoir, liste1);
		map.put(ETable.TableDeCouple, liste2);
		soiree_en_cours = gs.fetardPret(nomSoiree, pseudoFetard2, map);
		assertTrue(soiree_en_cours.getEtat() == Etat.EN_COURS);
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
