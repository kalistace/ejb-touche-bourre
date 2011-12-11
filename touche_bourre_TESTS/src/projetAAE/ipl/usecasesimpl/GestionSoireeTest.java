package projetAAE.ipl.usecasesimpl;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import projetAAE.ipl.domaine.Biere;
import projetAAE.ipl.domaine.ETable;
import projetAAE.ipl.domaine.Fetard;
import projetAAE.ipl.domaine.Soiree;
import projetAAE.ipl.domaine.Soiree.Etat;
import projetAAE.ipl.domaine.TablePlacee;
import projetAAE.ipl.domaine.Tournee;
import projetAAE.ipl.exceptions.CaseDejaOccupeeException;
import projetAAE.ipl.exceptions.MemePositionException;
import projetAAE.ipl.exceptions.TableDejaPlaceeException;
import projetAAE.ipl.usecases.GestionFetard;
import projetAAE.ipl.usecases.GestionSoiree;
import projetAAE.ipl.valueObject.XY;

public class GestionSoireeTest {

	private static String nomSoireeQuiSeJoue;
	private static String nomSoireeEnAttente1;
	private static String nomSoireeEnAttente2;
	private static String pseudoFetard1;
	private static String pseudoFetard2;
	private static String pseudoFetard3;
	private static Soiree soireeQuiSeJoue;
	private static Soiree soireeEnAttente1;
	private static Soiree soireeEnAttente2;
	private static GestionSoiree gs;
	private static GestionFetard gf;

	@BeforeClass
	public static void init() throws Exception {

		nomSoireeQuiSeJoue = "MA SOIREE1";
		nomSoireeEnAttente1 = "MA SOIREE2";
		nomSoireeEnAttente2 = "MA SOIREE3";
		pseudoFetard1 = "Stefano";
		pseudoFetard2 = "Cedric";
		pseudoFetard3 = "Michael";

		Context jndi1 = new InitialContext();
		gf = (GestionFetard) jndi1.lookup("toucheBourre/GestionFetardImpl/remote");

		Context jndi2 = new InitialContext();
		gs = (GestionSoiree) jndi2.lookup("toucheBourre/GestionSoireeImpl/remote");

		gf.enregistrer(pseudoFetard1);
		gf.enregistrer(pseudoFetard2);
		gf.enregistrer(pseudoFetard3);

	}

	@Before
	public void setUp() {

	}

	/*
	 * V�rifier que, quand on cr�e une soir�e, son �tat est bien �
	 * INITIAL_ATTENTE_FETARD
	 */
	@Test
	public void testCreerSoiree1() {
		soireeQuiSeJoue = gs.creerSoiree(nomSoireeQuiSeJoue, pseudoFetard1);
		assertTrue(soireeQuiSeJoue.getEtat() == Etat.INITIAL_ATTENTE_FETARD);
		soireeEnAttente1 = gs.creerSoiree(nomSoireeEnAttente1, pseudoFetard1);
		assertTrue(soireeEnAttente1.getEtat() == Etat.INITIAL_ATTENTE_FETARD);
		soireeEnAttente2 = gs.creerSoiree(nomSoireeEnAttente2, pseudoFetard1);
		assertTrue(soireeEnAttente1.getEtat() == Etat.INITIAL_ATTENTE_FETARD);
	}

	/*
	 * V�rifier que, quand on cr�e une soir�e, son cr�ateur fait bien partie de
	 * la soir�e
	 */
	@Test
	public void testCreerSoiree2() {
		assertTrue(soireeQuiSeJoue.getFetardSoiree1().getFetard().getPseudo()
				.equals(pseudoFetard1));
	}

	/*
	 * V�rifier que, tant que la partie n'a pas commenc�, il n'y a toujours pas
	 * de joueur courant
	 */
	@Test
	public void testCreerSoiree3() {
		assertTrue(soireeQuiSeJoue.getFetard_Soiree_Courant() == null);
	}

	/*
	 * V�rifier que, tant que la partie n'a pas commenc�, il y a toujours 0
	 * joueurs "pr�ts"
	 */
	@Test
	public void testCreerSoiree4() {
		assertTrue(soireeQuiSeJoue.getNbrFetardPret() == 0);
	}

	/*
	 * V�rifier que, tant que la partie n'a pas commenc�, il n'y a toujours pas
	 * de gagnant
	 */
	@Test
	public void testCreerSoiree5() {
		assertTrue(soireeQuiSeJoue.getGagnant() == null);
	}

