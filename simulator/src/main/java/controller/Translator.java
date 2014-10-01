package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import commands.Drop;
import commands.Flipzero;
import commands.Goto;
import commands.If;
import commands.IfAll;
import commands.IfAny;
import commands.Mark;
import commands.Move;
import commands.Pickup;
import commands.Refresh;
import commands.Repair;
import commands.Sense;
import commands.Turn;
import commands.Unmark;

/**
 * A single instance of this class is invoked by the simulator.
 * 
 * The simulator performs the method run(InputStream tactics) with every single given tactics file 
 * and the method returns a new List<Command> on every invoke. Within the translator, run(InputStream 
 * tactics) invokes sequentially translate(String line) with whole lines of the input stream. 
 * 
 * Translate will use makeSplits() to get the next element of the line until there is invalid input 
 * or a valid command is build. The command is returned to run, which adds it to the list and proceeds
 * to invoke translate with the next line again. Errors will be printed in their own List<String>. The
 * occurrence of errors will not stop the translating process. In the end, there will be a List of valid 
 * commands and a list of errors, but instead of the command list, the error list will be returned as
 * an invalid argument exception. 
 * 
 * The process of translating a single line: 
 * 
 * The first element of the file will be checked, whether it is a valid command word or not. In the
 * last case, translate will print an error and break. In the former case, the translator will perform
 * a specified translating routine for every valid command. It will check the syntax and semantics
 * of every next element as a whole and breaks, if there are errors. If there are no errors but some
 * gibberish in the appendix of the line, the build command will be returned but also an error will be 
 * printed. Hence exists a method overloadTest(). The deeper reason is to save code, because every
 * routine can dispense from one conditional instruction and one additional break statement.
 * At the positions, where an jump address must occure, the method evaluateAddress will be invoke, 
 * to return a valid integer or -1 for invalid input.
 * 
 * @author Andreas
 *
 */
public class Translator {
	
	List<String> errors;
	String currentElement = null;
	String appendix = null;
	int row;
	int columns;
	TranslatorTools toolBox;
	
	public Translator(){
		this.errors = new LinkedList<String>();
		this.row = 0;
		this.toolBox = new TranslatorTools();
	}
	
	/** @Specs: splits the given line in a and saves the first word in currentElements and the rest 
	 * in appendix.
	 * 
	 * @Params: the line, to work with **/
	
