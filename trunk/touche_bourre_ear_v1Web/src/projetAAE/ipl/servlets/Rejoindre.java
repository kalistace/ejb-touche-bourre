package projetAAE.ipl.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import projetAAE.ipl.usecases.GestionSoiree;

/**
 * Servlet implementation class Journal
 */
@WebServlet("/rejoindre.html")
public class Rejoindre extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private GestionSoiree gestionSoiree;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String soiree = (String)request.getParameter("nomSoiree");
		HttpSession session = request.getSession(false);
		
		if(session == null || (session!= null && session.getAttribute("pseudo") == null)) {
			response.sendRedirect(response.encodeRedirectURL("index.jsp?timeout=1"));
			return;
		}
		
		if (gestionSoiree.rejoindreSoiree(soiree, (String)session.getAttribute("pseudo")) == null) {
			response.sendRedirect(response.encodeRedirectURL("listerSoirees.html?fail"));
			return;
		}
		
		
		session.setAttribute("nomSoiree", soiree);
		
		RequestDispatcher rd = getServletContext().getNamedDispatcher("Soiree");
		rd.forward(request, response);
	}

}
