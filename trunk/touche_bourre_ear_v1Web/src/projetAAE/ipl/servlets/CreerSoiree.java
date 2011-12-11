package projetAAE.ipl.servlets;

import java.io.IOException;
import java.io.PushbackInputStream;

import javax.ejb.EJB;
import javax.jms.Session;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import projetAAE.ipl.listener.SessionListener;
import projetAAE.ipl.usecases.GestionSoiree;

/**
 * Servlet implementation class CreerSoiree
 */
@WebServlet("/soiree.html")
public class CreerSoiree extends HttpServlet  implements
javax.servlet.Servlet  {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private GestionSoiree ucc;
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(response.encodeRedirectURL("organiserSoiree.jsp"));
		return;
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		if(session == null || (session!= null && session.getAttribute("pseudo") == null)) {
			response.sendRedirect(response.encodeRedirectURL("index.jsp?timeout=1"));
			return;
		}
		
		HttpSession session2 = request.getSession();
		synchronized (session2) {
			if (session2.getAttribute("nomSoiree") != null) {
				session2.invalidate(); // détruit la session
				session = request.getSession(true);
			}
			session2.setAttribute("nomSoiree", request.getParameter("nomSoiree"));
		}
		//session.setMaxInactiveInterval(5);
		ucc.creerSoiree(request.getParameter("nomSoiree"), (String) session.getAttribute("pseudo"));
		//attribut soiree
		RequestDispatcher rd = getServletContext().getNamedDispatcher("Soiree");
		rd.forward(request, response);
	}
}