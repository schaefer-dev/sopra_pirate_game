package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
 * 	Maybe, there could be created a class Reports which includes team names, the input tactic, the 
 *  translated tactic and the error list. Before a game starts, the players could be easily informed
 *  about mistakes in there tactics. 
 * 
 * @author Andreas
 *
 */
public class Translator {
	
	Map<String, Integer> labels;
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
	String delabeledTactics;
	
	public Translator(){
		this.errors = new ArrayList<String>();
		this.reports = new HashMap<Character, List<String>>();
		this.row = 0;
		this.toolBox = new TranslatorTools();
		this.labelized = false;
		this.labels = new HashMap<String, Integer>();
	}
	
	/** @Specs: splits the given line in a and saves the first word in currentElements and the rest 
	 * in appendix.
	 * 
	 * @Params: the line, to work with **/
	
	private void makeSplits(String line){
		int index = 1;
		String[] splits = null;
		//if (!line.startsWith("*"))
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
	private void addDelabeled(String toAdd){
		if(toAdd.equalsIgnoreCase("error"))
			delabeledTactics = delabeledTactics + "INVALID: " + currentElement + "\n";
		else
			delabeledTactics = delabeledTactics + "		" + toAdd;						

	}
	private Command translate(String line){

		List<String> conditions = new ArrayList<String>();
		List<Comparison> bools = new ArrayList<Comparison>();
		Comparison comparison = null;
		int type = -1;
		if(line.contains(";")){  //schaut, ob ein Kommentar im Text steht und verkuerzt den String.
			line = line.substring(0, line.indexOf(";"));
		}
		/*if(line.contains("	"))
			System.out.println("contains tab");*/
		line = line.replaceAll("	"," ");
		columns = line.length();
		makeSplits(line.trim());


			switch (CommandWords.valueOf(currentElement.toUpperCase())){
			
			case DROP:
				line = appendix;
				overloadTest();
				addDelabeled("drop\n");
				return new Drop();
			
			case TURN:
				makeSplits(appendix);
				if(currentElement.equalsIgnoreCase("left")){
					overloadTest();
					addDelabeled("turn");
					addDelabeled("left\n");
					return new Turn(true);					
					
				}else if(currentElement.equalsIgnoreCase("right")){
					overloadTest();
					addDelabeled("turn");
					addDelabeled("right\n");
					return new Turn(false);
				
				}else 
					addDelabeled("error");
					errors.add(" l: " + row + " ,p: " + indexOfError() + "Invalid direction.");
					break;
					   
			case GOTO:
				addDelabeled("goto");
				makeSplits(appendix);
				if(evaluateAddress(currentElement) != -1){
					overloadTest();
					addDelabeled(evaluateAddress(currentElement) + "\n");
					return new Goto(evaluateAddress(currentElement));	
				}else{
					addDelabeled("error");
					errors.add(" l: " + row + " ,p: " + indexOfError() + "Invalid address or label.");
					break;
				}
				
			case MARK: 
				addDelabeled("mark");
				makeSplits(appendix);
				if(toolBox.isInteger(currentElement)){
					type = toolBox.toInt(currentElement);
					if(0 <= type && type <= 5){
						addDelabeled(type + "\n");
						overloadTest();
						return new Mark(type);
					}else{
						addDelabeled("error");
						errors.add(" l: " + row + " ,p: " + indexOfError() + "Invalid buoy type.");
						break;
					}
				}
				addDelabeled("error");
				errors.add(" l: " + row + " ,p: " + indexOfError() + "Missing buoy type.");
				break;
	
			case UNMARK:
				addDelabeled("unmark");
				makeSplits(appendix);
				if(toolBox.isInteger(currentElement)){
					type = toolBox.toInt(currentElement);
					if(0 <= type && type <= 5){
						addDelabeled(type + "\n");
						overloadTest();
						return new Unmark(type);
					}else{
						addDelabeled("error");
						errors.add(" l: " + row + " ,p: " + indexOfError() + "Invalid buoy type.");
						break;
					}
				}
				addDelabeled("error");
				errors.add(" l: " + row + " ,p: " + indexOfError() + "Missing buoy type.");
				break;
				
			case SENSE: 
				addDelabeled("sense");
				makeSplits(appendix);
				if(toolBox.isInteger(currentElement)){
					type = toolBox.toInt(currentElement);
					if(0 <= type && type <= 6){
						addDelabeled(type + "\n");
						overloadTest();
						return new Sense(type);
					}else{
						addDelabeled("error");
						errors.add(" l: " + row + " ,p: " + indexOfError() + "Invalid sense direction.");
						break;
					}
				}
				addDelabeled("error");
				errors.add(" l: " + row + " ,p: " + indexOfError() + "Missing sense direction.");
				break;
				
			case MOVE:
				addDelabeled("move");
				makeSplits(appendix);
				if(currentElement.equalsIgnoreCase("else") && appendix != null){
					addDelabeled("else");
					makeSplits(appendix);
					type = evaluateAddress(currentElement);
					if(type != -1){
						addDelabeled(type + "\n");
						overloadTest();
						return new Move(type);	
					}else{
						addDelabeled("error");
						errors.add(" l: " + row + " ,p: " + indexOfError() + "Invalid address or label.");
						break;
					}
				}
				addDelabeled("error");
				errors.add(" l: " + row + " ,p: " + indexOfError() + "Missing else.");
				break;											     
			
			case REPAIR: 
				addDelabeled("repair");
				makeSplits(appendix);
				if(currentElement.equalsIgnoreCase("else") && appendix != null){
					addDelabeled("else");
					makeSplits(appendix);
					type = evaluateAddress(currentElement);
					if(type != -1){
						addDelabeled(type + "\n");
						overloadTest();
						return new Repair(type);					
					}else{
						addDelabeled("error");
						errors.add(" l: " + row + " ,p: " + indexOfError() + "Invalid address or label.");
						break;
					}
				}
				addDelabeled("error");
				errors.add(" l: " + row + " ,p: " + indexOfError() + "Missing else.");
				break;
				
			case PICKUP: 
				addDelabeled("pickup");
				makeSplits(appendix);
				if(toolBox.isInteger(currentElement) || appendix != null){
					type = toolBox.toInt(currentElement);
						if(0 <= type && type <= 6){
							addDelabeled(type + " ");
							makeSplits(appendix);
							if(currentElement.equalsIgnoreCase("else")|| appendix != null){
								addDelabeled("else");
								makeSplits(appendix);
								if(evaluateAddress(currentElement) != -1){
									addDelabeled(evaluateAddress(currentElement) + "\n");
									overloadTest();
									return new Pickup(type,evaluateAddress(currentElement));	
								}else{
									addDelabeled("error");
									errors.add(" l: " + row + " ,p: " + indexOfError() + "Invalid address or label.");
									break;
								}
							}else{
								addDelabeled("error");
								errors.add(" l: " + row + " ,p: " + indexOfError() + "Missing else.");
								break;
							}
						}else{
							addDelabeled("error");
							errors.add(" l: " + row + " ,p: " + indexOfError() + "Invalid direction.");
							break;
						}				
				}
				addDelabeled("error");
				errors.add(" l: " + row + " ,p: " + indexOfError() + "Missing sense direction.");
				break;
							
			case REFRESH:
				addDelabeled("refresh");
				makeSplits(appendix);
				if(toolBox.isInteger(currentElement) || appendix != null){
					type = toolBox.toInt(currentElement);
						if(0 <= type && type <= 6){
							addDelabeled(type + " ");
							makeSplits(appendix);
							if(currentElement.equalsIgnoreCase("else")|| appendix != null){
								addDelabeled("else");
								makeSplits(appendix);
								if(evaluateAddress(currentElement) != -1){
									addDelabeled(evaluateAddress(currentElement) + "\n");
									overloadTest();
									return new Refresh(type,evaluateAddress(currentElement));
								}else
									break;
							}else{
								addDelabeled("error");
								errors.add(" l: " + row + " ,p: " + indexOfError() + "Missing else.");
								break;
							}
						}else{
							addDelabeled("error");
							errors.add(" l: " + row + " ,p: " + indexOfError() + "Invalid direction.");
							break;
						}				
				}
				addDelabeled("error");
				errors.add(" l: " + row + " ,p: " + indexOfError() + "Missing sense direction.");
				break;
			
			case FLIPZERO:
				addDelabeled("flipzero");
				makeSplits(appendix);
				if(toolBox.isInteger(currentElement) || appendix != null){
					type = toolBox.toInt(currentElement);
						if(type > 1){
							addDelabeled(type + "");
							makeSplits(appendix);
							if(currentElement.equalsIgnoreCase("else")|| appendix != null){
								addDelabeled("else");
								makeSplits(appendix);
								if(evaluateAddress(currentElement) != -1){
									addDelabeled(evaluateAddress(currentElement) + "\n");
									overloadTest();
									return new Flipzero(type,evaluateAddress(currentElement));
								}else
									break;
							}else{
								addDelabeled("error");
								errors.add(" l: " + row + " ,p: " + indexOfError() + "Missing else.");
								break;
							}
						}else{
							addDelabeled("error");
							errors.add(" l: " + row + " ,p: " + indexOfError() + "Value must be greater than 1.");
							break;
						}				
				}
				addDelabeled("error");
				errors.add(" l: " + row + " ,p: " + indexOfError() + "Missing value.");
				break;
				
			case IF:
				addDelabeled("if");
				makeSplits(appendix);
				comparison = toolBox.buildComparison(currentElement);
				if(comparison != null && appendix != null){
					addDelabeled(currentElement);
					makeSplits(appendix);
					if(appendix != null && currentElement.equalsIgnoreCase("else")){
						addDelabeled("else");
						makeSplits(appendix);
						type = evaluateAddress(currentElement);
						if(type != -1){
							addDelabeled(type + "\n");
							overloadTest();
								return new If(comparison, type);
						}else
							break;
					}else{
						addDelabeled("error");
						errors.add(" l: " + row + " ,p: " + indexOfError() + "Missing else.");
						break;					
					}
				}else{
					addDelabeled("error");
					errors.add(" l: " + row + " ,p: " + indexOfError() + "Missing condition.");
					break;
				}
			
			case IFALL:
				addDelabeled("ifall");
				conditions.clear();
				bools.clear();
				comparison = null;
				makeSplits(appendix);
				while(!currentElement.equalsIgnoreCase("else") && appendix != null){
					conditions.add(currentElement);
					makeSplits(appendix);
				}
				if(appendix == null){
					delabeledTactics = delabeledTactics + "INVALID: " + currentElement + "\n";						
					errors.add(" l: " + row + " ,p: " + indexOfError() + "Missing else or address.");
					break;
				}
				for(String condition: conditions){
					comparison = toolBox.buildComparison(condition);
					if(comparison == null){
						addDelabeled("error");
						errors.add(" l: " + row + " ,p: " + indexOfError() + "Invalid conditions.");
						break;
					}
					addDelabeled(condition);
					bools.add(comparison);
				}
				if(bools.size() < conditions.size()){
					addDelabeled("error");
					errors.add("l: " + row + "invalid conditions");
					break;
				}else{
					makeSplits(appendix);
					type = evaluateAddress(currentElement);
					if(type != -1){
						addDelabeled("else");
						addDelabeled(type + "\n");
						overloadTest();
						return new IfAll(bools, type);
					}else{
							break;
					}
				}
				
			
			case IFANY:
				addDelabeled("ifany");
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
						addDelabeled("error");
						errors.add(" l: " + row + " ,p: " + indexOfError() + "Invalid conditions.");
						break;
					}
					addDelabeled(condition);
					bools.add(comparison);
				}
				if(bools.size() < conditions.size()){
					addDelabeled("error");
					errors.add("l: " + row + "invalid conditions");
					break;
				}else{
					makeSplits(appendix);
					type = evaluateAddress(currentElement);
					if(type != -1){
						addDelabeled("else");
						addDelabeled(type + "\n");
						overloadTest();
						return new IfAny(bools, type);
					}else{
							break;
					}			
				}
			
			default:
				addDelabeled("error");
				errors.add(" l: " + row + " ,p: " + indexOfError() + "Invalid address or label.");
				break;															
			}
			
