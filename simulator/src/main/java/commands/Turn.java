package commands;

import model.Ship;
import controller.Command;

public class Turn implements Command {

	private boolean left;
	
	public Turn(boolean left){
		this.left = left;
	}
	
	@Override
	public void execute(Ship ship) {
		// TODO Auto-generated method stub

	}

}
