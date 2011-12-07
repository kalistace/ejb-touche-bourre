package projetAAE.ipl.exceptions;

public class ArgumentInvalideException extends Exception {

	private String message;
	
	public ArgumentInvalideException() {
		super();
	}
	
	public ArgumentInvalideException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
}
