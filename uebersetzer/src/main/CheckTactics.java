package src.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class CheckTactics {
	Translator translator;
	String input;
	InputStream in; 
	PrintWriter tactics;
	PrintWriter mistakes;
	File output; 
	File protocoll;

	
	
	public CheckTactics(String inputFile, String outputFile, String errorProtocoll){
		this.translator = new Translator();
		//this.in = getClass().getResourceAsStream(inputFile);
		try{
			this.in = new FileInputStream(inputFile);
		} catch (FileNotFoundException e1) {
			throw new IllegalArgumentException("inputFile not found!");
		}

		this.output = new File(outputFile);
		this.protocoll = new File(errorProtocoll);
		try {
			this.tactics = new PrintWriter(output);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("outputfile not found");
		}
		try {
			this.mistakes = new PrintWriter(protocoll);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("no file for errors found");
		}

		
	}
	
	public ArrayList<Command> goOn(){
		translator.setLabelized(true);
		List<Command> parsedTactics = translator.run(in);
		if(parsedTactics instanceof ArrayList)
			return (ArrayList<Command>) parsedTactics;
		else
			return null;
	}
	
	public ArrayList<String> seeErrors(){
		List<String> errors = translator.getErrors();
		if (errors.size() > 0){
			if(errors instanceof ArrayList)
				return (ArrayList<String>) errors;
			else return null;
		}else{
			errors.add("es wurden keine fehler gefunden, was nicht heißt, dass es keine gibt");
			return (ArrayList<String>) errors;
		}
	}
	
	public void printTactics(){
		ArrayList<Command> toPrint = goOn();
		String res = "";
		for(Command line: toPrint)
			res = res + line.toString() + "\n";
		tactics.write(res);
		
	}
	
	public void printErrors(){
		ArrayList<String> toPrint = seeErrors();
		String res = "";
		for(String line: toPrint)
			res = res + line.toString() + "\n";
		mistakes.write(res);
		
	}
}
