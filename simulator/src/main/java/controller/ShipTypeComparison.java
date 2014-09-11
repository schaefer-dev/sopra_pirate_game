package controller;

import model.Register;
import model.Ship;
import model.ShipType;

public class ShipTypeComparison implements Comparison {

	private Operator opr;
	private Register register;
	private ShipType type;
	
	public ShipTypeComparison(Operator opr, Register register, ShipType type) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean eval(Ship ship) {
		// TODO Auto-generated method stub
		return false;
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
