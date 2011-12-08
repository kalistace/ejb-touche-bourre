package projetAAE.ipl.exceptions;

public class TableDejaPlaceeException extends Exception {

	String message;

	public TableDejaPlaceeException() {
		super();
	}

	public TableDejaPlaceeException(String s) {
		super(s);
	}

}
