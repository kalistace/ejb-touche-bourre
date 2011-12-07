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
import javax.servlet.http.HttpSession;

import projetAAE.ipl.domaine.Fetard;
import projetAAE.ipl.domaine.Soiree;

/**
 * Servlet implementation class ListerJournaux
 */
@WebServlet("/listerJournaux.html")
public class ListerJournaux extends HttpServlet {
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
		List<Soiree> journaux = new ArrayList<Soiree>();
		HttpSession session = request.getSession();
		Fetard fetard = new Fetard((String) session.getAttribute("pseudo"));
		journaux.add(new Soiree("Soiree 1", fetard));
		journaux.add(new Soiree("Soiree 2", fetard));
		request.setAttribute("journaux", journaux);
		RequestDispatcher rd = getServletContext().getNamedDispatcher("Journaux");
		rd.forward(request, response);
	}

}
