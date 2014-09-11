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

}
