package main;

import enums.BoolWert;
import enums.Register;
/**
 * Implements the interface <code>Comparison</code>. A <code>BoolComparison</code> is a comparison consisting of one
 * bool register and a boolean value that specifies whether there is a not operator in front of bool register.
 * This class is used in the <code>If</code>, <code>IfAll</code> and <code>IfAny</code> commands.
 * @author Jan
 *
 */
public class BoolComparison implements Comparison {

	private Register register;
	private boolean invert;
	/**
	 * Constructor of the <code>BoolComparison</code> class. Sets invert to invert and register to register.
	 * @param register The bool register to be tested in the ship.
	 * @param invert Specifies whether this register's value is negated.
	 * @throws IllegalArgumentException if the Register is no BoolRegister.
	 */
	public BoolComparison(Register register, boolean invert) {
		switch(register){
		case sense_supply:
			break;
		case sense_treasure:
			break;
		case sense_marker0:
			break;
		case sense_marker1:
			break;
		case sense_marker2:
			break;
		case sense_marker3:
			break;
		case sense_marker4:
			break;
		case sense_marker5:
			break;
		case sense_enemymarker:
			break;
		case sense_shiploaded:
			break;
		default:
			throw new IllegalArgumentException("BoolComparisons can only be called with bool registers.");
		}
		this.invert = invert;
		this.register = register;
	}
	
	
	@Override
	public String toString(){
		if(invert)
			return "!"+register.toString();
		else
			return register.toString();
	}

}
