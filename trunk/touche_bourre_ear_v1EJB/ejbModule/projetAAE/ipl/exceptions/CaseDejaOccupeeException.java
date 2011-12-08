package projetAAE.ipl.exceptions;

public class CaseDejaOccupeeException extends Exception {

	String message;

	public CaseDejaOccupeeException() {
		super();
	}

	public CaseDejaOccupeeException(String s) {
		super(s);
	}
	
}
