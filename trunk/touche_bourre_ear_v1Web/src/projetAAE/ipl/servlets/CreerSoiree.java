package projetAAE.ipl.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
		ucc.creerSoiree(request.getParameter("nomSoiree"), (String) request.getSession().getAttribute("pseudo"));
		
		//attribut soiree
		RequestDispatcher rd = getServletContext().getNamedDispatcher("Soiree");
		rd.forward(request, response);
	}
}