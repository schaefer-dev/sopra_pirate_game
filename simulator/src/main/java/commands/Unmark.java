package commands;

import model.Ship;
import controller.Command;

public class Unmark implements Command {

	private int type;
	
	public Unmark(int type){
		this.type = type;
	}
	
	@Override
	public void execute(Ship ship) {
		// TODO Auto-generated method stub

	}

}
