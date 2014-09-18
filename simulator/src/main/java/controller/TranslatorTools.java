package controller;

import model.CellType;
import model.Register;
import model.ShipType;
import controller.Operator;

public class TranslatorTools {
	
	/** @Specs: builds comparison objects of given Strings to construct if, ifany, ifall commands.
	 * 
	 * @Params: the translators currentElement.
	 * 
	 * @Returns: a comparison object.**/
	
	public Comparison buildComparison(String condition){
		String operand2 = null;
		Boolean invert = false;
		Operator operator = null;
		int constant;
		
		if(condition.startsWith("!")){
			condition = condition.substring(1);
			invert = true;
		}else{
			if(condition.contains(">")){
				String comp = condition.substring(0, condition.indexOf(">"));
				if(isInteger(comp) || comp.equalsIgnoreCase("undefined") || comp.equalsIgnoreCase("enemy") || comp.equalsIgnoreCase("friend") 
				|| comp.equalsIgnoreCase("home") || comp.equalsIgnoreCase("enemyhome") || comp.equalsIgnoreCase("island") || comp.equalsIgnoreCase("empty")){
					operand2  = condition.substring(0, condition.indexOf(">"));
					condition = condition.substring(condition.indexOf(">") +1);
					operator = Operator.Less;
				}else{
					operand2 = condition.substring(condition.indexOf(">") +1);
					condition  = condition.substring(0, condition.indexOf(">"));
					operator = Operator.Greater;
				}	
			}else if(condition.contains("<")){
				String comp = condition.substring(0, condition.indexOf("<"));
				if(isInteger(comp) || comp.equalsIgnoreCase("undefined") || comp.equalsIgnoreCase("enemy") || comp.equalsIgnoreCase("friend") 
				|| comp.equalsIgnoreCase("home") || comp.equalsIgnoreCase("enemyhome") || comp.equalsIgnoreCase("island") || comp.equalsIgnoreCase("empty")){					
					operand2  = condition.substring(0, condition.indexOf("<"));
					condition = condition.substring(condition.indexOf("<") +1);
					operator = Operator.Greater;
				}else{
					operand2 = condition.substring(condition.indexOf("<") +1);
					condition  = condition.substring(0, condition.indexOf("<"));
					operator = Operator.Less;
				}	
			}else if(condition.contains("==")){
				String comp = condition.substring(0, condition.indexOf("="));
				operator = Operator.Equal;
				if(isInteger(comp) || comp.equalsIgnoreCase("undefined") || comp.equalsIgnoreCase("enemy") || comp.equalsIgnoreCase("friend") 
				|| comp.equalsIgnoreCase("home") || comp.equalsIgnoreCase("enemyhome") || comp.equalsIgnoreCase("island") || comp.equalsIgnoreCase("empty")){					
					operand2  = condition.substring(0, condition.indexOf("="));
					condition = condition.substring(condition.lastIndexOf("=") +1);
				}else{
					operand2 = condition.substring(condition.lastIndexOf("=") +1);
					condition  = condition.substring(0, condition.indexOf("="));
				}
			}else if(condition.contains("!=")){
				String comp = condition.substring(0, condition.indexOf("!"));
				operator = Operator.Unequal;
				if(isInteger(comp) || comp.equalsIgnoreCase("undefined") || comp.equalsIgnoreCase("enemy") || comp.equalsIgnoreCase("friend") 
				|| comp.equalsIgnoreCase("home") || comp.equalsIgnoreCase("enemyhome") || comp.equalsIgnoreCase("island") || comp.equalsIgnoreCase("empty")){					
					operand2  = condition.substring(0, condition.indexOf("!"));
					condition = condition.substring(condition.lastIndexOf("=") +1);
				}else{
					operand2 = condition.substring(condition.lastIndexOf("=") +1);
					condition  = condition.substring(0, condition.indexOf("!"));
				}
			}	
		}

		switch (Register.valueOf(condition)){
		
		case sense_shiploaded: //bool
			if(operand2 != null)
				return null;
			return new BoolComparison(Register.sense_shiploaded,invert);
		
		case sense_supply: //bool
			if(operand2 != null)
				return null;
			return new BoolComparison(Register.sense_supply,invert);
		
		case sense_treasure: //bool
			if(operand2 != null)
				return null;
			return new BoolComparison(Register.sense_treasure,invert);
		
		case sense_enemymarker: //bool
			if(operand2 != null)
				return null;
			return new BoolComparison(Register.sense_enemymarker,invert);
		
		case sense_marker0: //bool
			if(operand2 != null)
				return null;
			return new BoolComparison(Register.sense_marker0,invert);
		
		case sense_marker1: //bool
			if(operand2 != null)
				return null;
			return new BoolComparison(Register.sense_marker1,invert);
		
		case sense_marker2: //bool
			if(operand2 != null)
				return null;
			return new BoolComparison(Register.sense_marker2,invert);
		
		case sense_marker3: //bool
			if(operand2 != null)
				return null;
			return new BoolComparison(Register.sense_marker3,invert);
		
		case sense_marker4: //bool
			if(operand2 != null)
				return null;
			return new BoolComparison(Register.sense_marker4,invert);
		
		case sense_marker5: //bool
			if(operand2 != null)
				return null;
			return new BoolComparison(Register.sense_marker5,invert);
		
		case sense_shiptype: //String
			if(operand2 == null)
				return null;
			if (operator == null)
				return null;
			if(operand2.equalsIgnoreCase("enemy"))
				return new ShipTypeComparison(operator,Register.sense_shiptype, ShipType.Enemy);
			if(operand2.equalsIgnoreCase("friend"))
				return new ShipTypeComparison(operator,Register.sense_shiptype, ShipType.Friend);
			if(operand2.equalsIgnoreCase("undefined"))
				return new ShipTypeComparison(operator,Register.sense_shiptype, ShipType.Undefined);
			return null;
		
		case sense_celltype: //string
			if(operand2 == null)
				return null;
			if (operator == null)
				return null;
			if(operand2.equalsIgnoreCase("Empty"))
				return new CellTypeComparison(operator,Register.sense_celltype, CellType.Empty);
			else if(operand2.equalsIgnoreCase("Island"))
				return new CellTypeComparison(operator,Register.sense_celltype, CellType.Island);
			else if(operand2.equalsIgnoreCase("Home"))
				return new CellTypeComparison(operator,Register.sense_celltype, CellType.Home);
			else if(operand2.equalsIgnoreCase("EnemyHome"))
				return new CellTypeComparison(operator,Register.sense_celltype, CellType.EnemyHome);
			else if(operand2.equalsIgnoreCase("Undefined"))
				return new CellTypeComparison(operator,Register.sense_celltype, CellType.Undefined);
			return null;
		
		case sense_shipcondition: //int
			if(operand2 == null)
				return null;
			if (operator == null)
				return null;
			if(isInteger(operand2)){
				constant = toInt(operand2);
				if(0 <= constant && constant <= 4)
					return new IntComparison(operator,Register.sense_shipcondition, constant);
				else return null;
			}
			if(operand2.equalsIgnoreCase("sense_shipcondition"))
				return new IntComparison(operator,Register.sense_shipcondition, Register.sense_shipcondition);
			if(operand2.equalsIgnoreCase("sense_shipdirection"))
				return new IntComparison(operator,Register.sense_shipcondition, Register.sense_shipdirection);
			if(operand2.equalsIgnoreCase("ship_condition"))
				return new IntComparison(operator,Register.sense_shipcondition, Register.ship_condition);
			if(operand2.equalsIgnoreCase("ship_direction"))
				return new IntComparison(operator,Register.sense_shipcondition, Register.ship_direction);
			if(operand2.equalsIgnoreCase("ship_load"))
				return new IntComparison(operator,Register.sense_shipcondition, Register.ship_load);
			if(operand2.equalsIgnoreCase("ship_moral"))
				return new IntComparison(operator,Register.sense_shipcondition, Register.ship_moral);
			return null;

		case sense_shipdirection: //int
			if(operand2 == null)
				return null;
			if (operator == null)
				return null;
			if(isInteger(operand2)){
				constant = toInt(operand2);
				if(0 <= constant && constant <= 6)
					return new IntComparison(operator,Register.sense_shipdirection, constant);
				else return null;
			}
			if(operand2.equalsIgnoreCase("sense_shipcondition"))
				return new IntComparison(operator,Register.sense_shipdirection, Register.sense_shipcondition);
			if(operand2.equalsIgnoreCase("sense_shipdirection"))
				return new IntComparison(operator,Register.sense_shipdirection, Register.sense_shipdirection);
			if(operand2.equalsIgnoreCase("ship_condition"))
				return new IntComparison(operator,Register.sense_shipdirection, Register.ship_condition);
			if(operand2.equalsIgnoreCase("ship_direction"))
				return new IntComparison(operator,Register.sense_shipdirection, Register.ship_direction);
			if(operand2.equalsIgnoreCase("ship_load"))
				return new IntComparison(operator,Register.sense_shipdirection, Register.ship_load);
			if(operand2.equalsIgnoreCase("ship_moral"))
				return new IntComparison(operator,Register.sense_shipdirection, Register.ship_moral);
			return null;
			
		case ship_condition: //int
			if(operand2 == null)
				return null;
			if (operator == null)
				return null;
			if(isInteger(operand2)){
				constant = toInt(operand2);
				if(0 <= constant && constant <= 5)
					return new IntComparison(operator,Register.ship_condition, constant);
				else return null;
			}
			if(operand2.equalsIgnoreCase("sense_shipcondition"))
				return new IntComparison(operator,Register.ship_condition, Register.sense_shipcondition);
			if(operand2.equalsIgnoreCase("sense_shipdirection"))
				return new IntComparison(operator,Register.ship_condition, Register.sense_shipdirection);
			if(operand2.equalsIgnoreCase("ship_condition"))
				return new IntComparison(operator,Register.ship_condition, Register.ship_condition);
			if(operand2.equalsIgnoreCase("ship_direction"))
				return new IntComparison(operator,Register.ship_condition, Register.ship_direction);
			if(operand2.equalsIgnoreCase("ship_load"))
				return new IntComparison(operator,Register.ship_condition, Register.ship_load);
			if(operand2.equalsIgnoreCase("ship_moral"))
				return new IntComparison(operator,Register.ship_condition, Register.ship_moral);
			return null;
			
		case ship_direction: //int
			if(operand2 == null)
				return null;
			if (operator == null)
				return null;
			if(isInteger(operand2)){
				constant = toInt(operand2);
				if(0 <= constant && constant <= 6)
					return new IntComparison(operator,Register.ship_direction, constant);
				else return null;
			}
			if(operand2.equalsIgnoreCase("sense_shipcondition"))
				return new IntComparison(operator,Register.ship_direction, Register.sense_shipcondition);
			if(operand2.equalsIgnoreCase("sense_shipdirection"))
				return new IntComparison(operator,Register.ship_direction, Register.sense_shipdirection);
			if(operand2.equalsIgnoreCase("ship_condition"))
				return new IntComparison(operator,Register.ship_direction, Register.ship_condition);
			if(operand2.equalsIgnoreCase("ship_direction"))
				return new IntComparison(operator,Register.ship_direction, Register.ship_direction);
			if(operand2.equalsIgnoreCase("ship_load"))
				return new IntComparison(operator,Register.ship_direction, Register.ship_load);
			if(operand2.equalsIgnoreCase("ship_moral"))
				return new IntComparison(operator,Register.ship_direction, Register.ship_moral);
			return null;
			
		case ship_load: //int
			if(operand2 == null)
				return null;
			if (operator == null)
				return null;
			if(isInteger(operand2)){
				constant = toInt(operand2);
				if(0 <= constant && constant <= 5)
					return new IntComparison(operator,Register.ship_load, constant);
				else return null;
			}
			if(operand2.equalsIgnoreCase("sense_shipcondition"))
				return new IntComparison(operator,Register.ship_load, Register.sense_shipcondition);
			if(operand2.equalsIgnoreCase("sense_shipdirection"))
				return new IntComparison(operator,Register.ship_load, Register.sense_shipdirection);
			if(operand2.equalsIgnoreCase("ship_condition"))
				return new IntComparison(operator,Register.ship_load, Register.ship_condition);
			if(operand2.equalsIgnoreCase("ship_direction"))
				return new IntComparison(operator,Register.ship_load, Register.ship_direction);
			if(operand2.equalsIgnoreCase("ship_load"))
				return new IntComparison(operator,Register.ship_load, Register.ship_load);
			if(operand2.equalsIgnoreCase("ship_moral"))
				return new IntComparison(operator,Register.ship_load, Register.ship_moral);
			else return null;
			
		case ship_moral: //int
			if(operand2 == null)
				return null;
			if (operator == null)
				return null;
			if(isInteger(operand2)){
				constant = toInt(operand2);
				if(0 <= constant && constant <= 5)
					return new IntComparison(operator,Register.ship_moral, constant);
				else return null;
			}
			if(operand2.equalsIgnoreCase("sense_shipcondition"))
				return new IntComparison(operator,Register.ship_moral, Register.sense_shipcondition);
			if(operand2.equalsIgnoreCase("sense_shipdirection"))
				return new IntComparison(operator,Register.ship_moral, Register.sense_shipdirection);
			if(operand2.equalsIgnoreCase("ship_condition"))
				return new IntComparison(operator,Register.ship_moral, Register.ship_condition);
			if(operand2.equalsIgnoreCase("ship_direction"))
				return new IntComparison(operator,Register.ship_moral, Register.ship_direction);
			if(operand2.equalsIgnoreCase("ship_load"))
				return new IntComparison(operator,Register.ship_moral, Register.ship_load);
			if(operand2.equalsIgnoreCase("ship_moral"))
				return new IntComparison(operator,Register.ship_moral, Register.ship_moral);
			return null;
		
		default:
			return null;
		}
	}
	
	/** @Specs: checks, whether the given expression can be converted to an integer, so the translator knows, 
	 * whether it has to look up a label or convert the given expression to string.
	 * 
	 * @Params: the expression to check.
	 * 
	 * @Return: true, if the expression can be converted to integer.**/
	
	public boolean isInteger(String addr){
		try{
			Integer.parseInt(addr);
			return true;
		}catch (NumberFormatException n){
			return false;
		}
	}
	
	/** @Specs: converts a String to an integer. Maybe needless in view of Integer.parseInt(), but maybe
	 * could an additional method provide some synergie effects. 
	 * 
	 * @Params: the String to convert.
	 * 
	 * @Return: the integer value of the String.**/
	
	public int toInt(String addr){
		return Integer.parseInt(addr);
	}
	
	/** @Specs: returns the index of the invalid expressions first char within the initial line.**/
	
	public int indexOfError(int columns, String currentLine, String currentElement){
		return columns - currentLine.length() - currentElement.length();

	}
	
	public TranslatorTools(){
	}

}

