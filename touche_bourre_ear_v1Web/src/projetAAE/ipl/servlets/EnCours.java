package projetAAE.ipl.servlets;

import java.io.IOException;
import java.util.Calendar;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import projetAAE.ipl.domaine.Soiree;
import projetAAE.ipl.usecases.GestionSoiree;


@WebServlet("/encours.html")
public class EnCours extends javax.servlet.http.HttpServlet implements
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
		
		HttpSession sess = request.getSession();
		String nomSoiree = (String)sess.getAttribute("nomSoiree");
		String pseudo = (String)sess.getAttribute("pseudo");
		Soiree soiree = null;
		
		try {
			 soiree = gestionSoiree.commencerSoiree(nomSoiree);
		} catch (Exception e) {
			try {
				soiree = gestionSoiree.finirSoiree(nomSoiree);
			} catch (Exception e1) {}
			
				request.setAttribute("var",soiree.getGagnant().getFetard().getPseudo());
				RequestDispatcher rd = getServletContext().getNamedDispatcher("RepPret");
				rd.forward(request, response);
				return;
			
		}

		
		int rp = 0;
		String joueur = soiree.getFetard_Soiree_Courant().getFetard().getPseudo();
		if(joueur.equals(pseudo))
			rp = soiree.getFetard_Soiree_Courant().getNbBieresParTournee();
		
		request.setAttribute("var",rp);
		RequestDispatcher rd = getServletContext().getNamedDispatcher("RepPret");
		rd.forward(request, response);
	}
}