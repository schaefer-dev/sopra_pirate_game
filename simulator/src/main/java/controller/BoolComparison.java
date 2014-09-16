package controller;

import model.BoolWert;
import model.Register;
import model.Ship;

public class BoolComparison implements Comparison {

	private Register register;
	private boolean invert;
	
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
	public boolean eval(Ship ship) {
		int temp = ship.getSenseRegister(register);
		if (temp == BoolWert.Undefined.ordinal())
			return false;
		if(invert)
			return temp == BoolWert.False.ordinal();
		else
			return temp == BoolWert.True.ordinal();
	}
	
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

}
