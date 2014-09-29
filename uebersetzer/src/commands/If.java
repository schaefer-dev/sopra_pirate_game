package commands;

import main.Command;
import main.Comparison;
/**
 * 
 * The <code>If</code> class:
 * 
 * This class implements the interface <code> Command</code>. Instances of this class are generated by the <code>Translator</code> class.
 * They are part of the Java-representation of a tactics file. An instance of the <code>If</code> class represents an occurrence of
 * an if command in the tactics file. <code>If</code> commands contain a <code>Comparison</code> describing the condition and an int to which the program counter
 * is set in the alternative case.
 * 
 * @author Jan
 *
 */


public class If implements Command {

	private Comparison comparison;
	private int elsePC;
	
	/**
	 * The constructor of the <code>If</code> class.
	 * Sets the attributes. comparison is set to comparison and elsePC is set to pc.
	 * @param comparison A <code>Comparison</code> that specifies the condition of this <code>If</code> command.
	 * @param pc The value the program counter will be set to, if the comparison evaluates to false.
	 * @exception IllegalArgumentException
	 * 				If  pc is less than 0 or greater than 1999
	 * @exception NullPointerException
	 * 				If the parameter comparison is null.
	 */
	public If (Comparison comparison, int pc){
		if(pc<0 || pc> 1999)
		{
			throw new IllegalArgumentException("PC cannot be set to " + pc);
		}
		if(comparison == null)
		{
			throw new NullPointerException("If needs a comparison to work.");
		}
		this.comparison = comparison;
		this.elsePC = pc;
	}
	
	@Override
	public String toString(){
		return "if " + comparison.toString() + " else " + String.valueOf(elsePC);
	}

}
