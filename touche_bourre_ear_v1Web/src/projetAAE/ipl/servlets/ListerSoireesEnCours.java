package projetAAE.ipl.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import projetAAE.ipl.domaine.Fetard;
import projetAAE.ipl.domaine.Soiree;

/**
 * Servlet implementation class ListerSoireesEnCours
 */
@WebServlet("/listerSoirees.html")
public class ListerSoireesEnCours extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Soiree> soireesEnCours = new ArrayList<Soiree>();
		Soiree soiree1, soiree2, soiree3;
		Fetard fet2 = new Fetard("rantan");
		soiree1 = new Soiree("Soiree 1", new Fetard("zoubi"));
		soiree2 = new Soiree("Soiree 2", new Fetard("bibi"));
		soiree3 = new Soiree("Soiree 3", new Fetard("bibi"));
		soiree3.ajouterFetard(fet2, soiree3);
		soireesEnCours.add(soiree1);
		soireesEnCours.add(soiree2);
		soireesEnCours.add(soiree3);
		request.setAttribute("soireesEnCours", soireesEnCours);
		RequestDispatcher rd = getServletContext().getNamedDispatcher("Rejoindre");
		rd.forward(request, response);
	}

}
