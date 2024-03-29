package projetAAE.ipl.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import projetAAE.ipl.domaine.Biere;
import projetAAE.ipl.domaine.ETable;
import projetAAE.ipl.domaine.Fetard_Soiree;
import projetAAE.ipl.domaine.Soiree;
import projetAAE.ipl.domaine.Soiree.Etat;
import projetAAE.ipl.domaine.Tournee;
import projetAAE.ipl.usecases.GestionSoiree;
import projetAAE.ipl.valueObject.XY;


@WebServlet("/casesTouchees.html")
public class CasesTouchees extends javax.servlet.http.HttpServlet implements
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
		} catch (Exception e) {}
		
		
		
		Fetard_Soiree fs = soiree.getAdversaire(soiree.getFetard_Soiree(pseudo));
		System.out.println("CASATOUCHE: ADVERSAIRE: "+fs.getFetard().getPseudo());
		System.out.println("CASATOUCHE: ME: "+(String)sess.getAttribute("pseudo"));
		List<Tournee> mesTournees = fs.getMesTournees();
		if(mesTournees.size()==0){
			
			request.setAttribute("var",0);
			RequestDispatcher rd = getServletContext().getNamedDispatcher("RepPret");
			rd.forward(request, response);
			return;
		}
		Tournee tournee = mesTournees.get(mesTournees.size()-1);
		List<Biere> listeBiere = tournee.getBieres();

		String rep =  toStringList(listeBiere);
		request.setAttribute("var",rep);
		RequestDispatcher rd = getServletContext().getNamedDispatcher("RepPret");
		rd.forward(request, response);
	}
	
	private String toStringList(List<Biere> liste){
		String s = "";
		for(Biere biere : liste){
			if(biere!=null) {
				s+=""+biere.getX()+""+biere.getY()+",";
			}
		}
		if(s.length()==0)
			return s;
		return s.substring(0,s.length()-1);
	}
}