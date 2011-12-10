package projetAAE.ipl.listener;

import java.io.IOException;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import projetAAE.ipl.usecases.GestionSoiree;

public class SessionListener implements HttpSessionListener {

	private static final int TIMEOUTGENERAL = 120;
	
	private static final int TIMEOUTPARTIE = 5;
	
	@EJB
	private GestionSoiree gestionSoiree;
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		session.setMaxInactiveInterval(TIMEOUTGENERAL);//2 heures
		System.out.println("debut session...");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		System.out.println("fin de session...");
		HttpSession session = se.getSession();
		if(session.getMaxInactiveInterval()==TIMEOUTPARTIE){
			
			gestionSoiree.fetardDeconnecte((String)session.getAttribute("nomSoiree"), (String)session.getAttribute("pseudo"));
		}
	}

}