	private void makeSplits(String line){
		int index = 1;
		String[] splits = null;
		line.trim();
		splits = line.split(" ");
		String res = "";
		if (splits.length == 1){
			currentElement = splits[0];
			appendix = null;
		}else{
			currentElement = splits[0];
			while(index < splits.length){
				res = res + splits[index] + " ";
				index++;
			}
				appendix = res;
		}
	}
	
	
	/** @See: the class'es main method. A Hybrid of lexer and parser, which evaluates the semantics of
	 *  single strings and builds a valid command or prints an error. Due to the tactics grammar 
	 *  minor complexity and explicit structure, an elaborated lexer and parser were not necessary.
	 *  
	 *  @See: class TranslatorTools.
	 *  
	 *  @See: makeSplits(), overloadTest(), evaluateAddress()
	 *  
	 *  @Param: the inputstreams current line.
	 * 
	 *  @Return: Command.
	 * 
	 *  @Exception: prints errors into List<String> errors. run() will handle the exceptions.**/					

	
	private Command translate(String line){

		List<String> conditions = new LinkedList<String>();
		List<Comparison> bools = new LinkedList<Comparison>();
		Comparison comparison = null;
		int value = -1;
		columns = line.length();
		makeSplits(line);
		try{
			switch (CommandWords.valueOf(currentElement.toUpperCase())){
			
			case DROP:
				line = appendix;
				overloadTest();
				return new Drop();
			
			case TURN:
				makeSplits(appendix);
				if(currentElement.equalsIgnoreCase("left")){
					overloadTest();
					return new Turn(true);					
					
				}else if(currentElement.equalsIgnoreCase("right")){
					overloadTest();
					return new Turn(false);
				
				}else 
					errors.add(" l: " + row + " ,p: " + indexOfError() + "Invalid direction.");
					break;
					   
			case GOTO:
				makeSplits(appendix);
				if(evaluateAddress(currentElement) != -1){
					overloadTest();
					return new Goto(evaluateAddress(currentElement));	
				}else{
					errors.add(" l: " + row + " ,p: " + indexOfError() + "Invalid address or label.");
					break;
				}
				
			case MARK: 
				makeSplits(appendix);
				value = evaluateAddress(currentElement);
					if(0 <= value && value <= 5){
						overloadTest();
						return new Mark(value);
					}else{
						errors.add(" l: " + row + " ,p: " + indexOfError() + "Invalid buoy type.");
						break;
					}
				
	
			case UNMARK:
				makeSplits(appendix);
				value = evaluateAddress(currentElement);
					if(0 <= value && value <= 5){
						overloadTest();
						return new Unmark(value);
					}else{
						errors.add(" l: " + row + " ,p: " + indexOfError() + "Invalid buoy type.");
						break;
					}
				
				
			case SENSE: 
				makeSplits(appendix);
				value = evaluateAddress(currentElement);
					if(0 <= value && value <= 6){
						overloadTest();
						return new Sense(value);
					}else{
						errors.add(" l: " + row + " ,p: " + indexOfError() + "Invalid sense direction.");
						break;
					}
				
			case MOVE:
				makeSplits(appendix);
				if(currentElement.equalsIgnoreCase("else") && appendix != null){
					makeSplits(appendix);
					value = evaluateAddress(currentElement);
					if(value != -1){
						overloadTest();
						return new Move(value);	
					}else{
						errors.add(" l: " + row + " ,p: " + indexOfError() + "Invalid address or label.");
						break;
					}
				}
				errors.add(" l: " + row + " ,p: " + indexOfError() + "Missing else.");
				break;											     
			
			case REPAIR: 
				makeSplits(appendix);
				if(currentElement.equalsIgnoreCase("else") && appendix != null){
					makeSplits(appendix);
					value = evaluateAddress(currentElement);
					if(value != -1){
						overloadTest();
						return new Repair(value);					
					}else{
						errors.add(" l: " + row + " ,p: " + indexOfError() + "Invalid address or label.");
						break;
					}
				}
				errors.add(" l: " + row + " ,p: " + indexOfError() + "Missing else.");
				break;
				
			case PICKUP: 
				makeSplits(appendix);
				value = evaluateAddress(currentElement);
				if(0 <= value && value <= 6){
							makeSplits(appendix);
							if(currentElement.equalsIgnoreCase("else")|| appendix != null){
								makeSplits(appendix);
								if(evaluateAddress(currentElement) != -1){
									overloadTest();
									return new Pickup(value,evaluateAddress(currentElement));	
								}else{
									errors.add(" l: " + row + " ,p: " + indexOfError() + "Invalid address or label.");
									break;
								}
							}else{
								errors.add(" l: " + row + " ,p: " + indexOfError() + "Missing else.");
								break;
							}
						}else{
							errors.add(" l: " + row + " ,p: " + indexOfError() + "Invalid direction.");
							break;
						}				
										
			case REFRESH:
				makeSplits(appendix);
				value = evaluateAddress(currentElement);
						if(0 <= value && value <= 6){
							makeSplits(appendix);
							if(currentElement.equalsIgnoreCase("else")|| appendix != null){
								makeSplits(appendix);
								if(evaluateAddress(currentElement) != -1){
									overloadTest();
									return new Refresh(value,evaluateAddress(currentElement));
								}else
									break;
							}else{
								errors.add(" l: " + row + " ,p: " + indexOfError() + "Missing else.");
								break;
							}
						}else{
							errors.add(" l: " + row + " ,p: " + indexOfError() + "Invalid direction.");
							break;
						}				
	
			
			case FLIPZERO:
				makeSplits(appendix);
				value = evaluateAddress(currentElement);
						if(value > 1){
							makeSplits(appendix);
							if(currentElement.equalsIgnoreCase("else")|| appendix != null){
								makeSplits(appendix);
								if(evaluateAddress(currentElement) != -1){
									overloadTest();
									return new Flipzero(value,evaluateAddress(currentElement));
								}else
									break;
							}else{
								errors.add(" l: " + row + " ,p: " + indexOfError() + "Missing else.");
								break;
							}
						}else{
							errors.add(" l: " + row + " ,p: " + indexOfError() + "Value must be greater than 1.");
							break;
						}				
				
			case IF:
				makeSplits(appendix);
				comparison = toolBox.buildComparison(currentElement);
				if(comparison != null && appendix != null){
					makeSplits(appendix);
					if(appendix != null && currentElement.equalsIgnoreCase("else")){
						makeSplits(appendix);
						value = evaluateAddress(currentElement);
						if(value != -1){
							overloadTest();
								return new If(comparison, value);
						}else
							break;
					}else{
						errors.add(" l: " + row + " ,p: " + indexOfError() + "Missing else.");
						break;					
					}
				}else{
					errors.add(" l: " + row + " ,p: " + indexOfError() + "Missing condition.");
					break;
				}
			
			case IFALL:
				comparison = null;
				makeSplits(appendix);
				while(!currentElement.equalsIgnoreCase("else") && appendix != null){
					conditions.add(currentElement);
					makeSplits(appendix);
				}
				if(appendix == null){
					errors.add(" l: " + row + " ,p: " + indexOfError() + "Missing else or address.");
					break;
				}
				for(String condition: conditions){
					comparison = toolBox.buildComparison(condition);
					if(comparison == null){
						errors.add(" l: " + row + " ,p: " + indexOfError() + "Invalid conditions.");
						break;
					}
					bools.add(comparison);
				}
				if(bools.size() != conditions.size()){
					errors.add("l: " + row + "invalid conditions");
					break;
				}else{
					makeSplits(appendix);
					value = evaluateAddress(currentElement);
					if(value != -1){
						overloadTest();
						return new IfAll(bools, value);
					}else{
							break;
					}
				}
				
			
			case IFANY:
				comparison = null;
				makeSplits(appendix);
				while(!currentElement.equalsIgnoreCase("else") && appendix != null){
					conditions.add(currentElement);
					makeSplits(appendix);
				}
				if(appendix == null){
					errors.add(" l: " + row + " ,p: " + indexOfError() + "Missing else or address.");
					break;
				}
				for(String condition: conditions){
					comparison = toolBox.buildComparison(condition);
					if(comparison == null){
						errors.add(" l: " + row + " ,p: " + indexOfError() + "Invalid conditions.");
						break;
					}
					bools.add(comparison);
				}
				if(bools.size() < conditions.size()){
					errors.add("l: " + row + "invalid conditions");
					break;
				}else{
					makeSplits(appendix);
					value = evaluateAddress(currentElement);
					if(value != -1){
						overloadTest();
						return new IfAny(bools, value);
					}else{
							break;
					}			
				}
			
			default:
				errors.add(" l: " + row + " ,p: " + indexOfError() + "Invalid address or label.");
				break;															
			}
		
			
		return null;
		}catch(Exception e){
			errors.add("l:" + row + "not a valid command: " + currentElement);
			return null;
		}
	}
	
/** @Specs: returns the index of the invalid expressions first char within the initial line.**/
	
