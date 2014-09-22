package controller;

import model.CellType;
import model.Register;
import model.Ship;

public class CellTypeComparison implements Comparison {

	private Operator opr;
	private Register register;
	private CellType type;
	
	/**
	 * Implements the interface <code>Comparison</code>. A <code>CellTypeComparison</code> is a comparison consisting of one
	 * CellTypeRegister register (should only be sense_celltype) and a boolean value that specifies whether there is a not operator in front of bool register.
	 * This class is used in the <code>If</code>, <code>IfAll</code> and <code>IfAny</code> commands.
	 * @author Jan
	 *
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
	public boolean eval(Ship ship) {
		int temp = ship.getSenseRegister(register);
		if(temp == CellType.Undefined.ordinal())
			return false;
		if(opr == Operator.Equal)
			return type.ordinal() == temp;
		else
			return type.ordinal() != temp; //Fall unequal
	}
	
	@Override
	public boolean equals (Object o)
	{
		if(o instanceof CellTypeComparison)
		{
			CellTypeComparison other = (CellTypeComparison) o;
			if(register == other.register && type == other.type && opr == other.opr)
				return true;
		}
		return false;
	}

}
