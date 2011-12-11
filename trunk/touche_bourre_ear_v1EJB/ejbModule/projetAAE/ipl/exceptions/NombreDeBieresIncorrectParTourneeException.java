package projetAAE.ipl.exceptions;

public class NombreDeBieresIncorrectParTourneeException extends Exception {

	String message;

	public NombreDeBieresIncorrectParTourneeException() {
		super();
	}

	public NombreDeBieresIncorrectParTourneeException(String s) {
		super(s);
	}
	
}
