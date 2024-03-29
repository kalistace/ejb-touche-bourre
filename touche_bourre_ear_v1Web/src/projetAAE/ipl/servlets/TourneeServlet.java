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


@WebServlet("/tournee.html")
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
		System.out.println("pseudo LANCER: "+pseudo);
		Soiree soiree = null;
		List<XY> listeCoord = new ArrayList<XY>();
		
		String[] ncoords = coords.split(",");

		for(String xy : ncoords){
			XY c = new XY(Integer.parseInt(String.valueOf(xy.charAt(0))), Integer.parseInt(String.valueOf(xy.charAt(1))));
			listeCoord.add(c);
		}

		
		try{
		soiree = gestionSoiree.lancerUneTournee(nomSoiree, pseudo, listeCoord);
		}catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		
		
		Fetard_Soiree fs = soiree.getFetard_Soiree(pseudo);
		List<Tournee> mesTournees = fs.getMesTournees();
		
		Tournee tournee = mesTournees.get(mesTournees.size()-1);
		List<Biere> listeBiere = tournee.getBieres();
		List<ETable> tablesTouche = new ArrayList<ETable>();
		
		for(Biere b : listeBiere){
			tablesTouche.add(b.getTableTouchee());
		}
		String rep = toStringList(tablesTouche);
		request.setAttribute("var",rep);
		RequestDispatcher rd = getServletContext().getNamedDispatcher("RepPret");
		rd.forward(request, response);
	}
	
	private String toStringList(List<ETable> liste){
		String s = "";
		for(ETable table : liste){
			if(table!=null) {
				s+=table.toString()+",";
			}
		}
		if(s.length()==0)
			return s;
		return s.substring(0,s.length()-1);
	}
}