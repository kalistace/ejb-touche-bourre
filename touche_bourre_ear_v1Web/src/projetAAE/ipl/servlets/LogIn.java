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
import javax.servlet.http.HttpSessionListener;

import projetAAE.ipl.usecases.GestionFetard;
import projetAAE.ipl.usecasesimpl.GestionFetardImpl;

/**
 * Servlet implementation class LogIn
 */
@WebServlet("/accueil.html")
public class LogIn extends HttpServlet implements
javax.servlet.Servlet  {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private GestionFetard gestionFetard;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(response.encodeRedirectURL("index.jsp"));
		return;
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String pseudo = request.getParameter("pseudo");

		
		HttpSession session = request.getSession(false); //crée une session
		
		synchronized (session) {
			if (session.getAttribute("pseudo") != null) {
				session.removeAttribute("pseudo");
				session.invalidate();
				session = request.getSession(true);
			}
			session.setAttribute("pseudo", pseudo);
			gestionFetard.enregistrer(pseudo);
		}

		
		RequestDispatcher rd = getServletContext().getNamedDispatcher("Accueil");
		rd.forward(request, response);
	}

}