	/*
	 * V�rifier que, si on cr�e une soir�e pour un joueur qui n'existe pas, on
	 * retourne null
	 */
	@Test
	// EXCEPTION !
	public void testCreerSoiree6() {
		Soiree s = gs.creerSoiree("Une soiree", "unNomQuiNexistePasDansLaDB");
		assertTrue(s == null);
	}

	/*
	 * V�rifier qu'on ne puisse pas cr�er une partie du m�me nom qu'une autre
	 * d�j� toujours non finie
	 */
	@Test
	// EXCEPTION !
	public void testCreerSoiree7() {
		Soiree s = gs.creerSoiree(nomSoireeQuiSeJoue, pseudoFetard1);
		assertTrue(s == null);
	}

	/*
	 * V�rifier que le nombre de joueurs connect�s vaut bien 1
	 */
	@Test
	public void testCreerSoiree8() {
		assertTrue(soireeQuiSeJoue.getNbrFetardConnecte() == 1);
	}
	
	/*
	 * V�rifier que le nombre de bi�res par tourn�e est correctement initialis�
	 */
	@Test
	public void testCreerSoiree9() {

		assertTrue(soireeQuiSeJoue.getFetardSoiree1().getNbBieresParTournee() == 5);
	}

	/*
	 * V�rifier qu'un client qui n'existe pas dans la DB ne puisse pas rejoindre
	 * une soir�e
	 */
	@Test
	// EXCEPTION !
	public void testRejoindreSoiree1() {
		Soiree s = gs
				.rejoindreSoiree(nomSoireeQuiSeJoue, "UnGarsQuiNexistePas");
		assertTrue(s == null);
	}

	/*
	 * V�rifier qu'un joueur ne puisse pas rejoindre une partie inexistante
	 */
	@Test
	// EXCEPTION !
	public void testRejoindreSoiree2() {
		Soiree s = gs.rejoindreSoiree("SoireeQuiNexistePas", pseudoFetard2);
		assertTrue(s == null);
	}

	/*
	 * V�rifier qu'un joueur ne puisse pas rejoindre une partie termin�e
	 */
	@Test
	// EXCEPTION !
	public void testRejoindreSoiree3() {
		Soiree s = new Soiree("un nom", new Fetard("un pseudo"));
		s.setEtat(Etat.FINIE);
		Soiree s2 = gs.rejoindreSoiree("un nom", pseudoFetard1);
		assertTrue(s2 == null);
	}

	/*
	 * V�rifier qu'un joueur qui rejoint une soir�e y fait bien partie
	 */
	@Test
	public void testRejoindreSoiree4() {
		soireeQuiSeJoue = gs.rejoindreSoiree(nomSoireeQuiSeJoue, pseudoFetard2);
		assertTrue(soireeQuiSeJoue.getFetardSoiree2().getFetard().getPseudo()
				.equals(pseudoFetard2));
		assertTrue(soireeQuiSeJoue
				.getAdversaire(soireeQuiSeJoue.getFetardSoiree1()).getFetard()
				.getPseudo().equals(pseudoFetard2));
	}

	/*
	 * V�rifier que lors d'un ajout d'adversaire, le nombre de joueurs connect�s
	 * vaut bien 2
	 */
	@Test
	public void testRejoindreSoiree5() {

		assertTrue(soireeQuiSeJoue.getNbrFetardConnecte() == 2);
	}

	/*
	 * V�rifier que lors d'un ajout d'adversaire, l'�tat de la partie vaut
	 * d�sormais EN_PLACEMENT
	 */
	@Test
	public void testRejoindreSoiree6() {

		assertTrue(soireeQuiSeJoue.getEtat() == Etat.EN_PLACEMENT);
	}

	
	/*
	 * V�rifier que le nombre de bi�res par tourn�e est correctement initialis� pour le joueur qui a rejoint la partie
	 */
	@Test
	public void testRejoindreSoiree7() {

		assertTrue(soireeQuiSeJoue.getFetardSoiree2().getNbBieresParTournee() == 5);
	}

	/*
	 * V�rifier qu'un joueur ne puisse pas rejoindre une partie compl�te
	 */
	@Test
	// EXCEPTION !
	public void testRejoindreSoiree8() {
		Soiree s = gs.rejoindreSoiree(nomSoireeQuiSeJoue, pseudoFetard3);
		assertTrue(s == null);
	}

	/*
	 * V�rifier qu'on renvoie bien la liste des parties en attente de
	 * partenaires
	 */
	@Test
	public void testListerPartiesEnAttenteDePartenaire() {
		List<Soiree> soireesEnAttente = gs.listerPartiesEnAttenteDePartenaire();
		assertTrue(soireesEnAttente.size() == 2);
		assertTrue(soireesEnAttente.contains(soireeEnAttente1));
		assertTrue(soireesEnAttente.contains(soireeEnAttente2));
	}

