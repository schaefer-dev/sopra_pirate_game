package main;

import enums.Operator;
import enums.CellType;
import enums.Register;
/**
 * Implements the interface <code>Comparison</code>. A <code>CellTypeComparison</code> is a comparison consisting of one
 * CellTypeRegister register (should only be sense_celltype) a <code>CellType</code> and an <code>Operator</code>.
 * This class is used in the <code>If</code>, <code>IfAll</code> and <code>IfAny</code> commands.
 * @author Jan
 *
 */
public class CellTypeComparison implements Comparison {

	private Operator opr;
	private Register register;
	private CellType type;
	
	/**
	 * Constructor of the <code>CellTypeComparison</code> class. Sets opr to opr, type to type and register to register.
	 * @param register The CellType register to be tested in the ship (should be sense_celltype).
	 * @param opr Specifies the operator (Equal or unequal).
	 * @param type The <code>CellType</code> the register is compared with.
	 * @throws IllegalArgumentException if the Register is no CellTypeRegister, when type is undefined or if the Operator is Greater or Less.
	 * @exception NullPointerException if one of the parameters is null.
	 */
	public CellTypeComparison(Operator opr, Register register, CellType type) {
		if(register != Register.sense_celltype)
		{
			throw new IllegalArgumentException("CellTypeComparisons can only be used with the Register sense_celltype.");
		}
		if(opr == null || register == null || type == null)
			throw new NullPointerException("Null is not a valid argument.");
		if(opr == Operator.Less || opr == Operator.Greater)
		{
			throw new IllegalArgumentException("You cannot compare two celltypes with less or greater");
		}
		if(type == CellType.Undefined)
			throw new IllegalArgumentException("One cannot compare a celltype-register with undefined");
		this.opr = opr;
		this.register = register;
		this.type = type;
	}
	
	@Override
	public String toString(){
			return "sense_celltype" + opr.toString() + type.toString();
	}

}
