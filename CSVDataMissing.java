
public class CSVDataMissing extends InvalidException{
	private static String message = "\"" + Driver.fileRead.getName() + "\" line " + (Driver.counter+2) + " not converted to LaTEX: missing data.";
			
	public CSVDataMissing() {
		super(message);
	}
	
	public CSVDataMissing(String message) {
		super(message);
	}
}
