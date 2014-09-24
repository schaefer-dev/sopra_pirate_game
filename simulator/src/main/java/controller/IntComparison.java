package controller;

import model.Register;
import model.Ship;
/**
 * Implements the interface <code>Comparison</code>. An <code>IntComparison</code> is a comparison consisting of one
 * either two int-registers or one int register and an int constant.
 * This class is used in the <code>If</code>, <code>IfAll</code> and <code>IfAny</code> commands.
 * @author Jan
 *
 */
public class IntComparison implements Comparison {

	private Operator opr;
	private Register left;
	private Register right;
	private Integer constant;
	/**
	 * One Constructor of the <code>IntComparison</code> class. Sets opr to opr, left to left and right to right. It also sets constant to null.
	 * @param left The left register of this <code>IntComparison</code>.
	 * @param opr Specifies the operator (Equal or Unequal, Greater or Less).
	 * @param right The right the register the left one is compared with.
	 * @throws IllegalArgumentException if one of the registers is no IntRegister.
	 * @exception NullPointerException if one of the parameters is null.
	 */

	public IntComparison(Operator opr, Register left, Register right){
		if(opr == null || left == null || right == null)
		{
			throw new NullPointerException("Comparison needs all arguments to work properly.");
		}
		checkRegister(left);
		checkRegister(right);
		this.left = left;
		this.right = right;
		this.opr = opr;
		constant = null;
	}
	/**
	 * One Constructor of the <code>IntComparison</code> class. Sets opr to opr, left to left and constant to constant. It also sets right to null.
	 * @param left The left register of this <code>IntComparison</code>.
	 * @param opr Specifies the operator (Equal or Unequal, Greater or Less).
	 * @param constant The constant the left register is compared with. Seves as the right side of the comparison.
	 * @throws IllegalArgumentException if left is no IntRegister.
	 * @exception NullPointerException if opr or left is null.
	 */
	public IntComparison(Operator opr, Register left, int constant){
		if(opr == null || left == null)
		{
			throw new NullPointerException("Comparison needs all arguments to work properly.");
		}
		checkRegister(left);
		this.opr = opr;
		this.constant = constant;
		this.left = left;
		right = null;
	}
	/**
	 * One Constructor of the <code>IntComparison</code> class. Sets opr to opr, left to left and constant to constant. It also sets right to null.
	 * @param right The right register of this <code>IntComparison</code>.
	 * @param opr Specifies the operator (Equal or Unequal, Greater or Less).
	 * @param constant The constant the right register is compared with. Seves as the left side of the comparison.
	 * @throws IllegalArgumentException if right is no IntRegister.
	 * @exception NullPointerException if opr or right is null.
	 */
	public IntComparison(Operator opr, int constant, Register right){
		if(opr == null || right == null)
		{
			throw new NullPointerException("Comparison needs all arguments to work properly.");
		}
		checkRegister(right);
		this.opr = opr;
		this.constant = constant;
		this.right = right;
		left = null;
	}
	/**
	 * Overrides the eval method in Comparison.
	 * Evaluates the comparison: If the registers value is undefined the result is false. Otherwise the left and right side are compared depending on the operator.
	 * @param ship the ship with the register(s) to be tested.
	 * @return a boolean, the result of the comparison, false if one register has value undefined.
	 */
	@Override
	public boolean eval(Ship ship){
		int leftInt;
		int rightInt;
		if(this.left == null)
		{
			leftInt = constant;
		}
		else
		{
			leftInt =  ship.getSenseRegister(left);
			if(leftInt == Ship.undefinedInt)
				return false;
		}
		if(right == null)
		{
			rightInt = constant;
		}
		else
		{
			rightInt = ship.getSenseRegister(right);
			if(rightInt == Ship.undefinedInt)
				return false;
		}
		
		switch(opr)
		{
		case Less:
			return leftInt < rightInt;
		case Greater:
			return leftInt > rightInt;
		case Equal:
			return leftInt == rightInt;
		case Unequal:
			return leftInt != rightInt;
		default:
			throw new IllegalStateException();
		}
		
	}
	/**
	 * Overrides the equals method of Object. Two <code>IntComparisons</code> are equal if register and invert of both <code>IntComparison</code>s are identical.
	 * @param o the Object to be tested for equality.
	 * @result Returns whether the object equals this <code>IntComparison</code>
	 */
	@Override
	public boolean equals (Object o)
	{
		if(o instanceof IntComparison)
		{
			IntComparison other = (IntComparison) o;
			if(left == other.left && right == other.right && constant == other.constant && opr == other.opr)
				return true;
		}
		return false;
	}
	/**
	 * Checks whether a given register is an IntRegister.
	 * @param r The register to be tested
	 * @exception IllegalArgumentException thrown if r is not an IntRegister.
	 */
	private void checkRegister(Register r)
	{
		switch (r)
		{
		case ship_direction:
			break;
		case ship_load:
			break;
		case ship_moral:
			break;
		case ship_condition:
			break;
		case sense_shipdirection:
			break;
		case sense_shipcondition:
			break;
		default:
			throw new IllegalArgumentException("You can only compare IntRegisters in an int-Comparison.");
		}
	}

}
