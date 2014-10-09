package de.unisaarland.cs.st.pirates.group5.commands;

import de.unisaarland.cs.st.pirates.group5.controller.Command;
import de.unisaarland.cs.st.pirates.group5.controller.Comparison;
import de.unisaarland.cs.st.pirates.group5.model.Ship;
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
	
	/**
	 * Executes this <code>If</code> command. If the evaluation of the comparison gives true as a result nothing is done since the PC has already been incremented by the act method in <code>Ship</code>.
	 * If the evaluation of the comparison gives false as a result the ship's program counter is set to elsePC.
	 * @param ship The ship in which this command was called.
	 */
	@Override
	public void execute(Ship ship) {
		if(!comparison.eval(ship))
			ship.setPC(elsePC);
	}
	/**
	 * Overrides the equals method of Object. An Object is equal to an <code>If</code> if the object
	 * is an <code>If</code> as well and if the elsePC of both <code>If</code>s are the same. Additionally the comparisons have to be equal.
	 * @param o the Object that is to be tested for equality. 
	 */
	@Override
	public boolean equals (Object o)
	{
		if(o instanceof If)
		{
			If other = (If) o;
			if(other.elsePC == elsePC && other.comparison.equals(comparison))
				return true;
		}
		return false;
	}
	public int hashCode()
	{
		return elsePC;
	}

}