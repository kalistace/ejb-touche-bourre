package projetAAE.ipl.servlets;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import projetAAE.ipl.domaine.Soiree;
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null || (session!= null && session.getAttribute("pseudo") == null)) {
			response.sendRedirect(response.encodeRedirectURL("index.jsp?timeout=1"));
			return;
		}
		List<Soiree> journaux = ucc.listerSoireesFinies((String) session.getAttribute("pseudo"));
		request.setAttribute("monPseudo", session.getAttribute("pseudo"));
		request.setAttribute("journaux", journaux);
		RequestDispatcher rd = getServletContext().getNamedDispatcher("Journaux");
		rd.forward(request, response);
	}

}
