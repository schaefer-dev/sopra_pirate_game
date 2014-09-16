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
	String currentElement = null;
	String appendix = null;
	int row;
	int columns;
	TranslatorTools toolBox;
	int column = 0;//the column, that marks the index of the first whitespace right after 
    //the occurrence of a coherent string
	
	public Translator(){
		this.tactic = new ArrayList<Command>();
		this.errors = new ArrayList<String>();
		this.row = 0;
		this.toolBox = new TranslatorTools();
		this.labelized = false;
	}
	
	/* @Specs: splits the given line in a and saves the first word in currentElements and the rest 
	 * in appendix.
	 * 
	 * @Params: the line, to work with */
	
	public void makeSplits(String line){
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
		List<Comparison> bools = new ArrayList<Comparison>();
		int currentColumn = 0;//the column, that marks the begin of a coherent string and that would be used 
								//in a optional error statement.
		int type = -1;
		if(line.contains(";"))  //schaut, ob ein Kommentar im Text steht und verkuerzt den String.
			line = line.substring(0, line.indexOf(";"));
	
		makeSplits(line);

			switch (CommandWords.valueOf(currentElement.toUpperCase())){
			
			case DROP:
				line = appendix;
				if(appendix != null && !appendix.isEmpty()){
						overloadError();
						break;
				}
				return new Drop();
			
			case TURN:
				makeSplits(appendix);
				if(currentElement == "left")
					if(appendix == null || appendix.isEmpty())
						return new Turn(true);
					else{
						overloadError();
						break;
					}						
					
				if(currentElement == "right")
					if(appendix == null)
						return new Turn(false);
					else{
						overloadError();
						break;
					}
				errors.add("Not a valid direction @line " + row + " @position "
																	+ 5);
				break;
					   
			case GOTO:
				makeSplits(appendix);
				if(evaluateAddress(currentElement) != -1)
					if(appendix == null || appendix.isEmpty())
						return new Goto(evaluateAddress(currentElement));	
					else{
						overloadError();
						break;
					}
				errors.add("Invalid address or label @line " + row + " @position "
																	+ toolBox.indexOfError(columns, appendix, currentElement));
				break;
				
			case MARK: 
				makeSplits(appendix);
				if(toolBox.isInteger(currentElement)){
					type = toolBox.toInt(currentElement);
					if(0 <= type && type <= 5){
						if(appendix == null || appendix.isEmpty())
							return new Mark(type);
						else{
							overloadError();
							break;
						}
					}else{
						errors.add("Invalid buoy type @line " + row + " @position "
																	+ toolBox.indexOfError(columns, appendix, currentElement));
						break;
					}
				}
				errors.add("Missing buoy type @line " + row + " @position "
																	+ toolBox.indexOfError(columns, appendix, currentElement));
				break;
	
			case UNMARK:
				makeSplits(appendix);
				if(toolBox.isInteger(currentElement)){
					type = toolBox.toInt(currentElement);
					if(0 <= type && type <= 5){
						if(appendix == null || appendix.isEmpty())
							return new Unmark(type);
						else{
							overloadError();
							break;
						}
					}else{
						errors.add("Invalid buoy type @line " + row + " @position "
																	+ toolBox.indexOfError(columns, appendix, currentElement));
						break;
					}
				}
				errors.add("Missing buoy type @line " + row + " @position "
																	+ toolBox.indexOfError(columns, appendix, currentElement));
				break;
				
			case SENSE: 
				makeSplits(appendix);
				if(toolBox.isInteger(currentElement)){
					type = toolBox.toInt(currentElement);
					if(0 <= type && type <= 5){
						if(appendix == null || appendix.isEmpty())
							return new Sense(type);
						else{
							overloadError();
							break;
						}
					}else{
						errors.add("Invalid sense direction @line " + row + " @position "
																	+ toolBox.indexOfError(columns, appendix, currentElement));
						break;
					}
				}
				errors.add("Missing sense direction @line " + row + " @position "
																	+ toolBox.indexOfError(columns, appendix, currentElement));
				break;
				
			case MOVE:
				makeSplits(appendix);
				if(toolBox.isElse(currentElement) && appendix != null){
					makeSplits(appendix);
					if(evaluateAddress(currentElement) != -1){
						if(appendix == null || appendix.isEmpty())
						   return new Move(evaluateAddress(currentElement));	
						else{
							overloadError();
							break;
						}					}else{
						errors.add("not a valid address or label @line " + row + " @position "
																	+ toolBox.indexOfError(columns, appendix, currentElement));
						break;
					}
				}
				errors.add("missing else @line " + row + " @position "
																	+ toolBox.indexOfError(columns, appendix, currentElement));	
				break;											     
			
			case REPAIR: 
				makeSplits(appendix);
				if(toolBox.isElse(currentElement) && appendix != null){
					makeSplits(appendix);
					if(evaluateAddress(currentElement) != -1){
						if(appendix == null || appendix.isEmpty())
						   return new Repair(evaluateAddress(currentElement));	
						else{
							overloadError();
							break;
						}					
					}else{
						errors.add("not a valid address or label @line " + row + " @position "
																	+ toolBox.indexOfError(columns, appendix, currentElement));
						break;
					}
				}
				errors.add("missing else @line " + row + " @position "
																	+ toolBox.indexOfError(columns, appendix, currentElement));	
				break;
				
			case PICKUP: 
				makeSplits(appendix);
				if(toolBox.isInteger(currentElement) || appendix != null){
					type = toolBox.toInt(currentElement);
						if(0 <= type && type <= 6){
							makeSplits(appendix);
							if(toolBox.isElse(currentElement)|| appendix != null){
								makeSplits(appendix);
								if(evaluateAddress(currentElement) != -1){
									if(appendix == null || appendix.isEmpty())
										return new Pickup(type,evaluateAddress(currentElement));
									else{
										overloadError();
										break;
									}	
								}else{
									errors.add("not a valid address or label @line " + row + " @Position " 
													    + toolBox.indexOfError(columns, appendix, currentElement));
									break;
								}
							}else{
								errors.add("missing else @line " + row + " @Position " + 
													      toolBox.indexOfError(columns, appendix, currentElement));
								break;
							}
						}else{
							errors.add("not a valid direction @line " + row + " @Position " + 
														  toolBox.indexOfError(columns, appendix, currentElement));
							break;
						}				
				}
				errors.add("Missing sense direction @line " + row + " @Position " 
														+ toolBox.indexOfError(columns, appendix, currentElement));
				break;
							
			case REFRESH:
				makeSplits(appendix);
				if(toolBox.isInteger(currentElement) || appendix != null){
					type = toolBox.toInt(currentElement);
						if(0 <= type && type <= 5){
							makeSplits(appendix);
							if(toolBox.isElse(currentElement)|| appendix != null){
								makeSplits(appendix);
								if(evaluateAddress(currentElement) != -1){
									if(appendix == null || appendix.isEmpty())
										return new Pickup(type,evaluateAddress(currentElement));
									else{
										overloadError();
										break;
									}
								}else{
									errors.add("not a valid address or label @line " + row + " @Position " 
														+ toolBox.indexOfError(columns, appendix, currentElement));
									break;
								}
							}else{
								errors.add("missing else @line " + row + " @Position " + 
														  toolBox.indexOfError(columns, appendix, currentElement));
								break;
							}
						}else{
							errors.add("not a valid direction @line " + row + " @Position " + 
														  toolBox.indexOfError(columns, appendix, currentElement));
							break;
						}				
				}
				errors.add("Missing sense direction @line" + row + " @Position " + column);
				break;
			
			case FLIPZERO:
				makeSplits(appendix);
				if(toolBox.isInteger(currentElement) || appendix != null){
					type = toolBox.toInt(currentElement);
						if(type > 1){
							makeSplits(appendix);
							if(toolBox.isElse(currentElement)|| appendix != null){
								makeSplits(appendix);
								if(evaluateAddress(currentElement) != -1){
									if(appendix == null || appendix.isEmpty())
										return new Pickup(type,evaluateAddress(currentElement));
									else{
										overloadError();
										break;
									}
								}else{
									errors.add("not a valid address or label @line " + row + " @Position " 
														+ toolBox.indexOfError(columns, appendix, currentElement));
									break;
								}
							}else{
								errors.add("missing else @line " + row + " @Position "
														+ toolBox.indexOfError(columns, appendix, currentElement));
								break;
							}
						}else{
							errors.add("value to low @line " + row + " @Position "
												+ toolBox.indexOfError(columns, appendix, currentElement));
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
	
	/* @Specs: prints error, whether there are too many arguments in a single line*/
	
	private void overloadError() {
		errors.add("Too many arguments @line " + row);		
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
						makeSplits(tacticdoc.readLine());
						if(currentElement.startsWith("*"))
							labels.put(currentElement.substring(1), row);
						else continue;
					row++;
					}
				row = 0;
				}
						
				while(tacticdoc.readLine() != null){
					String currentLine = tacticdoc.readLine();
					if(currentLine == null) continue;
					else tactic.add(translate(currentLine));
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
