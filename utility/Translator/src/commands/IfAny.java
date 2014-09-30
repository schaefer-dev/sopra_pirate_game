package commands;

import java.util.List;

import main.Command;
import main.Comparison;
/**
 * The <code>IfAny</code> class:
 * 
 * This class implements the interface <code>Command</code>. Instances of this class are generated by the <code>Translator</code> class.
 * They are part of the Java-representation of a tactics file. An instance of the <code>IfAny</code> class represents an occurrence of
 * an ifany command in the tactics file. <code>IfAny</code> commands contain a List of <code>Comparison</code>s describing the condition and an int to which the program counter
 * is set in the alternative case.
 * @author Jan
 *
 */
public class IfAny implements Command {

	private List<Comparison> clauses;
	private int elsePC;
	/**
	 * The constructor of the <code>IfAny</code> class. 
	 * Sets the attributes. clauses is set to clauses and elsePC is set to pc.
	 * @param clauses The list of <code>Comparison</code>s which specify the condition.
	 * @param pc The value the program counter is set to on the alternative case.
	 * @exception IllegalArgumentException
	 * 				If the value of pc is not between 0 and 1999. Also thrown if clauses is null or if the size of clauses is 0.
	 */
	public IfAny(List<Comparison> clauses,int pc){
		if(pc<0 || pc> 1999)
		{
			throw new IllegalArgumentException("PC cannot be set to " + pc);
		}
		if(clauses == null || clauses.size() == 0)
			throw new IllegalArgumentException("IfAny needs at least one comparison to work.");
		this.clauses = clauses;
		this.elsePC = pc;
	}
	
	@Override
	public String toString(){
		String res = "ifany ";
		for (Comparison clause: clauses)
			res = res + clause.toString() + " ";
		return res + "else " + String.valueOf(elsePC);
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof IfAny){
			IfAny other = (IfAny) o;
			if(other.clauses == clauses && other.elsePC == elsePC)
				return true;
		}
		return false;	
	}

}