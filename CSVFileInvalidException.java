
public class CSVFileInvalidException extends InvalidException{
	private static String[] attributeArray = Driver.attributeArray;
	private static String message = "\"" + Driver.fileRead.getName() + "\" is invlaid: attribute is missing.\r\n"
			+ getAttributes() + "(and)\nFile is not converted to LATEX.";
	
	private static String getAttributes() {
		String attributes = "";
		
		for(int i=0; i<attributeArray.length; i++) {
			if(attributeArray[i].equals("")) {
				attributes = attributes + "****" + " & ";
			}
			else {
				attributes = attributes + attributeArray[i]  + " & ";
			}
		}
		return attributes;
	}
	
	public CSVFileInvalidException() {
		super(message);
	}
	
	public CSVFileInvalidException(String message) {
		super(message);
	}
	
	public String getMessage() {
		return message;
	}
}
