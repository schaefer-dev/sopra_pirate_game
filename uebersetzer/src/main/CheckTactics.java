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
	File output; 

	
	
	public CheckTactics(String inputFile, String outputFile){
		this.translator = new Translator();

		//this.in = getClass().getResourceAsStream(inputFile);
		try{
			this.in = new FileInputStream(inputFile);
		} catch (FileNotFoundException e1) {
			throw new IllegalArgumentException("inputFile not found!");
		}

		this.output = new File(outputFile);
		try {
			this.tactics = new PrintWriter(output);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("outputfile not found");
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
	
	public void printTactics(){
		ArrayList<Command> toPrint = goOn();
		String res = "";
		int i=0;
		for(Command line: toPrint){
			if(line == null){
				res = res + translator.getErrors().get(i) + "\n";
				i++;
			}else
				res = res + line.toString() + "\n";	
		}
		tactics.write(res);
		tactics.close();
		
	}
	
	public void printErrors(){
		if(translator.getErrors().size() > 0)
			System.out.println("incorrect tactics.Visit the printed to file for details.");
		else
			System.out.println("no errors found");
	}
}