	private int indexOfError(){
		if(appendix != null)
			return columns - appendix.length() - currentElement.length();
		return columns - currentElement.length();
	}
	
	/** @see: prints errors, whether there are too many arguments in a single line**/
	
	private void overloadTest() {
		if(appendix != null)
			errors.add("Too many arguments @line " + row);
	}

	/** @See: hatches the given tactics file to method translate line by line. 
	 * 
	 *  @See: translate()
	 * 
	 *  @Param: a tactics file as input stream. 
	 * 
	 *  @Return: a list of commands
	 * 
	 *  @Exception: throws illegalArgumentException, if the input stream is broken or errors.size() is greater than zero.**/
	
	public List<Command> run(InputStream tacticsFile){
		BufferedReader tacticsdoc = new BufferedReader(new InputStreamReader(tacticsFile));
		row = 0;
		List<Command> tactic = new LinkedList<Command>();
		errors = new LinkedList<String>();
			try {
				while(true){
					String currentLine = tacticsdoc.readLine();
					if(row >= 2001){
						break;
					}
					if(currentLine == null)
						break;
					if(currentLine.contains(";")){  //schaut, ob ein Kommentar im Text steht und verkuerzt den String.
						currentLine = currentLine.substring(0, currentLine.indexOf(";"));
					}
					tactic.add(translate(currentLine.replaceAll("	", " ")));	
					row++;
					}					
			}catch (IOException e) {
				throw new IllegalArgumentException("File not found");
			}
		if(row>=2001)
			throw new IllegalArgumentException("Tactics file too long.");
		if (errors.size() > 0)
			throw new IllegalArgumentException("Text: " + errors.get(0) +" laenge " +  errors.size()+"\n");//(errors.get(0));
		return tactic;
	}
	
	/** @Param: the current word in the line.
	 * 
	 *  @Return: the specified jump address as int or -1 whether the word is out of bounds or not a label.**/
	
	private int evaluateAddress(String currentElement){
		if(toolBox.isInteger(currentElement)){
			    if(0 <= toolBox.toInt(currentElement) && toolBox.toInt(currentElement) <= 1999)
			    	return toolBox.toInt(currentElement);
			    else
			    	return -1;
		}else{
			   errors.add("l: " + row + " ,p: " + indexOfError() + "Invalid jump address.");
			   return -1;
			   }
	}
	
	
	
}
