package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
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
import commands.Error;
import enums.CommandWords;

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
	
	Map<String, Integer> labels;
	List<String> errors;
	boolean labelized;
	String currentElement = null;
	String appendix = null;
	int row, absRow;
	TranslatorTools toolBox;
	
	public Translator(){
		this.errors = new ArrayList<String>();
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
		splits = line.trim().split(" ");
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
		
		int value = -1;
		int elsepc = -1;
		makeSplits(line);
		try{
			switch (CommandWords.valueOf(currentElement.toUpperCase())){
			
			case DROP:
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
				
				}else{ 
					errors.add("row: " + (row + 1));
					return new Error(absRow,"Invalid turn direction: " + currentElement);
				}	   
			case GOTO:
				value = evaluateValues(false,false);
				if(value != -1)
					return new Goto(value);
				break;
				
			case MARK: 
				value = evaluateValues(false,false);
				if(0 <= value && value <= 5)
					return new Mark(value);
				errors.add("row: " + (row + 1));
				return new Error(absRow,"Invalid bouyType: " + currentElement);
				
	
			case UNMARK:
				value = evaluateValues(false,false);
				if(0 <= value && value <= 5)
					return new Unmark(value);
				errors.add("row: " + (row + 1));
				return new Error(absRow,"Invalid bouyType: " + currentElement);
							
			case SENSE: 
				value = evaluateValues(false,false);
					if(0 <= value && value <= 6)
						return new Sense(value);
					errors.add("row: " + (row + 1));
					return new Error(absRow,"Invalid sense direction: " + currentElement);
				
			case MOVE:
				value = evaluateValues(true,false);
				if(value == -1)
					return new Error(absRow, "invalid address or label: " + currentElement);
				return new Move(value);										     
			
			case REPAIR: 
				value = evaluateValues(true,false);
				if(value == -1)
					return new Error(absRow, "invalid address or label: " + currentElement);
				return new Repair(value);
				
			case PICKUP: 
				value = evaluateValues(false,true);
					if(0 <= value && value <= 6){
						elsepc = evaluateValues(true,true);
						if(elsepc != -1){
									return new Pickup(value,elsepc);	
								}else
									return new Error(absRow, "invalid address or label: " + currentElement);
								
					}else{
						if(value > 6)
							errors.add("row: " + (row + 1));
						return new Error(absRow,"Invalid sense direction: " + currentElement);
					}
									
										
			case REFRESH:
				value = evaluateValues(false,true);
					if(0 <= value && value <= 6){
						elsepc = evaluateValues(true,true);
						if(elsepc != -1){
								return new Refresh(value,elsepc);
							}else
								return new Error(absRow, "invalid address or label: " + currentElement);
					}else{
						if(value > 6)
							errors.add("row: " + (row + 1));
						return new Error(absRow,"Invalid sense direction: " + currentElement);
					}				
			
			case FLIPZERO:
				value = evaluateValues(false,true);
					if(value > 1){
						elsepc = evaluateValues(true,true);
						if(elsepc != -1){
							return new Flipzero(value,elsepc);
						}else
							return new Error(absRow, "invalid address or label: " + currentElement);
					}else{
						if(value >= 0)
							errors.add("row: " + (row + 1));
						return new Error(absRow,"Please flip on any number larger than 1: " + currentElement);
					}
				
			case IF:
				makeSplits(appendix);
				Comparison comparison = toolBox.buildComparison(currentElement);
				if(comparison != null && appendix != null){
					value = evaluateValues(true,false);
					if(value != -1){
						return new If(comparison, value);
					}else
						return new Error(absRow, "invalid address or label: " + currentElement);
				}else{
					errors.add("row: " + (row + 1));
					if(comparison == null)
						return new Error(absRow,"Invalid comparison: " + currentElement);	
					else if(appendix == null)
						return new Error(absRow,"Missing else");	

				}		
			
			case IFALL:
				IfAll ifAll = (IfAll) buildIfX(true);
				if(ifAll == null)
					return new Error(absRow, "invalid conditional phrase: " + currentElement);
				return ifAll;
				
			
			case IFANY:
				IfAny ifAny = (IfAny) buildIfX(false);
				if(ifAny == null)
					return new Error(absRow, "invalid conditional phrase: " + currentElement);
				return ifAny;
			
			default:
				errors.add("row: " + (row + 1));
				return new Error(absRow,"This error shouldnt even exist! Looks like you forget to enter a valid command.");	
			}
		
			
		return null;
		}catch(Exception e){
			errors.add("row: " + (row + 1));
			return new Error(absRow,"Exception caught, due to some enum Exceptions.");	
		}
	}
	
	/** @see: prints errors, whether there are too many arguments in a single line**/
	
	private void overloadTest() {
		if(appendix != null)
			errors.add("Too many arguments @line " + row + "(absolut row: " + absRow + ")" );
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
/*************HIER WERDEN DIE LABELS AUSGELESEN*****************************/
				if(labelized){
					tacticsdoc.mark(140*2000);
						while(true){
							String labeledLine = tacticsdoc.readLine();
							if(row >= 2001){
								break;
							}
							if(labeledLine == null)
								break;
							labeledLine = labeledLine.trim().replaceAll("	", " ");
							if(labeledLine.length() < 1){
								row = row + 0;
								continue;
							}else{
								makeSplits(labeledLine);
								if(currentElement.startsWith("*")){
									if (labels.containsValue(currentElement.substring(1).toLowerCase()))
										errors.add("ACHTUNG!!! LABEL :"+ currentElement.substring(1).toLowerCase() + "DOPPELT VERGEBEN!!!!");
									else{
										labels.put(currentElement.substring(1).toLowerCase(), row);
										row++;
									}	
								}else{ 
									row++;
									continue;
								}
							}
						}
/*************DER STREAM WIRD RESETTET*****************************************/

				if(tacticsdoc.markSupported())
					tacticsdoc.reset();
				else throw new IllegalStateException("mark not supported!");
				}
/******************HIER BEGINNT DAS PARSEN*****************************/
				absRow = 1;
				row = 0;
						while(true){
							String currentLine = tacticsdoc.readLine();
							if(row >= 2001)
								break;
							if(currentLine == null)
								break;
							if(currentLine.contains(";"))  //schaut, ob ein Kommentar im Text steht und verkuerzt den String.
								currentLine = currentLine.substring(0, currentLine.indexOf(";"));
							currentLine = currentLine.trim().replaceAll("	", " ");
			if(currentLine.length() < 1){
							row = row + 0;
							absRow++;
			}else{
							if(labelized){
									makeSplits(currentLine);
									if(currentElement.startsWith("*")){
										try{
											tactic.add(translate(appendix));
										}catch(Exception e){
											errors.add("l: " + row + "leere Zeile");
										}
									}else
										tactic.add(translate(currentLine));
							}else 	
								tactic.add(translate(currentLine));	
							row++;
							absRow++;
					}
			}	
				if(labelized){
					if (errors.size() > 0){
						int error = 0;
						while(error < errors.size()){
							System.out.println("Error:" + (error+1) + " @ " + errors.get(error));
							error++;
						}
					}
				}	
		
			} catch (IOException e) {
				throw new IllegalArgumentException("File not found");
			}
		//if(tooLong)
			//throw new IllegalArgumentException("Tactics file too long.");
		//if (errors.size() > 0)
			//throw new IllegalArgumentException("Text: " + errors.get(0) +" laenge " +  errors.size()+"\n");//(errors.get(0));
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
				else
					   return -1;
		}else
			   return -1;
	}
	
	public List<String> getErrors(){
		return errors;
	}
	
	public void setLabelized(boolean what)
	{
		labelized = what;
	}
	
	/** Looks, if the current Element equals else and if there are any following elements.
	 * 
	 *  @return true or false
	 */
		
		private boolean isElse(){
			return appendix != null && currentElement.equalsIgnoreCase("else");
		}
	/**
	 * Evaluates the necessary integers to build any command except drop and turn
	 * 
	 * @return int
	 */
		private int evaluateValues(boolean withElse, boolean twoValues){
				int address;
				makeSplits(appendix);
				if(withElse){
					if(isElse())
						makeSplits(appendix);
					else{
					errors.add("row: " + (row + 1));
					return -1;
					}
			}address = evaluateAddress(currentElement);
				if(address != -1){
					if(!twoValues)
						overloadTest();
					else if(twoValues && withElse)
						overloadTest();
					return address;					
				}else{
					errors.add("row: " + (row + 1));
					return -1;
				}
			
		}
		
	/** Builds IfAny, IfAll Commands.
	 * 
	 *  @param all
	 *  @return all = true : return ifAll; all = false : return ifAny
	 */
		private Command buildIfX(boolean all){
			int elsePC;
			List<String> conditions = new LinkedList<String>();
			List<Comparison> bools = new LinkedList<Comparison>();
			Comparison comparison = null;
			makeSplits(appendix);
			while(!isElse()){
				conditions.add(currentElement);
				makeSplits(appendix);
			}
			for(String condition: conditions){
				comparison = toolBox.buildComparison(condition);
			if(comparison == null){
				errors.add("row: " + (row + 1));
				break;
			}
			bools.add(comparison);
			}
			if(bools.size() != conditions.size()){
			//	errors.add("row: " + (row + 1));
				return null;
			}else{
				elsePC = evaluateValues(false, false);
				if(elsePC != -1){
					if(all)
						return new IfAll(bools, elsePC);
					else
						return new IfAny(bools, elsePC);
				}else{
					return null;
				}
			}
		}
}
