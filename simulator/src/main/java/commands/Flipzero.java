package commands;

import controller.Command;
import model.Ship;

public class Flipzero implements Command{
	private int p;
	private int elsePC;
	
	public Flipzero(int p, int pc){
		this.p = p;
		this.elsePC = pc;
	}
	
	@Override
	public void execute(Ship ship) {
		// TODO Auto-generated method stub

	
	}
}
