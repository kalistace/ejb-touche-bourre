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
 * Servlet implementation class ListerSoireesEnCours
 */
@WebServlet("/listerSoirees.html")
public class ListerSoireesEnCours extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    @EJB   
	private GestionSoiree ucc;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("fail", true);
		doPost(request, response);
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
		List<Soiree> soireesEnCours = ucc.listerPartiesEnAttenteDePartenaire();
		request.setAttribute("soireesEnCours", soireesEnCours);
		RequestDispatcher rd = getServletContext().getNamedDispatcher("Rejoindre");
		rd.forward(request, response);
	}

}