		return null;
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
		boolean tooLong = false;
		PrintWriter out = null;
		row = 0;
		input = "";
		List<Command> tactic = new ArrayList<Command>();
		errors = new ArrayList<String>();
		invokes += 1;
					try {
/*************HIER WERDEN DIE LABELS AUSGELESEN*****************************/
				if(labelized){
					tacticsdoc.mark(140*2000);
					delabeledTactics ="";
						while(true){
							String labeledLine = tacticsdoc.readLine();
							if(row >= 2001){
								tooLong = true;
								break;
							}
							if(labeledLine == null)
								break;
							labeledLine = labeledLine.replaceAll("	", " ");
							makeSplits(labeledLine);
							if(currentElement.startsWith("*")){
								labels.put(currentElement.substring(1).toLowerCase(), row);
								row++;
							}else{ 
								row++;
								continue;
							}
						}
/*************DER STREAM WIRD RESETTET*****************************************/

				if(tacticsdoc.markSupported())
					tacticsdoc.reset();
				else throw new IllegalStateException("mark not supported!");
				row = 0;
				}
/******************HIER BEGINNT DAS PARSEN*****************************/
				if(labelized){
					File file = new File("src/test/resources/dereferenzierteTaktik.log");
					out = new PrintWriter(file);
						while(true){
							String currentLine = tacticsdoc.readLine();
							currentLine = currentLine.replaceAll("	", " ");
								if(row >= 2001){
									tooLong = true;
									break;
								}
								if(currentLine == null || currentLine.isEmpty())
									break;
								/*if(row >= 139)
						input = row + " " + input + currentLine + "\n";*/
								if(labelized){
										if(currentLine.startsWith("*")){
											makeSplits(currentLine);
											tactic.add(translate(appendix));
										}else
											tactic.add(translate(currentLine.replaceAll("	", " ")));
								}else 	
									tactic.add(translate(currentLine));	
								reports.put(((char)( 'a' + invokes)), errors);
								row++;
						}
					out.write(delabeledTactics);
					out.close();
				}	
				
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
			throw new IllegalArgumentException("Text: " + errors.get(0) +" laenge " +  errors.size()+"\n"+ input);//(errors.get(0));
		return tactic;
	}
	
	/** @Param: the current word in the line.
	 * 
	 *  @Return: the specified jump address as int or -1 whether the word is out of bounds or not a label.**/
	
	private int evaluateAddress(String currentElement){
		if(toolBox.isInteger(currentElement)){
			    if(0 <= toolBox.toInt(currentElement) && toolBox.toInt(currentElement) <= 1999)
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
	
	public void setLabelized(boolean what)
	{
		labelized = what;
	}
}
