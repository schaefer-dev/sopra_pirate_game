package src.main;

import src.enums.Operator;
import src.enums.Register;
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
		this.opr = opr;
		this.constant = constant;
		this.right = right;
		left = null;
	}
	
	@Override
	public String toString(){
		if(constant == null)
			return left.toString() + opr.toString() + right.toString();
		else if(left==null)
			return String.valueOf(constant) + opr.toString() + right.toString();
		else
			return left.toString() + opr.toString() + String.valueOf(constant);
	}

	@Override
	public boolean equals(Object o){
		if(o instanceof IntComparison){
			IntComparison other = (IntComparison) o;
			if(other.opr == opr && other.left == left && other.right == right && other.constant == constant)
				return true;
		}
		return false;	
	}
	
}
