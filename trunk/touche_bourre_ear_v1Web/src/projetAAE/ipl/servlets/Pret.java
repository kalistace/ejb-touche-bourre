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
import projetAAE.ipl.domaine.Soiree;
import projetAAE.ipl.domaine.Soiree.Etat;
import projetAAE.ipl.usecases.GestionSoiree;
import projetAAE.ipl.valueObject.XY;


@WebServlet("/pret.html")
public class Pret extends javax.servlet.http.HttpServlet implements
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
		String tables = request.getParameter("tables");
		HttpSession sess = request.getSession();
		String nomSoiree = (String)sess.getAttribute("nomSoiree");
		
		Soiree soiree = null;

		
		HashMap<ETable, List<XY>> mtables = new HashMap<ETable,List<XY>>();
		mtables.put(ETable.TableDeCouple, new ArrayList<XY>());
		mtables.put(ETable.TableDeFilles,  new ArrayList<XY>());
		mtables.put(ETable.TableDeGarcons,  new ArrayList<XY>());
		mtables.put(ETable.TableDePotes,  new ArrayList<XY>());
		mtables.put(ETable.Comptoir,  new ArrayList<XY>());
		
		
		ETable currentT = ETable.TableDeCouple;
		XY c = new XY(-1,-1);
		
		String[] ntables = tables.split(",");
		for ( String t : ntables){			
			if(t.charAt(0)=='T'){
				switch (t.charAt(1)){
					case '1' : currentT = ETable.TableDeCouple;break;
					case '2' : currentT = ETable.TableDeFilles;break;
					case '3' : currentT = ETable.TableDeGarcons;break;
					case '4' : currentT = ETable.TableDePotes;break;
					case '5' : currentT = ETable.Comptoir;break;
				}
				
			}else{
				if(c.getX()==-1){
					c.setX(Integer.parseInt(t));
					continue;
				}
				else { 
					List<XY> cr = mtables.get(currentT);
					c.setY(Integer.parseInt(t));
					cr.add(c);
					c = new XY(-1,-1);
					
					mtables.put(currentT,cr);
				}
			}
		}
		
		
		if(tables.equals("-1")){
		try {
			
			soiree = gestionSoiree.commencerSoiree(nomSoiree);
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
				request.setAttribute("var",2);//timer
				RequestDispatcher rd = getServletContext().getNamedDispatcher("RepPret");
				rd.forward(request, response);
				return;
			}
			request.setAttribute("var",1);
			RequestDispatcher rd = getServletContext().getNamedDispatcher("RepPret");
			rd.forward(request, response);
			return;
		}
		
			try {
			soiree = gestionSoiree.fetardPret(nomSoiree, (String)request.getSession().getAttribute("pseudo"), mtables);
			}
			catch(Exception e) {
				request.setAttribute("var",0);
				RequestDispatcher rd = getServletContext().getNamedDispatcher("RepPret");
				rd.forward(request, response);
				return;
			}
			
			request.setAttribute("var",0);
			RequestDispatcher rd = getServletContext().getNamedDispatcher("RepPret");
			rd.forward(request, response);
			

	}
}