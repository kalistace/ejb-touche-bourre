package projetAAE.ipl.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import projetAAE.ipl.domaine.ETable;
import projetAAE.ipl.domaine.Fetard;
import projetAAE.ipl.domaine.Soiree;
import projetAAE.ipl.exceptions.ArgumentInvalideException;
import projetAAE.ipl.usecases.GestionSoiree;
import projetAAE.ipl.valueObject.XY;


@WebServlet("/deconnecte.html")
public class Deconnecte extends javax.servlet.http.HttpServlet implements
		javax.servlet.Servlet {
	static final long serialVersionUID = 1L;
	
	@EJB
	private GestionSoiree gestionSoiree;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if(session == null || (session!= null && session.getAttribute("pseudo") == null)) {
			response.sendRedirect(response.encodeRedirectURL("index.jsp?timeout=1"));
			return;
		}
		String nomSoiree = (String)session.getAttribute("nomSoiree");
		
		Soiree soiree = null;
		
		try {
			soiree = gestionSoiree.commencerPlacement(nomSoiree);
		} catch (Exception e) {
			try {
				soiree = gestionSoiree.commencerSoiree(nomSoiree);
			} catch (Exception e1) {
				request.setAttribute("var",2);//ok
				RequestDispatcher rd = getServletContext().getNamedDispatcher("RepPret");
				rd.forward(request, response);
				return;
			}
			if(soiree.getNbrFetardConnecte()<2){	
				request.setAttribute("var",soiree.getGagnant());//encours
				RequestDispatcher rd = getServletContext().getNamedDispatcher("RepPret");
				rd.forward(request, response);
				return;
			}
			
		}
	
		if(soiree.getNbrFetardConnecte()<2){	
			request.setAttribute("var",1); //en placement
			RequestDispatcher rd = getServletContext().getNamedDispatcher("RepPret");
			rd.forward(request, response);
			return;
		}
		request.setAttribute("var",2);//ok
		RequestDispatcher rd = getServletContext().getNamedDispatcher("RepPret");
		rd.forward(request, response);

	}
}