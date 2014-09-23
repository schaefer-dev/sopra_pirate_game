package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

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

public class Translator {
	
	Map<String, Integer> labels = new HashMap<String, Integer>();
	Map<Character, List<String>> reports;
	List<String> errors;
	boolean labelized;
	String currentElement = null;
	String appendix = null;
	int row;
	int columns;
	TranslatorTools toolBox;
	int invokes = -1;
	String input = ""; 
	
	public Translator(){
		this.errors = new ArrayList<String>();
		this.reports = new HashMap<Character, List<String>>();
		this.row = 0;
		this.toolBox = new TranslatorTools();
		this.labelized = false;
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

		List<String> conditions = new ArrayList<String>();
		List<Comparison> bools = new ArrayList<Comparison>();
		Comparison comparison = null;
		int type = -1;
		boolean checkBools = true;
		if(line.contains(";")){  //schaut, ob ein Kommentar im Text steht und verkuerzt den String.
			line = line.substring(0, line.indexOf(";"));
		}
		/*if(line.contains("	"))
			System.out.println("contains tab");*/
		line = line.replaceAll("	"," ");
		columns = line.length();
		makeSplits(line);

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
				if(toolBox.isInteger(currentElement)){
					type = toolBox.toInt(currentElement);
					if(0 <= type && type <= 5){
						overloadTest();
						return new Mark(type);
					}else{
						errors.add(" l: " + row + " ,p: " + indexOfError() + "Invalid buoy type.");
						break;
					}
				}
				errors.add(" l: " + row + " ,p: " + indexOfError() + "Missing buoy type.");
				break;
	
			case UNMARK:
				makeSplits(appendix);
				if(toolBox.isInteger(currentElement)){
					type = toolBox.toInt(currentElement);
					if(0 <= type && type <= 5){
						overloadTest();
						return new Unmark(type);
					}else{
						errors.add(" l: " + row + " ,p: " + indexOfError() + "Invalid buoy type.");
						break;
					}
				}
				errors.add(" l: " + row + " ,p: " + indexOfError() + "Missing buoy type.");
				break;
				
			case SENSE: 
				makeSplits(appendix);
				if(toolBox.isInteger(currentElement)){
					type = toolBox.toInt(currentElement);
					if(0 <= type && type <= 6){
						overloadTest();
						return new Sense(type);
					}else{
						errors.add(" l: " + row + " ,p: " + indexOfError() + "Invalid sense direction.");
						break;
					}
				}
				errors.add(" l: " + row + " ,p: " + indexOfError() + "Missing sense direction.");
				break;
				
			case MOVE:
				makeSplits(appendix);
				if(currentElement.equalsIgnoreCase("else") && appendix != null){
					makeSplits(appendix);
					type = evaluateAddress(currentElement);
					if(type != -1){
						overloadTest();
						return new Move(type);	
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
					type = evaluateAddress(currentElement);
					if(type != -1){
						overloadTest();
						return new Repair(type);					
					}else{
						errors.add(" l: " + row + " ,p: " + indexOfError() + "Invalid address or label.");
						break;
					}
				}
				errors.add(" l: " + row + " ,p: " + indexOfError() + "Missing else.");
				break;
				
			case PICKUP: 
				makeSplits(appendix);
				if(toolBox.isInteger(currentElement) || appendix != null){
					type = toolBox.toInt(currentElement);
						if(0 <= type && type <= 6){
							makeSplits(appendix);
							if(currentElement.equalsIgnoreCase("else")|| appendix != null){
								makeSplits(appendix);
								if(evaluateAddress(currentElement) != -1){
									overloadTest();
									return new Pickup(type,evaluateAddress(currentElement));	
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
				}
				errors.add(" l: " + row + " ,p: " + indexOfError() + "Missing sense direction.");
				break;
							
			case REFRESH:
				makeSplits(appendix);
				if(toolBox.isInteger(currentElement) || appendix != null){
					type = toolBox.toInt(currentElement);
						if(0 <= type && type <= 5){
							makeSplits(appendix);
							if(currentElement.equalsIgnoreCase("else")|| appendix != null){
								makeSplits(appendix);
								if(evaluateAddress(currentElement) != -1){
									overloadTest();
									return new Refresh(type,evaluateAddress(currentElement));
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
				}
				errors.add(" l: " + row + " ,p: " + indexOfError() + "Missing sense direction.");
				break;
			
			case FLIPZERO:
				makeSplits(appendix);
				if(toolBox.isInteger(currentElement) || appendix != null){
					type = toolBox.toInt(currentElement);
						if(type > 1){
							makeSplits(appendix);
							if(currentElement.equalsIgnoreCase("else")|| appendix != null){
								makeSplits(appendix);
								if(evaluateAddress(currentElement) != -1){
									overloadTest();
									return new Flipzero(type,evaluateAddress(currentElement));
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
				}
				errors.add(" l: " + row + " ,p: " + indexOfError() + "Missing value.");
				break;
				
			case IF:
				makeSplits(appendix);
				comparison = toolBox.buildComparison(currentElement);
				if(comparison != null && appendix != null){
					makeSplits(appendix);
					if(appendix != null && currentElement.equalsIgnoreCase("else")){
						makeSplits(appendix);
						type = evaluateAddress(currentElement);
						if(type != -1){
							overloadTest();
								return new If(comparison, type);
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
				conditions.clear();
				bools.clear();
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
						checkBools = false;
						break;
					}
					bools.add(comparison);
				}
				if(checkBools = false)
					break;
				makeSplits(appendix);
				type = evaluateAddress(currentElement);
				if(type != -1){
					overloadTest();
					return new IfAll(bools, type);
				}else{
						break;
				}
				
			
			case IFANY:
				conditions.clear();
				bools.clear();
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
						checkBools = false;
						break;
					}
					bools.add(comparison);
				}
				if(checkBools = false)
					break;
				makeSplits(appendix);
				type = evaluateAddress(currentElement);
				if(type != -1){
					overloadTest();
					return new IfAny(bools, type);
				}else{
						break;
				}
			
			default:
				errors.add(" l: " + row + " ,p: " + indexOfError() + "Invalid address or label.");
				break;															
			}
			
		return null;
	}
	
/** @Specs: returns the index of the invalid expressions first char within the initial line.**/
	
	public int indexOfError(){
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
	
	public List<Command> run(InputStream tacticFile){
		BufferedReader tacticdoc = new BufferedReader(new InputStreamReader(tacticFile));
		boolean tooLong = false;
		row = 0;
		input = "";
		List<Command> tactic = new ArrayList<Command>();
		errors = new ArrayList<String>();
		invokes += 1;
					try {
				if (labelized){
					while(tacticdoc.readLine() != null){
						makeSplits(tacticdoc.readLine());
						if(currentElement.startsWith("*"))
							labels.put(currentElement.substring(1).toLowerCase(), row);
						else continue;
					row++;
					}
				row = 0;
				}
						
				while(true){
					String currentLine = tacticdoc.readLine();
					if(row >= 2001){
						tooLong = true;
						break;
					}
					if(currentLine == null)
						break;
					if(row >= 139)
						input = row + " " + input + currentLine + "\n";
					tactic.add(translate(currentLine));	
					reports.put(((char)( 'a' + invokes)), errors);
					row++;
				}
				tacticdoc.close();
				/*if (errors.size() > 0){
					int error = 0;
					while(error < errors.size()){
						System.out.println("Error:" + (error+1) + errors.get(error));
						error++;
					}
				}*/
			} catch (IOException e) {
				throw new IllegalArgumentException("File not found");
			}
		if(tooLong)
			throw new IllegalArgumentException("Tactics file too long.");
		if (errors.size() > 0)
			throw new IllegalArgumentException("Text: " +errors.get(0) +" laenge " +  errors.size()+"\n"+ input);//(errors.get(0));
		return tactic;
	}
	
	/** @Param: the current word in the line.
	 * 
	 *  @Return: the specified jump address as int or -1 whether the word is out of bounds or not a label.**/
	
	private int evaluateAddress(String currentElement){
		if(toolBox.isInteger(currentElement)){
			    if(0 <= toolBox.toInt(currentElement))
			    		return toolBox.toInt(currentElement);
		}if(labelized){
				if(labels.containsKey(currentElement.toLowerCase()))
				    	return (int) labels.get(currentElement.toLowerCase());
				else{
					   errors.add("l: " + row + " ,p: " + indexOfError() + "Invalid label.");
					   return -1;
				   }
		}else{
			   errors.add("l: " + row + " ,p: " + indexOfError() + "Invalid jump address.");
			   return -1;
			   }
	}
	
}
