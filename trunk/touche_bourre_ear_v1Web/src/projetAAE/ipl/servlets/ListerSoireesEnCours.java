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

import projetAAE.ipl.domaine.Soiree;
import projetAAE.ipl.usecases.GestionSoiree;
import projetAAE.ipl.usecasesimpl.GestionSoireeImpl;

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
		response.sendRedirect(response.encodeRedirectURL("accueil.jsp"));
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Soiree> soireesEnCours = ucc.listerPartiesEnAttenteDePartenaire();
		request.setAttribute("soireesEnCours", soireesEnCours);
		RequestDispatcher rd = getServletContext().getNamedDispatcher("Rejoindre");
		rd.forward(request, response);
	}

}
