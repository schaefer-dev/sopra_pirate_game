package controller;

import model.Register;
import model.Ship;

public class IntComparison implements Comparison {

	private Operator opr;
	private Register left;
	private Register right;
	private int constant;
	
	public IntComparison(Operator opr, Register left, Register right){
		// TODO Auto-generated method stub
	}
	
	public IntComparison(Operator opr, Register left, int constant){
		// TODO Auto-generated method stub
	}
	
	public IntComparison(Operator opr, int constant, Register right){
		// TODO Auto-generated method stub
	}
	
	@Override
	public boolean eval(Ship ship) {
		// TODO Auto-generated method stub
		return false;
	}
	
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

}
