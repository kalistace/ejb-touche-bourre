package projetAAE.ipl.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import projetAAE.ipl.domaine.Biere;
import projetAAE.ipl.domaine.ETable;
import projetAAE.ipl.domaine.Fetard_Soiree;
import projetAAE.ipl.domaine.Soiree;
import projetAAE.ipl.domaine.Tournee;
import projetAAE.ipl.usecases.GestionSoiree;

/**
 * Servlet implementation class ListerJournaux
 */
@WebServlet("/listerJournaux.html")
public class ListerJournaux extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private GestionSoiree ucc;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(response.encodeRedirectURL("accueil.jsp"));
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("deprecation")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null || (session!= null && session.getAttribute("pseudo") == null)) {
			response.sendRedirect(response.encodeRedirectURL("index.jsp?timeout=1"));
			return;
		}
		List<Soiree> journaux = ucc.listerSoireesFinies((String) session.getAttribute("pseudo"));
		request.setAttribute("monPseudo", session.getAttribute("pseudo"));
		request.setAttribute("journaux", journaux);
		Map<Integer, List<String>> mapTournees = new HashMap<Integer, List<String>>();
		for (Soiree soiree : journaux) {
			List<Tournee> tournees = soiree.listePermuteeEtOrdonneeDeTournees();
			System.out.println(tournees);
			List<String> resultats = new ArrayList<String>();
			Fetard_Soiree fetardCourant = soiree.getPremierFetardAJouer();
			Fetard_Soiree nextFetard;
			if (fetardCourant != null) {
				if (fetardCourant.equals(soiree.getFetardSoiree1())) nextFetard = soiree.getFetardSoiree2();
				else nextFetard = soiree.getFetardSoiree1();
				// init maps
				Map<ETable, Integer> nbrHits = new HashMap<ETable, Integer>();
				Map<ETable, Boolean> isCoule = new HashMap<ETable, Boolean>();
				Map<ETable, String> tableNames = new HashMap<ETable, String>();
				for (ETable etable : ETable.values()) {
					nbrHits.put(etable, 0);
					isCoule.put(etable, false);
					switch (etable) {
						case Comptoir:
							tableNames.put(etable, "Comptoir");
							break;
						case TableDeCouple:
							tableNames.put(etable, "Table De Couple");
							break;
						case TableDeFilles:
							tableNames.put(etable, "Table De Filles");
							break;
						case TableDeGarcons:
							tableNames.put(etable, "Table De Garcons");
							break;
						case TableDePotes:
							tableNames.put(etable, "Table De Potes");
							break;
						default:
							tableNames.put(etable, "Undefined Name");
							break;
					}
				}
				
				// CR�ATION DES STRINGS RESULTATS
				resultats.add("Nom de la soirée: "+soiree.getNom());
				String date = "Début de la soirée: ";
				date += soiree.getDateDebut().get(Calendar.DAY_OF_MONTH) + "/";
				date += soiree.getDateDebut().get(Calendar.MONTH) + "/";
				date += soiree.getDateDebut().get(Calendar.YEAR) + " à ";
				date += soiree.getDateDebut().get(Calendar.HOUR_OF_DAY) + "h";
				if (soiree.getDateDebut().get(Calendar.MINUTE) < 10) date += "0";
				date += + soiree.getDateDebut().get(Calendar.MINUTE);
				resultats.add(date);
				resultats.add("Fêtards: "+fetardCourant.getFetard().getPseudo()+ " vs. "+nextFetard.getFetard().getPseudo());
				resultats.add(""); //espace
				for (Tournee tournee : tournees) {
					String res = fetardCourant.getFetard().getPseudo() + " sert en ";
					int nbrBieres = 0;
					int coupsRate = 0;
					for (Biere biere : tournee.getBieres()) {
						nbrBieres++;
						switch (biere.getY()) {
							case 0:
								res += "A";
								break;
							case 1:
								res += "B";
								break;
							case 2:
								res += "C";
								break;
							case 3:
								res += "D";
								break;
							case 4:
								res += "E";
								break;
							case 5:
								res += "F";
								break;
							case 6:
								res += "G";
								break;
							case 7:
								res += "H";
								break;
							case 8:
								res += "I";
								break;
							case 9:
								res += "J";
								break;
						}
						res += biere.getX();
						if (nbrBieres < tournee.getBieres().size()) res += ", ";
						if (biere.getTableTouchee() != null) {
							nbrHits.put(biere.getTableTouchee(), nbrHits.get(biere.getTableTouchee())+1);
							if (biere.aCoule())
								isCoule.put(biere.getTableTouchee(), true);
						} else {
							coupsRate++;
						}
					}
					res += " -> ";
					for (Map.Entry<ETable, Integer> entry : nbrHits.entrySet()) {
					    if (entry.getValue() > 0) {
					    	res += tableNames.get(entry.getKey()) + " servi(e) " + entry.getValue() + " fois";
						    if (isCoule.get(entry.getKey()))
						    	res += " et bourré(e)";
						    res += ". ";
					    }
					}
					if (coupsRate > 1)
						res += coupsRate +" bières cassées.";
					if (coupsRate == 1)
						res += coupsRate +" bière cassée.";
					resultats.add(res);
					for (ETable etable : ETable.values()) {
						nbrHits.put(etable, 0);
						isCoule.put(etable, false);
					}
					Fetard_Soiree tmp = fetardCourant;
					fetardCourant = nextFetard;
					nextFetard = tmp;
				}
				Date time = new Date(soiree.getDateFin().getTimeInMillis()-soiree.getDateDebut().getTimeInMillis());
				String duree = soiree.getGagnant().getFetard().getPseudo()+ " est le dernier debout après ";
				if (time.getHours()-1 != 0)
					duree += time.getHours()-1 + " heures ";
				duree += time.getMinutes() + " minutes et ";
				duree += time.getSeconds() + " secondes.";
				resultats.add(duree);
			}
			if (resultats.size()==0) {
				resultats.add("Pas de souvenirs de cette soirée.");
			}
			mapTournees.put(soiree.getId(), resultats);
		}
		request.setAttribute("mapTournees", mapTournees);
		RequestDispatcher rd = getServletContext().getNamedDispatcher("Journaux");
		rd.forward(request, response);
	}

}
