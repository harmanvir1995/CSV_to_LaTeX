
public class InvalidException extends Exception{
	
	public InvalidException() {
		super("Error: Input row cannot be parsed due to missing information");
	}
	
	public InvalidException(String message) {
		super(message);
	}
}
