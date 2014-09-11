package commands;

import model.Ship;
import controller.Command;
import controller.Comparison;

public class If implements Command {

	private Comparison comparison;
	private int elsePC;
	
	public If (Comparison comparison, int pc){
		this.comparison = comparison;
		this.elsePC = pc;
	}
	
	@Override
	public void execute(Ship ship) {
		// TODO Auto-generated method stub

	}

}
