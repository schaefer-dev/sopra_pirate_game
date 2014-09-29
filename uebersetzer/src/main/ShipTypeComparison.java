package src.main;

import src.enums.Operator;
import src.enums.Register;
import src.enums.ShipType;
/**
 * Implements the interface <code>Comparison</code>. A <code>ShipTypeComparison</code> is a comparison consisting of one
 * CellTypeRegister register (should only be sense_shiptype) a <code>ShipType</code> and an <code>Operator</code>.
 * This class is used in the <code>If</code>, <code>IfAll</code> and <code>IfAny</code> commands.
 * @author Jan
 *
 */
public class ShipTypeComparison implements Comparison {

	private Operator opr;
	private Register register;
	private ShipType type;
	/**
	 * Constructor of the <code>ShipTypeComparison</code> class. Sets opr to opr, type to type and register to register.
	 * @param register The CellType register to be tested in the ship (should be sense_shiptype).
	 * @param opr Specifies the operator (Equal or unequal).
	 * @param type The <code>ShipType</code> the register is compared with.
	 * @throws IllegalArgumentException if the Register is no ShipTypeRegister, when type is undefined or if the Operator is Greater or Less.
	 * @exception NullPointerException if one of the parameters is null.
	 */
	public ShipTypeComparison(Operator opr, Register register, ShipType type) {
		if(opr == null || register == null || type == null)
			throw new NullPointerException("Null is not a valid argument.");
		if(register != Register.sense_shiptype)
			throw new IllegalArgumentException("You can only compare a shiptype with sense_shiptype");
		if(type == ShipType.Undefined)
			throw new IllegalArgumentException("You cannot compare a register with undefined.");
		if(opr == Operator.Less || opr == Operator.Greater)
			throw new IllegalArgumentException("One cannot compare a shiptype with less or greater.");
		this.opr = opr;
		this.register = register;
		this.type = type;
	}
	
	@Override
	public String toString(){
		return register.toString() + opr.toString() + type.toString();
	}

}
