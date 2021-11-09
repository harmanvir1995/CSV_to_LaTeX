import java.io.*;
import java.util.Scanner;

/**
 * A group of social scientists has conducted studies on the community and data
 * has been recorded using excel sheets. The scientists want to write a book
 * about the findings of the study. The book is written using LaTeX. LaTeX is a
 * type setting system that is widely used among scientific community to produce
 * technical report, scientific research papers and books. The data gathered
 * throughout the study will be published in the book in the form of LaTeX
 * tables. As a software designer, you are hired to design and implement a Java
 * code to convert the data into LaTeX table format.
 *
 * @author HARMANVIR SINGH (40019114)
 * @author SARABPREET SINGH REKHI (40154067)
 */
public class Driver {
	private static String[] filesInDirectory = null;
	public static String[] filesWithGivenExtention = null;
	private static String fileExtentions = null;
	public static File fileRead = null;
	public static File fileToWrite = null;
	public static String[] attributeArray = null;
	public static String[] dataArray = null;
	public static String originalFile = "";
	public static int fileNumber = 0;
	public static int counter = 0;

	public static void main(String[] args) {
		System.out.println("\n------------------------------------------------------------------------\n"
				+ "\t\t Starting the program\n"
				+ "------------------------------------------------------------------------");
		int repeat = 2;
		while (repeat > 0) {
			counter = 0;
			System.out.println("\n***********************************************************************\n"
					+ "\t\t Select the File"
					+ "\n***********************************************************************\n");
			File file = new File(".");
			filesInDirectory = file.list();
			/*------------------------------------------------------------------------
			 * Printing all the .csv files present in the project folder.
			 * -----------------------------------------------------------------------
			 */
			endsWith(".csv");
			Scanner keyIn = new Scanner(System.in);
			System.out.print("Enter the file number you want to edit: ");
			if (keyIn.hasNextInt()) {
				int number = keyIn.nextInt();
				fileNumber = number;
				if (fileNumber > filesWithGivenExtention.length || fileNumber <= 0) {
					System.out.println("Not the correct Number. \nEnding the program...!!");
					keyIn.close();
					System.exit(0);
				}
			} else {
				System.out.println("You did not enter a number.\nEnding the program...!!");
				keyIn.close();
				System.exit(0);
			}
			System.out.println("\nOpening the file \"" + filesWithGivenExtention[fileNumber - 1] + "\"....");
			fileRead = new File(filesWithGivenExtention[fileNumber - 1]);
			String fileName = filesWithGivenExtention[fileNumber - 1].substring(0,
					filesWithGivenExtention[fileNumber - 1].length() - fileExtentions.length());
			originalFile = fileName;
			fileName = fileName + ".tex";
			fileToWrite = new File(fileName);
			String logName = originalFile + "logBook.txt";
			File fileLogBook = new File(logName);
			processFilesForValidation(fileRead, fileToWrite, fileLogBook);
			repeat--;
		}
	}

	/**
	 * This methods prints the number of columns on .tex file required for the
	 * table. Number is counted on the based of "," found in the string.
	 * 
	 * @param firstLine 	String value which contains ",".
	 * @return 				It returns the string with latex formed columns.
	 */
	private static String printNumberOfColumn(String firstLine) {
		String column = "{|l|";
		for (int i = 0; i < firstLine.length(); i++) {
			if (firstLine.charAt(i) == ',') {
				column = column + "l|";
			}
		}
		column = column + "}";
		return column;
	}

	/**
	 * It prints all the files with the given extension. And adds it to the String
	 * array of name filesWithGivenExtention. It ends the program if no file is
	 * found of the given extension.
	 * 
	 * @param fileExtention		 a String value of the extension of file.
	 */
	public static void endsWith(String fileExtention) {
		fileExtentions = fileExtention;
		int counter = 0;
		boolean hasFile = false;
		fileExtention = fileExtention.trim();
		int fileExtentionLength = fileExtention.length();
		System.out.println(
				"Printing all the files with the given file Extention as: " + fileExtention + " In your Project....");
		for (int i = 0; i < filesInDirectory.length; i++) {
			if (filesInDirectory[i].length() > fileExtentionLength) {
				if (filesInDirectory[i]
						.substring(filesInDirectory[i].length() - fileExtentionLength, filesInDirectory[i].length())
						.equalsIgnoreCase(fileExtention)) {
					counter++;
					System.out.println(counter + ") " + filesInDirectory[i]);
					hasFile = true;
				}
			}
		}
		filesWithGivenExtention = new String[counter];
		int counter_2 = 0;
		for (int i = 0; i < filesInDirectory.length; i++) {
			if (filesInDirectory[i].length() > fileExtentionLength) {
				if (filesInDirectory[i]
						.substring(filesInDirectory[i].length() - fileExtentionLength, filesInDirectory[i].length())
						.equalsIgnoreCase(fileExtention)) {
					filesWithGivenExtention[counter_2] = filesInDirectory[i];
					counter_2++;
				}
			}
		}
		if (!hasFile) {
			System.out.println("No File fould with the given file extension " + fileExtention);
			System.out.println("Ending the program...!!");
			System.exit(0);
		}
		System.out.println(
				"\nYou have a total of " + filesWithGivenExtention.length + " files with the given Extension name.");
	}

