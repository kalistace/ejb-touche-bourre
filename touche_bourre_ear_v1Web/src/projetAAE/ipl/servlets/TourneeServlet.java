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
import projetAAE.ipl.domaine.Fetard;
import projetAAE.ipl.domaine.Soiree;
import projetAAE.ipl.domaine.Tournee;
import projetAAE.ipl.usecases.GestionSoiree;
import projetAAE.ipl.valueObject.XY;


@WebServlet("/encours.html")
public class TourneeServlet extends javax.servlet.http.HttpServlet implements
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
		String coords = request.getParameter("coord");
		String nomSoiree = (String)sess.getAttribute("nomSoiree");
		String pseudo = (String)sess.getAttribute("pseudo");
		Soiree soiree = null;
		
		List<XY> listeCoord = new ArrayList<XY>();
		
		String[] ncoords = coords.split(",");
		for(String xy : ncoords){
			XY c = new XY(xy.charAt(0), xy.charAt(1));
			listeCoord.add(c);
		}
		
		soiree = gestionSoiree.lancerUneTournee(nomSoiree, pseudo, listeCoord);
		
		List<Tournee> mesTournees = soiree.getFetard_Soiree(new Fetard(pseudo)).getMesTournees();
		
		Tournee tournee = mesTournees.get(mesTournees.size());
		List<Biere> listeBiere = tournee.getBieres();
		for(Biere b : listeBiere){
			//b.getTableTouchee()
		}
		
		request.setAttribute("var","");
		RequestDispatcher rd = getServletContext().getNamedDispatcher("RepPret");
		rd.forward(request, response);
	}
}