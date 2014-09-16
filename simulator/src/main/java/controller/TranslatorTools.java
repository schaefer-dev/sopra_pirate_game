package controller;

public class TranslatorTools {
	
	/* @Specs: builds comparison objects of given Strings to construct if, ifany, ifall commands.
	 * 
	 * @Params: Not yet determined.
	 * 
	 * @Returns: an object of type comparison.*/
	
	public Comparison buildComparison(){
		Comparison comparison = null;
		return comparison;
	}
	

	 /*@Specs: isolates the next word
	 * 
	 * @Params: the string to check.
	 * 
	 * @Returns: the index of the next .*/

	public String nextElement(String line){
		return line;
	}
	
	/* @Specs: cuts off the string behind the last valid token.
	 * 
	 * @Params: the string to cut.
	 * 
	 * @Return: the string behind the last valid token.*/
	
	public String getAppendix(String line){
		return line;
	}
	
	/* @Specs: checks, whether the given expression can be converted to an integer, so the translator knows, 
	 * whether it has to look up a label or convert the given expression to string.
	 * 
	 * @Params: the expression to check.
	 * 
	 * @Return: true, if the expression can be converted to integer.*/
	
	public boolean isInteger(String addr){
		/*if(addr.length() > 4)
			return false;
		try{
			Integer.parseInt(addr);
			return true;
		}catch (NumberFormatException n){
			return false;
		}*/ return true;
	}
	
	/* @Specs: converts a String to an integer. Maybe needless in view of Integer.parseInt(), but maybe
	 * could an additional method provide some synergie effects. 
	 * 
	 * @Params: the String to convert.
	 * 
	 * @Return: the integer value of the String.*/
	
	public int toInt(String addr){
		return Integer.parseInt(addr);
	}
	
	
	/* @Specs: checks if the input string equals "else" */
	
	public boolean isElse(String currentElement){
		if(currentElement == "else")
			return true;
		return false;
	}
	
	/* @Specs: returns the index of the invalid expressions first char within the initial line.*/
	
	public int indexOfError(int columns, String line, String currentElement){
		return columns - getAppendix(line).length() - currentElement.length();

	}
	
	public TranslatorTools(){
	}

}

