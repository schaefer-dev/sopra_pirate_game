package controller;

import model.BoolWert;
import model.Register;
import model.Ship;
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
	/**
	 * Overrides the eval method in Comparison.
	 * Evaluates the comparison: If the registers value is undefined the result is false. Otherwise the result is the value of the Register or,
	 * if invert is true, the opposite of the value of the register.
	 * @param ship the ship with the register to be tested.
	 * @return a boolean, false if register's value is undefined. The result of the comparison otherwise.
	 */
	@Override
	public boolean eval(Ship ship) {
		int temp = ship.getSenseRegister(register);
		if (temp == BoolWert.Undefined.ordinal())
			return false;
		if(invert)
			return (temp == BoolWert.False.ordinal());
		else
			return (temp == BoolWert.True.ordinal());
	}
	/**
	 * Overrides the equals method of Object. Two <code>BoolComparisons</code> are equal if register and invert of both <code>BoolComparison</code>s are identical.
	 * @param o the Object to be tested for equality.
	 * @result Returns whether the object equals this <code>BoolComparison</code>
	 */
	@Override
	public boolean equals (Object o)
	{
		if(o instanceof BoolComparison)
		{
			BoolComparison other = (BoolComparison) o;
			if(register == other.register && invert == other.invert)
				return true;
		}
		return false;
	}
	@Override
	public int hashCode()
	{
		return register.ordinal();
	}

}