	/*
	 * V�rifier qu'on lance bien une exception quand on envoie 2 fois la m�me
	 * coordonn�e pour positionner une table
	 */
	@Test
	// (expected = MemePositionException.class)
	public void testFetardPret1() throws MemePositionException,
			TableDejaPlaceeException, CaseDejaOccupeeException {
		Map<ETable, List<XY>> map = new HashMap<ETable, List<XY>>();
		List<XY> liste1 = new ArrayList<XY>();

		liste1.add(new XY(1, 1));
		liste1.add(new XY(1, 1));

		map.put(ETable.Comptoir, liste1);
		Soiree s = gs.fetardPret(nomSoireeQuiSeJoue, pseudoFetard1, map);
		assertTrue(s == null);
	}

	/*
	 * Tester qu'une exception est bien lanc�e quand on place une table sur une
	 * case o� se trouve d�j� une autre table
	 */
	@Test
	// (expected = CaseDejaOccupeeException.class)
	public void testFetardPret2() throws MemePositionException,
			TableDejaPlaceeException, CaseDejaOccupeeException {
		Map<ETable, List<XY>> map = new HashMap<ETable, List<XY>>();
		List<XY> liste1 = new ArrayList<XY>();
		List<XY> liste2 = new ArrayList<XY>();

		liste1.add(new XY(1, 1));
		liste2.add(new XY(1, 1));

		map.put(ETable.Comptoir, liste1);
		map.put(ETable.TableDeCouple, liste2);
		Soiree s = gs.fetardPret(nomSoireeQuiSeJoue, pseudoFetard1, map);
		assertTrue(s == null);
	}

	/*
	 * V�rifier que le nombre de f�tards pr�ts a bien �t� incr�ment� quand un
	 * joueur a plac� ses tables
	 */
	@Test
	public void testFetardPret3() throws MemePositionException,
			CaseDejaOccupeeException {
		Map<ETable, List<XY>> map = new HashMap<ETable, List<XY>>();
		List<XY> liste1 = new ArrayList<XY>();
		List<XY> liste2 = new ArrayList<XY>();

		liste1.add(new XY(1, 1));
		liste2.add(new XY(2, 2));
		liste2.add(new XY(7, 7));

		map.put(ETable.Comptoir, liste1);
		map.put(ETable.TableDeCouple, liste2);
		soireeQuiSeJoue = gs.fetardPret(nomSoireeQuiSeJoue, pseudoFetard1, map);
		assertTrue(soireeQuiSeJoue.getNbrFetardPret() == 1);
	}

	/*
	 * V�rifier que le f�tard qui a plac� ses tables est bien "pr�t"
	 */
	@Test
	public void testFetardPret4() {

		assertTrue(soireeQuiSeJoue.getFetardSoiree1().isPret());
	}

	/*
	 * V�rifier que, quand le f�tard a plac� ses tables, les vies de celles-ci
	 * sont bien initialis�es
	 */
	@Test
	public void testFetardPret5() {

		int tablesTestees = 0;
		for (TablePlacee tp : soireeQuiSeJoue.getFetardSoiree1()
				.getMesTablesPlacees()) {
			if (tp.getTable().equals(ETable.Comptoir)) {
				assertTrue(tp.getVies() == 1);
				tablesTestees++;
			} else if (tp.getTable().equals(ETable.TableDeCouple)) {
				assertTrue(tp.getVies() == 2);
				tablesTestees++;
			}
		}
		if (tablesTestees != 2)
			fail();
	}


	/*
	 * V�rifier que, quand le second joueur a plac� ses tables, l'�tat de la
	 * partie devient EN_COURS
	 */
	@Test
	public void testFetardPret6() throws MemePositionException,
			CaseDejaOccupeeException {
		Map<ETable, List<XY>> map = new HashMap<ETable, List<XY>>();
		List<XY> liste1 = new ArrayList<XY>();
		List<XY> liste2 = new ArrayList<XY>();

		liste1.add(new XY(3, 3));
		liste2.add(new XY(4, 4));

		map.put(ETable.Comptoir, liste1);
		map.put(ETable.TableDePotes, liste2);
		soireeQuiSeJoue = gs.fetardPret(nomSoireeQuiSeJoue, pseudoFetard2, map);
		assertTrue(soireeQuiSeJoue.getEtat() == Etat.EN_COURS);
	}
	
	/*
	 * V�rifier que le premier f�tard � jouer est bien celui qui a plac� le premier ses tables
	 */
	@Test
	public void testFetardPret7() {

		
		assertTrue(soireeQuiSeJoue.getPremierFetardAJouer().getFetard()
				.getPseudo().equals(pseudoFetard1));
	}

