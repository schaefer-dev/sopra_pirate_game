package controller;

import model.Register;
import model.Ship;

public class BoolComparison implements Comparison {

	private Register register;
	private boolean invert;
	
	public BoolComparison(Register register, boolean invert) {
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
		if(o instanceof BoolComparison)
		{
			BoolComparison other = (BoolComparison) o;
			if(register == other.register && invert == other.invert)
				return true;
		}
		return false;
	}

}
