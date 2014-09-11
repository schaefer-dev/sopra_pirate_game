package commands;

import model.Ship;
import controller.Command;

public class Refresh implements Command {

	private int direction;
	private int elsePC;
	
	public Refresh(int dir, int pc){
		this.direction = dir;
		this.elsePC = pc;
	}
	
	@Override
	public void execute(Ship ship) {
		// TODO Auto-generated method stub

	}

}
