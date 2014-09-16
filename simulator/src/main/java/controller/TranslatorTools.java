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
	
	/* @Specs: looks for any irregular expressions or strings in the current line.
	 
	 * @Params: the subString of the current line between the last valid statement 
	 * and the end of the line, resp. a ';' expression. 
	 * 
	 * @Return: returns true if irregular expressions are found, false if not.*/

	public boolean lookForGarbage(String apendix){
		char[] appendix = apendix.toCharArray();
		int index = 0;
		while(appendix[index] != -1){
			if(appendix[index] != ' '){
				return true;
			}
			index++;
		}
		return false;
	}
	
	 /*@Specs: identifies the index of the first non- whitespace char in a hazy String
	 * 
	 * @Params: the string to check.
	 * 
	 * @Returns: the index of the next char\{' '}.*/

	public int nextChar(String hazy){
		char[] toCheck = hazy.toCharArray();
		int index = 0;
		while(toCheck[index] != ' ')
			index++;
		return index + 1;
	}
	
	/* @Specs: identifies the index of the next whitespace after a sequence of non- whitespace chars, 
	 * to elaborate a coherent string 
	 * 
	 * @Params: a hazy Substring.
	 * 
	 * @Returns: the index of the next whitespace*/
	
	public int nextSpace(String subSequence){
		return subSequence.indexOf(' ');
	}
	
	/* @Specs: checks, whether the given expression can be converted to an integer, so the translator knows, 
	 * whether it has to look up a label or convert the given expression to string.
	 * 
	 * @Params: the expression to check.
	 * 
	 * @Return: true, if the expression can be converted to integer.*/
	
	public boolean isInteger(String addr){
		if(addr.length() > 4)
			return false;
		try{
			Integer.parseInt(addr);
			return true;
		}catch (NumberFormatException n){
			return false;
		}
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
	
	public TranslatorTools(){
	}

}

