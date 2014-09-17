package controller;

import model.Register;
import model.Ship;

public class IntComparison implements Comparison {

	private Operator opr;
	private Register left;
	private Register right;
	private Integer constant;
	
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
