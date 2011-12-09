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
		
		if(tables.equals("")){
			
			try {
			   
			   soiree = gestionSoiree.commencerPlacement(nomSoiree);
			   
			}
			catch(Exception e){
				System.out.println(e.getMessage());
				request.setAttribute("pret",0);
				RequestDispatcher rd = getServletContext().getNamedDispatcher("RepPret");
				rd.forward(request, response);
				return;
			}
		}
		
		
		//get nom adver;
		System.out.println("PSEEEEEUUUDDOOOOO"+ (String)request.getSession().getAttribute("pseudo"));
		
		HashMap<ETable, List<XY>> mtables = new HashMap<ETable,List<XY>>();
		mtables.put(ETable.TableDeCouple, new ArrayList<XY>());
		mtables.put(ETable.TableDeFilles,  new ArrayList<XY>());
		mtables.put(ETable.TableDeGarcons,  new ArrayList<XY>());
		mtables.put(ETable.TableDePotes,  new ArrayList<XY>());
		mtables.put(ETable.Comptoir,  new ArrayList<XY>());
		
		
		ETable currentT = ETable.TableDeCouple;
		while(tables.length()>0){
			
			if(tables.charAt(0)=='T'){
				switch (tables.charAt(1)){
					case 1 : currentT = ETable.TableDeCouple;break;
					case 2 : currentT = ETable.TableDeFilles;break;
					case 3 : currentT = ETable.TableDeGarcons;break;
					case 4 : currentT = ETable.TableDePotes;break;
					case 5 : currentT = ETable.Comptoir;break;
				}
			}else{
				
				XY c = null;
				try {
					c = new XY((int)tables.charAt(0),(int)tables.charAt(1));
				} catch (ArgumentInvalideException e) {}
				List<XY> cr = new ArrayList<XY>(mtables.get(currentT));
				cr.add(c);
				mtables.put(currentT,cr);
			}
			tables = String.copyValueOf(tables.toCharArray(), 2, tables.toCharArray().length-2).toString();	
		}
		
			soiree = gestionSoiree.fetardPret(nomSoiree, (String)request.getSession().getAttribute("pseudo"), mtables);
			
			
			
			if(soiree.getFetardSoiree2().getFetard().getPseudo().equals(sess.getAttribute("pseudo"))){
				request.setAttribute("pret",soiree.getFetardSoiree1().getFetard().getPseudo());
			}else {		
				request.setAttribute("pret",soiree.getFetardSoiree2().getFetard().getPseudo());
			}
					
			RequestDispatcher rd = getServletContext().getNamedDispatcher("RepPret");
			rd.forward(request, response);

	}
}