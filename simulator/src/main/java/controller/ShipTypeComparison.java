package controller;

import model.Register;
import model.Ship;
import model.ShipType;

public class ShipTypeComparison implements Comparison {

	private Operator opr;
	private Register register;
	private ShipType type;
	
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
	public boolean eval(Ship ship) {
		int temp = ship.getSenseRegister(register);
		if(temp == ShipType.Undefined.ordinal())
		{
			return false;
		}
		if(opr == Operator.Equal)
			return temp == type.ordinal();
		else
			return temp != type.ordinal();
	}
	
	@Override
	public boolean equals (Object o)
	{
		if(o instanceof ShipTypeComparison)
		{
			ShipTypeComparison other = (ShipTypeComparison) o;
			if(register == other.register && type == other.type && opr == other.opr)
				return true;
		}
		return false;
	}

}
