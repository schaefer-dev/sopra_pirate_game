package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
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
	
	public LinkedList<Command> goOn(){
		translator.setLabelized(true);
		List<Command> parsedTactics = translator.run(in);
		if(parsedTactics instanceof LinkedList)
			return (LinkedList<Command>) parsedTactics;
		else
			return null;
	}
	
	public void printTactics(){
		LinkedList<Command> toPrint = goOn();
		String res = "";		
		for(Command line: toPrint){
			if(line!=null)
				res = res + line.toString() + "\n";	
			else
				break;
		}
		tactics.write(res);
		tactics.close();
		
	}
	
	public void printErrors(){
		if(translator.getErrors().size() > 0)
			System.out.println(translator.getErrors().size() + " Errors occurred. Visit the printed to file for further informations.");
		else
			System.out.println("no errors found, except those displayed on the console.");
	}
}