	/*
	 * V�rifier qu'on lance bien une exception quand un joueur lance une tourn�e
	 * pour une soir�e qui n'existe pas
	 */
	@Test(expected = Exception.class)
	public void testLancerUneTournee1() throws Exception {
		List<XY> coord = new ArrayList<XY>();
		coord.add(new XY(1, 2));
		coord.add(new XY(3, 4));
		coord.add(new XY(5, 6));
		coord.add(new XY(7, 8));
		coord.add(new XY(9, 9));
		gs.lancerUneTournee("SoireeQuiNexistePas", pseudoFetard2, coord);
	}

	/*
	 * TOUR : pseudoFetard1 - V�rifier qu'on lance bien une exception quand un
	 * joueur lance une tourn�e alors que ce n'est pas son tour
	 */
	@Test(expected = Exception.class)
	public void testLancerUneTournee2() throws Exception {
		List<XY> coord = new ArrayList<XY>();
		coord.add(new XY(1, 2));
		coord.add(new XY(3, 4));
		coord.add(new XY(5, 6));
		coord.add(new XY(7, 8));
		coord.add(new XY(9, 9));
		gs.lancerUneTournee(nomSoireeQuiSeJoue, pseudoFetard2, coord);
	}

	/*
	 * V�rifier qu'une exception est bien lanc�e quand on n'envoie pas le bon
	 * nombre de bi�res
	 */
	@Test(expected = Exception.class)
	public void testLancerUneTournee3() throws Exception {
		List<XY> coord = new ArrayList<XY>();
		coord.add(new XY(1, 2));
		coord.add(new XY(3, 4));
		gs.lancerUneTournee(nomSoireeQuiSeJoue, pseudoFetard1, coord);
	}

	/*
	 * V�rifier que, quand une tourn�e est correctement envoy�e, le joueur
	 * contient bien la tourn�e
	 */
	@Test
	public void testLancerUneTournee4() throws Exception {
		List<XY> coord = new ArrayList<XY>();
		coord.add(new XY(1, 2));
		coord.add(new XY(3, 4));
		coord.add(new XY(4, 4));
		coord.add(new XY(7, 8));
		coord.add(new XY(9, 9));
		soireeQuiSeJoue = gs.lancerUneTournee(nomSoireeQuiSeJoue,
				pseudoFetard1, coord);
		Tournee t = soireeQuiSeJoue.getFetardSoiree1().getMesTournees().get(0);

		for (Biere b : t.getBieres()) {
			boolean trouve = false;
			for (XY xy : coord) {
				if (b.getX() == xy.getX() && b.getY() == xy.getY()) {
					trouve = true;
					break;
				}
			}
			assertTrue(trouve);
		}
	}

	/*
	 * V�rifier que, quand une tourn�e est correctement envoy�e, l'adversaire
	 * devient le joueur courant
	 */
	@Test
	public void testLancerUneTournee5() {
		assertTrue(soireeQuiSeJoue.getFetard_Soiree_Courant().getFetard()
				.getPseudo().equals(pseudoFetard2));
	}

	/*
	 * V�rifier que, quand une bi�re a touch� une table adverse, sa tableTouch�e
	 * est correctement attribu�e Et que pour les autres, elle n'est pas d�finie
	 * (null)
	 */
	@Test
	public void testLancerUneTournee6() {

		for (Biere b : soireeQuiSeJoue.getFetardSoiree1().getMesTournees()
				.get(0).getBieres()) {
			if (b.getX() == 4 && b.getY() == 4) {
				assertTrue(b.getTableTouchee() == ETable.TableDePotes);
			} else {
				assertTrue(b.getTableTouchee() == null);
			}
		}
	}

	/*
	 * V�rifier que, quand une bi�re a touch� une table adverse, sa vie est
	 * correctement d�cr�ment�e
	 */
	@Test
	public void testLancerUneTournee7() {

		for (TablePlacee tp : soireeQuiSeJoue.getFetardSoiree2()
				.getMesTablesPlacees()) {
			if (tp.getTable().equals(ETable.TableDePotes)) {
				assertTrue(tp.getVies() == 0);
				return;
			}
		}
		fail();
	}

	/*
	 * V�rifier que, quand une tourn�e a coul� une table adverse, le nombre de
	 * "tirs" (bi�res) autoris� pour la prochaine tourn�e est correctement
	 * d�cr�ment�
	 */
	@Test
	public void testLancerUneTournee8() {
		assertTrue(soireeQuiSeJoue.getFetardSoiree1().getNbBieresParTournee() == 4);
	}

}
