package projetAAE.ipl.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import projetAAE.ipl.usecases.GestionSoiree;

/**
 * Servlet implementation class HandleLeavePartie
 */
@WebServlet("/handleLeavePartie.html")
public class HandleLeavePartie extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private GestionSoiree uccGestionSoiree;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		System.out.println("in handleleavepartieservlet");
		String nomSoiree = (String) session.getAttribute("nomSoiree");
		String pseudo = (String) session.getAttribute("pseudo");
		if (nomSoiree != null || pseudo != null) {
			System.out.println("handleleavepartieservlet in if");
			uccGestionSoiree.fetardDeconnecte(nomSoiree, pseudo);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