	/**
	 * This method represents the core engine for processing the input files and creating the output ones.
	 * It reads from the file and writes to the given file. Moreover it generates the log file, which 
	 * stores the information as described by the user.
	 * 
	 * @param fileRead		File from which input is taken.
	 * @param fileToWrite	File to which output is printed. 
	 * @param fileLogBook	File which stores all the information about the errors and empty spaces.
	 */
	public static void processFilesForValidation(File fileRead, File fileToWrite, File fileLogBook) {//Opening the provided file and trying to create new file to write data.
		PrintWriter output = null;
		Scanner input = null;
		PrintWriter outputLog = null;
		try {
			input = new Scanner(fileRead);
		} catch (FileNotFoundException e) {
			System.out.println("Could not open file " + fileRead.getName() + " for reading\n"
					+ "Please check if file exists! Program will terminate after closing all files.");
			System.exit(0);
		}
		try {
			output = new PrintWriter(fileToWrite);
			outputLog = new PrintWriter(fileLogBook);
		} catch (FileNotFoundException e) {
			System.out.println("Could not create file " + fileToWrite.getName() + "\n" + "Terminating the Program....");
			input.close();
			System.exit(0);
		}
		//Reading from the provided files and writing a LaTAX file.
		String firstLine = input.nextLine();
		String[] titleArray = firstLine.split(",", -1);
		String title = titleArray[0];
		outputLog.println("\t\t\t\t Keeping track of Empty data entry and empty attribute.");
		System.out.println("Reading from the file \"" + filesWithGivenExtention[fileNumber - 1] + "\"....");
		System.out.println("Writing to the file \"" + fileToWrite.getName() + "\"....\n");
		output.println("\\begin{table}[]");
		output.print("\t\\begin{tabular}");
		output.println(printNumberOfColumn(firstLine));
		output.println("\t\t\\toprule");
		while (input.hasNextLine()) {
			String line = input.nextLine();
			String[] lineArray = line.split(",", -1);
			String lineToOutput = "";
			// Reading the line which contains the attributes.
			if (counter == 0) {
				attributeArray = lineArray;
				for (int i = 0; i < lineArray.length; i++) {
					// Empty Attributes Condition.
					try {
						if (lineArray[i].trim().equals("")) {
							throw (new CSVFileInvalidException());
						}
					} catch (CSVFileInvalidException e) {
						System.out.println(e.getMessage());
						outputLog.println(e.getMessage());
						output.close();
						input.close();
						if (fileToWrite.delete()) {
							System.out.println("\nDeleted the file: " + fileToWrite.getName());
						} else {
							System.out.println("\nFailed to delete the file.");
						}
						outputLog.close();
						System.exit(0);
					}
					if (i == lineArray.length - 1) {
						lineToOutput = lineToOutput + lineArray[i];
					} else {
						lineToOutput = lineToOutput + lineArray[i] + " & ";
					}
				}
			}
			// Reading the line which contains data.
			else {
				dataArray = lineArray;
				for (int i = 0; i < lineArray.length; i++) {
					// Empty data condition.
					try {
						if (lineArray[i].trim().equals("")) {
							throw (new CSVDataMissing());
						}
					} catch (CSVDataMissing e) {
						System.out.println(e.getMessage());

						outputLog.println("File " + fileRead.getName() + " line " + (counter + 2) + "\n" + getData());
					}
					if (i == lineArray.length - 1) {
						lineToOutput = lineToOutput + lineArray[i];
					} else {
						lineToOutput = lineToOutput + lineArray[i] + " & ";
					}
				}
			}
			if (input.hasNextLine()) {
				output.println("\t\t" + lineToOutput + "\\\\ \\midrule");
			} else {
				output.println("\t\t" + lineToOutput + "\\\\ \\bottomrule");
			}
			counter++;
		}
		output.println("\t\\end{tabular}");
		output.println("\t\\caption{" + title.trim() + "}");
		output.println("\t\\label{" + originalFile + "}");
		output.println("\\end{table}");
		System.out.println("Successfully finished writing to the file " + fileToWrite.getName() + "....");
		outputLog.close();
		output.close();
		input.close();
	}

	/**
	 * This method gets the data from the dataArray which stores the line with missing data. It is used 
	 * to get the line and print in the log book. 
	 * @return 		a String value with after concatenating the values of dataArray.
	 */
	private static String getData() {
		String data = "";
		for (int i = 0; i < dataArray.length; i++) {
			if ((dataArray[i].trim()).equals("")) {
				data = data + "****" + " & ";
			} else {
				data = data + dataArray[i] + " & ";
			}
		}
		return data;
	}
}
