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


@WebServlet("/connecte.html")
public class Connecte extends javax.servlet.http.HttpServlet implements
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
		
		Soiree soiree = null;
			
			try {
			   
			   soiree = gestionSoiree.commencerPlacement(nomSoiree);
			   
			}
			catch(Exception e){
				System.out.println(e.getMessage());
				request.setAttribute("var",0);
				RequestDispatcher rd = getServletContext().getNamedDispatcher("RepPret");
				rd.forward(request, response);
				return;
			}
		
			if(soiree.getFetardSoiree2().getFetard().getPseudo().equals(sess.getAttribute("pseudo"))){
				request.setAttribute("var",soiree.getFetardSoiree1().getFetard().getPseudo());
			}else {		
				request.setAttribute("var",soiree.getFetardSoiree2().getFetard().getPseudo());
			}

			RequestDispatcher rd = getServletContext().getNamedDispatcher("RepPret");
			rd.forward(request, response);

	}
}