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
import commands.Goto;
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
	List<Command> tactic;
	List<String> errors;
	boolean labelized;
	int row;
	int columns;
	TranslatorTools toolBox;
	int column = 0;//the column, that marks the index of the first whitespace right after 
    //the occurrence of a coherent string
	int mussNochGeklaertWerden; //ist ein dummy.
	
	public Translator(){
		this.tactic = new ArrayList<Command>();
		this.errors = new ArrayList<String>();
		this.row = 0;
		this.toolBox = new TranslatorTools();
		this.labelized = false;
	}
	
	/* @Specs: the classes main method. A Hybrid of lexer and parser, which evaluates the semantics of
	 *  single coherent strings and builds a valid command or prints an error. Due to the tactics grammar 
	 *  minor complexity and explicit structure, an elaborated lexer and parser were not necessary.
	 *  
	 * @Param: the inputstreams current line.
	 * 
	 * @Return: Command.
	 * 
	 * @Error: prints error*/
	
	private Command translate(String line){
		String currentElement = null;
		List<Comparison> bools = new ArrayList<Comparison>();
		int currentColumn = 0;//the column, that marks the begin of a coherent string and that would be used 
								//in a optional error statement.
		int type = -1;
		line = line.trim();
		if(line.contains(";"))  //schaut, ob ein Kommentar im Text steht und verkuerzt den String.
			line = line.substring(0, line.indexOf(";"));
	
		currentElement = nextElement(line);
		line = line.substring(currentElement.length());

			switch (CommandWords.valueOf(currentElement.toUpperCase())){
			
			case DROP: 
				System.out.println("dropline: " + line);
				if(!line.isEmpty())
						break;
				return new Drop();
			
			case TURN: 
				currentElement = nextElement(line);
				if(currentElement == "left")
					if(!line.substring(5).isEmpty())
						return new Turn(true);
					else break;						
					
				if(currentElement == "right")
					if(!line.substring(6).isEmpty())
						return new Turn(true);
					else break;
				errors.add("Not a valid direction @line " + row + " @position "
																	+ toolBox.indexOfError(columns, line, currentElement));
				break;
					   
			case GOTO: 
				currentElement = nextElement(line);
				if(evaluateAddress(currentElement) != -1)
					if(!toolBox.getAppendix(line).isEmpty())
						return new Goto(evaluateAddress(currentElement));	
					else break;
				errors.add("Invalid address or label @line " + row + " @position "
																	+ toolBox.indexOfError(columns, line, currentElement));
				break;
				
			case MARK: 
				currentElement = nextElement(line);
				if(toolBox.isInteger(currentElement)){
					type = toolBox.toInt(currentElement);
					if(0 <= type && type <= 5){
						if(!toolBox.getAppendix(line).isEmpty())
							return new Mark(type);
						else break;
					}else{
						errors.add("Invalid buoy type @line " + row + " @position "
																	+ toolBox.indexOfError(columns, line, currentElement));
						break;
					}
				}
				errors.add("Missing buoy type @line " + row + " @position "
																	+ toolBox.indexOfError(columns, line, currentElement));
				break;
	
			case UNMARK:
				System.out.println("unmark line: " + line);
				currentElement = nextElement(line);
				System.out.println("unmark: " + currentElement);
				if(toolBox.isInteger(currentElement)){
					type = toolBox.toInt(currentElement);
					if(0 <= type && type <= 5){
						if(!toolBox.getAppendix(line).isEmpty())
							return new Unmark(type);
						else break;
					}else{
						errors.add("Invalid buoy type @line " + row + " @position "
																	+ toolBox.indexOfError(columns, line, currentElement));
						break;
					}
				}
				errors.add("Missing buoy type @line " + row + " @position "
																	+ toolBox.indexOfError(columns, line, currentElement));
				break;
				
			case SENSE: 
				currentElement = nextElement(line);
				System.out.println(currentElement);
				if(toolBox.isInteger(currentElement)){
					type = toolBox.toInt(currentElement);
					if(0 <= type && type <= 5){
						if(!toolBox.getAppendix(line).isEmpty())
							return new Sense(type);
						else break;
					}else{
						errors.add("Invalid sense direction @line " + row + " @position "
																	+ toolBox.indexOfError(columns, line, currentElement));
						break;
					}
				}
				errors.add("Missing sense direction @line " + row + " @position "
																	+ toolBox.indexOfError(columns, line, currentElement));
				break;
				
			case MOVE:
				currentElement = nextElement(line);
				line = toolBox.getAppendix(line);
				if(toolBox.isElse(currentElement)){
					currentElement = nextElement(line);
					if(evaluateAddress(currentElement) != -1){
						if(!toolBox.getAppendix(line).isEmpty())
						   return new Move(evaluateAddress(currentElement));	
					   else break;
					}else{
						errors.add("not a valid address or label @line " + row + " @position "
																	+ toolBox.indexOfError(columns, line, currentElement));
						break;
					}
				}
				errors.add("missing else @line " + row + " @position "
																	+ toolBox.indexOfError(columns, line, currentElement));	
				break;											     
			
			case REPAIR: 
				currentElement = nextElement(line);
				line = toolBox.getAppendix(line);
				if(toolBox.isElse(currentElement)){
					currentElement = nextElement(line);
					if(evaluateAddress(currentElement) != -1){
						if(!toolBox.getAppendix(line).isEmpty())
						   return new Repair(evaluateAddress(currentElement));	
					   else break;
					}else{ 
						errors.add("not a valid address or label @line " + row + " @position "
																	+ toolBox.indexOfError(columns, line, currentElement));
						break;
					}
				}
				errors.add("missing else @line " + row + " @position " + toolBox.indexOfError(columns, line, currentElement));	
				break;
				
			case PICKUP: 
				currentElement = nextElement(line);
				line = toolBox.getAppendix(line);
				if(toolBox.isInteger(currentElement)){
					type = toolBox.toInt(currentElement);
						if(0 <= type && type <= 6){
							currentElement = nextElement(line);
							line = toolBox.getAppendix(line);
							if(toolBox.isElse(currentElement)){
								currentElement = nextElement(line);
								line = toolBox.getAppendix(line);
								column = 14 + currentElement.length();
								if(evaluateAddress(currentElement) != -1){
									if(!line.isEmpty())
										return new Pickup(type,evaluateAddress(currentElement));
									else break;
								}else{
									errors.add("not a valid address or label @line " + row + " @Position " 
													    + toolBox.indexOfError(columns, line, currentElement));
									break;
								}
							}else{
								errors.add("missing else @line " + row + " @Position " + 
													      toolBox.indexOfError(columns, line, currentElement));
								break;
							}
						}else{
							errors.add("not a valid direction @line " + row + " @Position " + 
														  toolBox.indexOfError(columns, line, currentElement));
							break;
						}				
				}
				errors.add("Missing sense direction @line " + row + " @Position " 
														+ toolBox.indexOfError(columns, line, currentElement));
				break;
							
			case REFRESH:
				currentElement = nextElement(line);
				line = toolBox.getAppendix(line);
				if(toolBox.isInteger(currentElement)){
					type = toolBox.toInt(currentElement);
						if(0 <= type && type <= 6){
							currentElement = nextElement(line);
							line = toolBox.getAppendix(line);
							if(toolBox.isElse(currentElement)){
								currentElement = nextElement(line);
								line = toolBox.getAppendix(line);
								column = 14 + currentElement.length();
								if(evaluateAddress(currentElement) != -1){
									if(!line.isEmpty())
										return new Refresh(type,evaluateAddress(currentElement));
									else break;
								}else{
									errors.add("not a valid address or label @line " + row + " @Position " 
														+ toolBox.indexOfError(columns, line, currentElement));
									break;
								}
							}else{
								errors.add("missing else @line " + row + " @Position " + 
														  toolBox.indexOfError(columns, line, currentElement));
								break;
							}
						}else{
							errors.add("not a valid direction @line " + row + " @Position " + 
														  toolBox.indexOfError(columns, line, currentElement));
							break;
						}				
				}
				errors.add("Missing sense direction @line" + row + " @Position " + column);
				break;
			
			case FLIPZERO:
				currentElement = nextElement(line);
				line = toolBox.getAppendix(line);
				if(toolBox.isInteger(currentElement)){
					type = toolBox.toInt(currentElement);
						if(type > 1){
							currentElement = nextElement(line);
							line = toolBox.getAppendix(line);
							if(toolBox.isElse(currentElement)){
								currentElement = nextElement(line);
								line = toolBox.getAppendix(line);
								column = 14 + currentElement.length();
								if(evaluateAddress(currentElement) != -1){
									if(!line.isEmpty())
										return new Refresh(type,evaluateAddress(currentElement));
									else break;
								}else{
									errors.add("not a valid address or label @line " + row + " @Position " +column);
									break;
								}
							}else{
								errors.add("missing else @line " + row + " @Position 9");
								break;
							}
						}else{
							errors.add("value to low @line" + row + " @Position 7");
							break;
						}				
				}
				errors.add("Missing value @line" + row + " @Position " + column);
				break;
				
			case IF:
				break;
			
			case IFALL: 
				bools.add(toolBox.buildComparison());
				break;
			
			case IFANY:
				bools.add(toolBox.buildComparison());
				break;
			
			default:
				throw new IllegalArgumentException("Not a valid command @line" + row + 
																			" position" + currentColumn);
			}
			
		return null;
	}
	
	/* @Specs: takes a tactics file, invokes translate(String string) on every line and creates a team tactics
	 * 
	 * @Param: the inputstream of the tactics file
	 * 
	 * @Return: a list of commands
	 * 
	 * @Error: throws illegalArgumentException on empty broken inputstreams.*/
	
	public List<Command> run(InputStream tacticFile){
		BufferedReader tacticdoc = new BufferedReader(new InputStreamReader(tacticFile));
					try {
				if (labelized){
					while(tacticdoc.readLine() != null){
						String firstElement = nextElement(tacticdoc.readLine());
						if(firstElement.startsWith("*"))
							labels.put(firstElement.substring(1), row);
						else continue;
					row++;
					}
				row = 0;
				}
						
				while(tacticdoc.readLine() != null){
					String currentLine = tacticdoc.readLine();
					tactic.add(translate(currentLine));
					row++;
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
		return tactic;
	}
	
	private String nextElement(String line){
		return line.substring(toolBox.nextChar(line), toolBox.nextSpace(line.substring(toolBox.nextChar(line))));
	}
	
	/* @Param: the current word in the line.
	 * 
	 * @Return: the specified jump address as int or -1 whether the word is out of bounds or not a label.*/
	
	private int evaluateAddress(String currentElement){
		if(toolBox.isInteger(currentElement)){
			    if(0 <= toolBox.toInt(currentElement) && toolBox.toInt(currentElement) <= 1999)
			    		return toolBox.toInt(currentElement);
		}if(labelized){
				if(labels.containsKey(currentElement))
				    	return (int) labels.get(currentElement);
				else{
					   errors.add("Not a valid label @line" + row + " ,position" + column);
					   return -1;
				   }
		}else{
				   errors.add("Not a valid jump address @line" + row + " ,position" + column);
				   return -1;
			   }
	}
	
}
