package controller;

import model.CellType;
import model.Register;
import model.Ship;

public class CellTypeComparison implements Comparison {

	private Operator opr;
	private Register register;
	private CellType type;
	
	public CellTypeComparison(Operator opr, Register register, CellType type) {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean eval(Ship ship) {
		// TODO Auto-generated method stub
		return false;
	}

}